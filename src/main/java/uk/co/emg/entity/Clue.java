package uk.co.emg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

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

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Clue clue = (Clue) o;

    return id.equals(clue.id);
  }

  private Integer getGeneration() {
    return generation;
  }

  private void setGeneration(Integer generation) {
    this.generation = generation;
  }

  public List<Guess> getGuesses() {
    return guesses;
  }

  void setGuesses(List<Guess> guesses) {
    this.guesses = guesses;
  }
}
