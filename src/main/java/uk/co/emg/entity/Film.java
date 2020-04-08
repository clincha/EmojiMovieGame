package uk.co.emg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Film {

  @Id
  Long id;

  String title;

  String posterPath;

  @Column(length = 100000)
  String overview;

  @OneToMany
  List<Clue> clues;

  public Film() {
  }

  public Film(long id, String title, String posterPath, String overview) {
    this.id = id;
    this.title = title;
    this.posterPath = posterPath;
    this.overview = overview;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public List<Clue> getClues() {
    return clues;
  }

  public void setClues(List<Clue> clues) {
    this.clues = clues;
  }

  @Override
  public String toString() {
    return "Film{" +
      "title='" + title + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Film film = (Film) o;

    if (!id.equals(film.id)) return false;
    return title.equals(film.title);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + title.hashCode();
    return result;
  }
}
