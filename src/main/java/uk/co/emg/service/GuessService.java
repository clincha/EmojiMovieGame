package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.repository.GuessRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class GuessService {
    private final GuessRepository guessRepository;

    public GuessService(GuessRepository guessRepository) {
        this.guessRepository = guessRepository;
    }

    public boolean guess(Clue clue, Film film, String sessionId) {
        Guess guess = new Guess(clue, film, sessionId);
        guessRepository.save(guess);
        return guess.isCorrect();
    }

    public List<Guess> getGuesses(Clue clue) {
        return guessRepository.findAllByClue(clue);
    }

    public List<Guess> getGuesses(HttpSession session) {
        return guessRepository.findAllBySessionId(session.getId());
    }
}
