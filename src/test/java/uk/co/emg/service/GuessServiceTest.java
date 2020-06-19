package uk.co.emg.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import uk.co.emg.entity.Guess;
import uk.co.emg.repository.GuessRepository;
import uk.co.emg.utils.ClueUtils;
import uk.co.emg.utils.FilmUtils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
class GuessServiceTest {

    private final GuessService guessService;

    @Mock
    private final GuessRepository guessRepository = Mockito.mock(GuessRepository.class);

    public GuessServiceTest() {
        this.guessService = new GuessService(guessRepository);
    }

    @Test
    void guess() {
        Guess guess = new Guess(ClueUtils.getClue(), FilmUtils.getFilm(), "test");
        when(guessRepository.save(guess)).thenReturn(guess);
        var test = guessService.guess(ClueUtils.getClue(), FilmUtils.getFilm(), "test");
        assertTrue(test);
    }
}