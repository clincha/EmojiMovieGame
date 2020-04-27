package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FilmServiceTest {

  @MockBean
  private ApiService apiService;
  @MockBean
  private FilmRepository filmRepository;
  @MockBean
  private ClueService clueService;
  private FilmService filmService;

  @Before
  public void before() {
    filmService = new FilmService(apiService, filmRepository, clueService);
  }

  @Test
  public void generationCheckTest() {
    Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
    Clue clue = new Clue(film);

    ArrayList<Guess> guesses = new ArrayList<>(FilmService.GENERATION_GUESS_THRESHOLD);
    for (int i = 0; i < FilmService.GENERATION_GUESS_THRESHOLD; i++) {
      Guess guess = new Guess(clue, film);
      guesses.add(guess);
    }

    clue.setGuesses(guesses);
    assertTrue(filmService.isGenerationComplete(List.of(clue)));
    clue.setGuesses(guesses.subList(0, 2));
    assertFalse(filmService.isGenerationComplete(List.of(clue)));
  }
}