package uk.co.emg.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;
import uk.co.emg.utils.FilmUtils;
import uk.co.emg.utils.ClueUtils;

import java.util.List;

import static org.junit.Assert.*;
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
    public void newGenerationDoesNotDestroyOldGeneration() {
        Film film = FilmUtils.getFilm();

        when(clueService.getAllClues(film)).thenReturn(film.getClues());
        when(clueService.save(any(Clue.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        when(clueService.breed(any(Clue.class), any(Clue.class))).thenReturn(ClueUtils.getClue());

        filmService.generationCheck(film);

        assertEquals(20, film.getClues().size());
        assertEquals(2, film.getClues().stream().mapToInt(Clue::getGeneration).distinct().count());
    }

    @Test
    public void isGenerationCompleteTest() {
        Film film = FilmUtils.getFilm();
        when(clueService.getAllClues(film)).thenReturn(film.getClues());
        assertTrue(filmService.isGenerationComplete(film));
        film.getClues().get(0).setGuesses(film.getClues().get(0).getGuesses().subList(0, 2));
        assertFalse(filmService.isGenerationComplete(film));
    }

    @Test
    public void newGenerationIsTheCorrectSize() {
        Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        film.setClues(ClueUtils.getClues());

        when(clueService.getAllClues(film)).thenReturn(ClueUtils.getClues());
        when(clueService.calculateFitness(any(Clue.class))).thenReturn(12.2).thenReturn(45.3).thenReturn(15.6);
        when(clueService.save(any(Clue.class))).thenReturn(ClueUtils.getClue());

        List<Clue> newGeneration = filmService.createNewGeneration(film);

        assertEquals(10, newGeneration.size());
    }
}