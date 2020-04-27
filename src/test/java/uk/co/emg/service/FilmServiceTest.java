package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FilmServiceTest {

  @MockBean
  private final ClueService clueService = Mockito.mock(ClueService.class);
  @MockBean
  private final FilmRepository filmRepository = Mockito.mock(FilmRepository.class);
  @MockBean
  private ApiService apiService;
  private FilmService filmService;

  @Before
  public void before() {
    filmService = new FilmService(apiService, filmRepository, clueService);
  }

  @Test
  public void generationCheckTest() {
    Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
    List<Clue> clues = new ArrayList<>();
    for (int i = 0; i < FilmService.INITIAL_CLUE_GENERATION_SIZE; i++) {
      Clue clue = new Clue(film);
      clues.add(clue);
      List<Guess> guesses = new ArrayList<>();
      for (int j = 0; j < FilmService.GENERATION_GUESS_THRESHOLD; j++) {
        Guess guess = new Guess(clue, film);
        guesses.add(guess);
      }
      clue.setGuesses(guesses);
    }
    film.setClues(clues);

    when(clueService.save(any(Clue.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    when(clueService.breed(any(Clue.class), any(Clue.class))).thenReturn(new Clue(film, clues.get(0)
      .getGeneration() + 1));

    filmService.generationCheck(film);

    assertEquals(film.getClues()
      .size(), FilmService.INITIAL_CLUE_GENERATION_SIZE * 2);
    assertEquals(2, film.getClues()
      .stream()
      .mapToInt(Clue::getGeneration)
      .distinct()
      .count());
  }

  @Test
  public void isGenerationCompleteTest() {
    Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
    Clue clue = new Clue(film);
    film.setClues(List.of(clue));
    ArrayList<Guess> guesses = new ArrayList<>(FilmService.GENERATION_GUESS_THRESHOLD);
    for (int i = 0; i < FilmService.GENERATION_GUESS_THRESHOLD; i++) {
      Guess guess = new Guess(clue, clue.getFilm());
      guesses.add(guess);
    }
    clue.setGuesses(guesses);
    assertTrue(filmService.isGenerationComplete(film));
    clue.setGuesses(guesses.subList(0, 2));
    assertFalse(filmService.isGenerationComplete(film));
  }

  @Test
  public void createNewGenerationTest() {
    Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
    List<Clue> clues = new ArrayList<>(FilmService.INITIAL_CLUE_GENERATION_SIZE);
    for (int i = 0; i < FilmService.INITIAL_CLUE_GENERATION_SIZE; i++) {
      Clue clue = new Clue(film);
      clue.setId((long) i);
      clues.add(clue);
    }
    film.setClues(clues);

    when(clueService.save(any(Clue.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

    List<Clue> newGeneration = filmService.createNewGeneration(film);

    assertTrue(newGeneration.contains(film.getClues()
      .get(0)));
    assertTrue(newGeneration.contains(film.getClues()
      .get(1)));
    assertTrue(newGeneration.contains(film.getClues()
      .get(2)));

    assertEquals(10, newGeneration.size());
  }
}