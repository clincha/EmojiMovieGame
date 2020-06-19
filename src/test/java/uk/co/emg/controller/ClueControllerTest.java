package uk.co.emg.controller;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;
import uk.co.emg.exception.IncorrectClueIdException;
import uk.co.emg.exception.IncorrectFilmIdException;
import uk.co.emg.service.ClueService;
import uk.co.emg.service.FilmService;
import uk.co.emg.service.GuessService;
import uk.co.emg.utils.ClueUtils;
import uk.co.emg.utils.FilmUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.when;

class ClueControllerTest {

    private final ClueService clueService = Mockito.mock(ClueService.class);
    private final FilmService filmService = Mockito.mock(FilmService.class);
    private final GuessService guessService = Mockito.mock(GuessService.class);
    private final HttpSession httpSession = Mockito.mock(HttpSession.class);
    ClueController clueController;

    public ClueControllerTest() {
        clueController = new ClueController(clueService, filmService, guessService);
    }

    @Test
    void clue() {
        ModelAndView modelAndView = clueController.clue(httpSession);
        Assert.assertEquals("Clue", modelAndView.getViewName());

    }

    @Test
    void guess() throws IncorrectFilmIdException, IncorrectClueIdException {
        when(clueService.getClue(1L)).thenReturn(Optional.of(ClueUtils.getClue()));
        when(filmService.getFilm(1L)).thenReturn(Optional.of(FilmUtils.getFilm()));
        when(clueService.createClueFamilyTree(ClueUtils.getClue())).thenReturn(new JSONObject(new HashMap<String, String>()));
        ModelAndView modelAndView = clueController.guess(httpSession, 1L, 1L);
        Assert.assertEquals("Result", modelAndView.getViewName());
    }
}