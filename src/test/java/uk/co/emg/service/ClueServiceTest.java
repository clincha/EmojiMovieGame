package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.entity.Mutation;
import uk.co.emg.enumeration.MutationType;
import uk.co.emg.repository.ClueRepository;
import uk.co.emg.utils.ClueUtils;
import uk.co.emg.utils.FilmUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
        clueService = new ClueService(emojiService, guessService, mutationService, clueRepository);
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

        assertEquals("{\"children\":[{\"text\":{\"name\":\"☺ ☹\",\"desc\":\"RANDOM_ADDITION\"}},{\"text\":{\"name\":\"☺ ☹\",\"desc\":\"GROUP_CHANGE\"}}],\"text\":{\"name\":\"☺ ☹\",\"desc\":\"RANDOM_CHANGE\"}}",
                clueService.createClueFamilyTree(clue).toString());
    }

    @Test
    public void getClueTest_UnseenClueAvailable() {
        var allClues = new ArrayList<Clue>(3);
        var previousGuesses = new ArrayList<Guess>(2);

        allClues.add(new Clue(0, FilmUtils.getFilm()));
        allClues.add(new Clue(1, FilmUtils.getFilm()));
        allClues.add(new Clue(2, FilmUtils.getFilm()));

        previousGuesses.add(new Guess(allClues.get(0), allClues.get(0).getFilm(), "99"));
        previousGuesses.add(new Guess(allClues.get(1), allClues.get(1).getFilm(), "99"));

        when(clueRepository.findAllByFitnessIsNull()).thenReturn(allClues);
        when(guessService.getGuesses(Mockito.any(HttpSession.class))).thenReturn(previousGuesses);

        var returnedClue = clueService.getClue(Mockito.mock(HttpSession.class));

        assertNotNull(returnedClue);
        assertEquals(returnedClue, allClues.get(2));
    }

    @Test
    public void getClueTest_UnseenClueUnavailable() {
        var allClues = new ArrayList<Clue>(3);
        var previousGuesses = new ArrayList<Guess>(2);

        allClues.add(new Clue(0, FilmUtils.getFilm()));
        allClues.add(new Clue(1, FilmUtils.getFilm()));
        allClues.add(new Clue(2, FilmUtils.getFilm()));

        previousGuesses.add(new Guess(allClues.get(0), allClues.get(0).getFilm(), "99"));
        previousGuesses.add(new Guess(allClues.get(1), allClues.get(1).getFilm(), "99"));
        previousGuesses.add(new Guess(allClues.get(2), allClues.get(2).getFilm(), "99"));

        when(clueRepository.findAllByFitnessIsNull()).thenReturn(allClues);
        when(guessService.getGuesses(Mockito.any(HttpSession.class))).thenReturn(previousGuesses);

        var returnedClue = clueService.getClue(Mockito.mock(HttpSession.class));

        assertNotNull(returnedClue);
        assertTrue(allClues.contains(returnedClue));
    }
}
