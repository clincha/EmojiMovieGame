package uk.co.emg.entity;

public class Film {

  long id;
  String title;
  String posterPath;
  String overview;

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
