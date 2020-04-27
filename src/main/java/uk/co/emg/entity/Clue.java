package uk.co.emg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Clue {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Film film;

  @OneToMany(mappedBy = "clue")
  private List<ClueComponent> clueComponents;

  @OneToMany(mappedBy = "clue")
  private List<Guess> guesses;

  private Double fitness;

  private Integer generation;

  public Clue() {
  }

  public Clue(Film film) {
    this.film = film;
    this.generation = film.getGeneration();
  }

  public Clue(Film film, Integer generation) {
    this.film = film;
    this.generation = generation;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Film getFilm() {
    return film;
  }

  public void setFilm(Film film) {
    this.film = film;
  }

  public List<ClueComponent> getClueComponents() {
    return clueComponents;
  }

  public void setClueComponents(List<ClueComponent> clueComponents) {
    this.clueComponents = clueComponents;
  }

  public Double getFitness() {
    return fitness;
  }

  public void setFitness(Double fitness) {
    this.fitness = fitness;
  }

  public Integer getGeneration() {
    return generation;
  }

  private void setGeneration(Integer generation) {
    this.generation = generation;
  }

  public List<Guess> getGuesses() {
    return guesses;
  }

  public void setGuesses(List<Guess> guesses) {
    this.guesses = guesses;
  }

  @Override
  public int hashCode() {
    int result = film != null ? film.hashCode() : 0;
    result = 31 * result + (clueComponents != null ? clueComponents.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Clue clue = (Clue) o;

    if (!Objects.equals(film, clue.film)) return false;
    return Objects.equals(clueComponents, clue.clueComponents);
  }

  @Override
  public String toString() {
    return "Clue{" +
      "film=" + film +
      ", guesses=" + guesses +
      ", generation=" + generation +
      '}';
  }
}
