package uk.co.emg.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Film {

  @Id
  Long id;

  String title;

  String posterPath;

  @Column(length = 100000)
  String overview;

  public Film() {
  }

  public Film(long id, String title, String posterPath, String overview) {
    this.id = id;
    this.title = title;
    this.posterPath = posterPath;
    this.overview = overview;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "Film{" +
      "title='" + title + '\'' +
      '}';
  }
}
