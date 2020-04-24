package uk.co.emg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.repository.ClueRepository;
import uk.co.emg.service.ClueComponentService;
import uk.co.emg.service.ClueService;
import uk.co.emg.service.EmojiService;
import uk.co.emg.service.FilmService;
import uk.co.emg.service.GuessService;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class FitnessTests {

  private ClueService clueService;

  @Mock
  private EmojiService emojiService;

  @Mock
  private ClueRepository clueRepository;

  @Mock
  private FilmService filmService;

  @Mock
  private ClueComponentService clueComponentService;

  @Mock
  private GuessService guessService;

  @Before
  public void setup() {
    clueService = new ClueService(emojiService, filmService, clueComponentService, clueRepository);
  }

  @Test
  public void calculateFitnessTest() {
    Film correctFilm = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
    Film wrongFilm = new Film(2, "Test Title", "Test Poster Path", "Test Overview");
    Clue clue = new Clue(correctFilm);
    Guess correctGuess = new Guess(clue, correctFilm);
    Guess wrongGuess = new Guess(clue, wrongFilm);

    assertEquals(0.5, clueService.calculateFitness(List.of(correctGuess, wrongGuess)), 0);
  }

}
