package uk.co.emg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
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

  public Clue() {
  }

  public Clue(Film film) {
    this.film = film;

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
}
