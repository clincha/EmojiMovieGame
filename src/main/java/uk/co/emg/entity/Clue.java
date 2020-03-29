package uk.co.emg.entity;

import java.util.Arrays;
import java.util.HashMap;

public class Clue {

  private Film film;
  private int correctGuesses;
  private HashMap<String, Integer> incorrectGuesses;
  private Emoji[] emojis;

  public Clue(Film film) {
    this.film = film;
  }

  @Override
  public String toString() {
    return Arrays.toString(emojis);
  }
}
