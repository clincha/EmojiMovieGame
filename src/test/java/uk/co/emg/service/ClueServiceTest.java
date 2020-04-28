package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.*;
import uk.co.emg.exception.NoCluesException;
import uk.co.emg.repository.ClueRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    private EmojiService emojiService;
    private ClueService clueService;

    @Before
    public void before() {
        clueService = new ClueService(emojiService, clueComponentService, guessService, clueRepository);
    }

    @Test
    public void getClueTest() throws NoCluesException {
        Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");

        Clue clue = new Clue(film);
        Clue clue1 = new Clue(film);
        Clue clue2 = new Clue(film);

        clue.setId(0L);
        clue1.setId(1L);
        clue2.setId(2L);

        when(clueRepository.findAllByFitnessIsNull()).thenReturn(List.of(clue, clue1, clue2));

        when(guessService.getGuesses(clue)).thenReturn(List.of(new Guess(clue, film), new Guess(clue, film)));
        when(guessService.getGuesses(clue1)).thenReturn(List.of(new Guess(clue, film)));
        when(guessService.getGuesses(clue2)).thenReturn(List.of(new Guess(clue, film), new Guess(clue, film), new Guess(clue, film)));

        assertEquals(clue1, clueService.getClue());
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

        Clue child = clueService.breed(mother, father);

        assertEquals(child.getClueComponents().get(0), clueComponents.get(0));
        assertEquals(child.getClueComponents().get(1), clueComponents.get(1));
    }

    @Test
    public void calculateFitnessTest() {
        Film correctFilm = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        Film wrongFilm = new Film(2, "Wrong Test Title", "WrongTest Poster Path", "Wrong Test Overview");

        Clue clue = new Clue(correctFilm);

        Guess correctGuess = new Guess(clue, correctFilm);
        Guess wrongGuess = new Guess(clue, wrongFilm);

        when(guessService.getGuesses(clue)).thenReturn(List.of(correctGuess, wrongGuess));
        when(clueRepository.save(clue)).thenReturn(clue);

        assertEquals(0.5, clueService.calculateFitness(clue), 0);
    }

}
