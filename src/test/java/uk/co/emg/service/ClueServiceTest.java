package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.exception.NoCluesException;
import uk.co.emg.repository.ClueRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ClueServiceTest {

  @MockBean
  private final ClueRepository clueRepository = Mockito.mock(ClueRepository.class);
  @MockBean
  private final GuessService guessService = Mockito.mock(GuessService.class);
  private ClueService clueService;
  @Mock
  private EmojiService emojiService;
  @Mock
  private FilmService filmService;
  @Mock
  private ClueComponentService clueComponentService;

  @Before
  public void before() {
    clueService = new ClueService(emojiService, filmService, clueComponentService, guessService, clueRepository);
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

    when(clueRepository.findAll()).thenReturn(List.of(clue, clue1, clue2));

    when(guessService.getGuesses(clue)).thenReturn(List.of(new Guess(clue, film), new Guess(clue, film)));
    when(guessService.getGuesses(clue1)).thenReturn(List.of(new Guess(clue, film)));
    when(guessService.getGuesses(clue2)).thenReturn(List.of(new Guess(clue, film), new Guess(clue, film), new Guess(clue,film)));

    assertEquals(clue1, clueService.getClue());
  }

  @Test
  public void calculateFitnessTest() {
    Film correctFilm = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
    Film wrongFilm = new Film(2, "Test Title", "Test Poster Path", "Test Overview");

    Clue clue = new Clue(correctFilm);

    Guess correctGuess = new Guess(clue, correctFilm);
    Guess wrongGuess = new Guess(clue, wrongFilm);

    when(guessService.getGuesses(clue)).thenReturn(List.of(correctGuess, wrongGuess));
    when(clueRepository.save(clue)).thenReturn(clue);

    assertEquals(0.5, clueService.calculateFitness(clue), 0);
  }

}
