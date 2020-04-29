package uk.co.emg.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.exception.IncorrectClueIdException;
import uk.co.emg.exception.IncorrectFilmIdException;
import uk.co.emg.service.ClueService;
import uk.co.emg.service.FilmService;
import uk.co.emg.service.GuessService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClueController {

    private final ClueService clueService;
    private final FilmService filmService;
    private final GuessService guessService;

    public ClueController(ClueService clueService, FilmService filmService, GuessService guessService) {
        this.clueService = clueService;
        this.filmService = filmService;
        this.guessService = guessService;
    }

    @GetMapping("/clue")
    public ModelAndView clue(HttpSession session) {
        Clue clue = clueService.getClue(guessService.getGuesses(session).stream()
                .map(Guess::getClue)
                .collect(Collectors.toList())
        );
        List<Film> options = filmService.getOptions(clue);
        return new ModelAndView("Clue")
                .addObject("clue", clue)
                .addObject("options", options);
    }

    @PostMapping("/guess")
    public ModelAndView guess(HttpSession session, @RequestParam("option") Long filmId, @RequestParam("clueId") Long clueId) throws IncorrectClueIdException, IncorrectFilmIdException {
        Clue clue = clueService.getClue(clueId).orElseThrow(IncorrectClueIdException::new);
        Film film = filmService.getFilm(filmId).orElseThrow(IncorrectFilmIdException::new);
        Boolean isCorrect = guessService.guess(clue, film, session.getId());
        filmService.generationCheck(film);
        JSONObject clueFamilyTree = clueService.createClueFamilyTree(clue);
        return new ModelAndView("Guessed")
                .addObject("correct", isCorrect)
                .addObject("film", clue.getFilm())
                .addObject("clueFamilyTree", clueFamilyTree);
    }

}
