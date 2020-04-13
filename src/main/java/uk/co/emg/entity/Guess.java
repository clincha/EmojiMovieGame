package uk.co.emg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Guess {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Clue clue;

  @ManyToOne
  private Film film;

  public Guess() {
  }

  public Guess(Clue clue, Film film) {
    this.clue = clue;
    this.film = film;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Clue getClue() {
    return clue;
  }

  public void setClue(Clue clue) {
    this.clue = clue;
  }

  public Film getFilm() {
    return film;
  }

  public void setFilm(Film userGuess) {
    this.film = userGuess;
  }

  public boolean isCorrect() {
    return clue.getFilm()
      .equals(film);
  }
}
