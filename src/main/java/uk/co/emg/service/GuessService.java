package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.repository.GuessRepository;

@Service
public class GuessService {
  private final GuessRepository guessRepository;

  public GuessService(GuessRepository guessRepository) {
    this.guessRepository = guessRepository;
  }

  public boolean guess(Clue clue, Film film) {
    Guess guess = new Guess(clue, film);
    guessRepository.save(guess);
    return guess.isCorrect();
  }
}
