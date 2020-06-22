package uk.co.emg;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import uk.co.emg.controller.ClueController;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Guess;
import uk.co.emg.exception.IncorrectClueIdException;
import uk.co.emg.exception.IncorrectFilmIdException;
import uk.co.emg.repository.ClueRepository;
import uk.co.emg.repository.FilmRepository;
import uk.co.emg.repository.GuessRepository;
import uk.co.emg.service.FilmService;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class LifecycleTest {
    private static final int NUMBER_OF_GUESSES_IN_A_GENERATION = FilmService.FILM_GENERATION_SIZE * FilmService.CLUE_GENERATION_SIZE * FilmService.GENERATION_GUESS_THRESHOLD;
    @Autowired
    ClueController clueController;
    @Autowired
    FilmService filmService;
    @Autowired
    private GuessRepository guessRepository;
    @Autowired
    private ClueRepository clueRepository;
    private FilmRepository filmRepository;

    @Test
    @Transactional
    public void singleGuess() throws IncorrectFilmIdException, IncorrectClueIdException {
        HttpSession httpSession = new MockHttpSession();
        Random random = new Random();

        ModelAndView clue = clueController.clue(httpSession);
        clueController.guess(
                httpSession,
                filmService.getPopularFilms().get(random.nextInt(filmService.getPopularFilms().size())).getId(),
                ((Clue) (clue.getModel().get("clue"))).getId()
        );
    }

    @Test
    @Transactional
    public void generationGuess() throws IncorrectFilmIdException, IncorrectClueIdException {
        HttpSession httpSession = new MockHttpSession();
        Random random = new Random();
        for (int i = 0; i < FilmService.FILM_GENERATION_SIZE * FilmService.CLUE_GENERATION_SIZE * FilmService.GENERATION_GUESS_THRESHOLD; i++) {
            ModelAndView clue = clueController.clue(httpSession);
            clueController.guess(
                    httpSession,
                    filmService.getPopularFilms().get(random.nextInt(filmService.getPopularFilms().size())).getId(),
                    ((Clue) (clue.getModel().get("clue"))).getId()
            );
        }
        Assert.assertEquals(NUMBER_OF_GUESSES_IN_A_GENERATION, guessRepository.count());

    }

}
