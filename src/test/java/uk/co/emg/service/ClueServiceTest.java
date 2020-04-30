package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.*;
import uk.co.emg.enumeration.MutationType;
import uk.co.emg.repository.ClueRepository;
import utils.ClueUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ClueServiceTest {

    @MockBean
    private final ClueRepository clueRepository = Mockito.mock(ClueRepository.class);
    @MockBean
    private final GuessService guessService = Mockito.mock(GuessService.class);
    @MockBean
    private final ClueComponentService clueComponentService = Mockito.mock(ClueComponentService.class);
    @MockBean
    private final MutationService mutationService = Mockito.mock(MutationService.class);
    @MockBean
    private EmojiService emojiService;
    private ClueService clueService;

    @Before
    public void before() {
        clueService = new ClueService(emojiService, clueComponentService, guessService, mutationService, clueRepository);
    }

    @Test
    public void newGetClueTest() {
        ArrayList<Film> films = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Film film = new Film(i, "Test " + i + " Title", "Test " + i + " Poster Path", "Test " + i + " Overview");
            ArrayList<Clue> clues = new ArrayList<>(3);
            for (int j = 0; j < 3; j++) {
                Clue clue = new Clue(i * 3 + j, film);
                clues.add(clue);
            }
            film.setClues(clues);
            films.add(film);
        }

        when(clueRepository.findAllByFitnessIsNull()).thenReturn(films.stream()
                .map(Film::getClues)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
        );

        ArrayList<Clue> usedClues = new ArrayList<>(9);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Clue clue = clueService.getClue(usedClues);
                assertFalse(usedClues.contains(clue));
                usedClues.add(films.get(j).getClues().get(i));
            }
        }
    }

    @Test
    public void childContainsHalfMotherAndHalfFatherInCorrectOrder() {
        Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        Clue mother = new Clue(film);
        Clue father = new Clue(film);

        ArrayList<ClueComponent> clueComponents = new ArrayList<>();
        clueComponents.add(new ClueComponent(mother, new Emoji("smiling-face", "☺", "263A FE0F", "smileys-emotion", "face-affection", "smiling face")));
        clueComponents.add(new ClueComponent(mother, new Emoji("frowning-face", "☹", "2639 FE0F", "smileys-emotion", "face-concerned", "frowning face")));

        mother.setClueComponents(clueComponents);
        father.setClueComponents(clueComponents);

        when(clueRepository.save(any(Clue.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        when(clueComponentService.saveAll(any())).then(invocationOnMock -> invocationOnMock.getArguments()[0]);
        
        Clue child = clueService.breed(mother, father);

        assertEquals(child.getClueComponents().get(0), clueComponents.get(0));
        assertEquals(child.getClueComponents().get(1), clueComponents.get(1));
    }

    @Test
    public void calculateFitnessTest() {
        Film correctFilm = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        Film wrongFilm = new Film(2, "Wrong Test Title", "WrongTest Poster Path", "Wrong Test Overview");

        Clue clue = new Clue(correctFilm);

        Guess correctGuess = new Guess(clue, correctFilm, "1234");
        Guess wrongGuess = new Guess(clue, wrongFilm, "1234");

        when(guessService.getGuesses(clue)).thenReturn(List.of(correctGuess, wrongGuess));
        when(clueRepository.save(clue)).thenReturn(clue);

        assertEquals(0.5, clueService.calculateFitness(clue), 0);
    }

    @Test
    public void createClueFamilyTree() {
        Clue clue = ClueUtils.getClue();
        clue.setMother(ClueUtils.getClue());
        clue.setFather(ClueUtils.getClue());

        clue.setMutation(new Mutation(clue, MutationType.RANDOM_CHANGE));
        clue.getFather().setMutation(new Mutation(clue.getFather(), MutationType.GROUP_CHANGE));
        clue.getMother().setMutation(new Mutation(clue.getMother(), MutationType.RANDOM_ADDITION));

        assertEquals("{\"children\":[{\"text\":{\"name\":\"☺☹\",\"desc\":\"RANDOM_ADDITION\"}},{\"text\":{\"name\":\"☺☹\",\"desc\":\"GROUP_CHANGE\"}}],\"text\":{\"name\":\"☺☹\",\"desc\":\"RANDOM_CHANGE\"}}", clueService.createClueFamilyTree(clue).toString());
    }
}
