package uk.co.emg.entity;

import java.util.Arrays;
import java.util.HashMap;

public class Clue {

  private Film film;
  private int correctGuesses;
  private HashMap<Film, Integer> incorrectGuesses;
  private Emoji[] emojis;

  public Clue(Film film, Emoji[] emojis) {
    this.film = film;
    this.emojis = emojis;
    correctGuesses = 0;
    incorrectGuesses = new HashMap<>();
  }

  public Film getFilm() {
    return film;
  }

  public void setFilm(Film film) {
    this.film = film;
  }

  public int getCorrectGuesses() {
    return correctGuesses;
  }

  public void setCorrectGuesses(int correctGuesses) {
    this.correctGuesses = correctGuesses;
  }

  public HashMap<Film, Integer> getIncorrectGuesses() {
    return incorrectGuesses;
  }

  public void setIncorrectGuesses(HashMap<Film, Integer> incorrectGuesses) {
    this.incorrectGuesses = incorrectGuesses;
  }

  public Emoji[] getEmojis() {
    return emojis;
  }

  public void setEmojis(Emoji[] emojis) {
    this.emojis = emojis;
  }

  @Override
  public String toString() {
    return Arrays.toString(emojis);
  }
}
