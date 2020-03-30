package uk.co.emg.entity;

public class FilmBuilder {
  private long id;
  private String title;
  private String posterPath;
  private String overview;

  public FilmBuilder setId(Long id) {
    this.id = id;
    return this;
  }

  public FilmBuilder setTitle(String title) {
    this.title = title;
    return this;
  }

  public FilmBuilder setPosterPath(String posterPath) {
    this.posterPath = posterPath;
    return this;
  }

  public FilmBuilder setOverview(String overview) {
    this.overview = overview;
    return this;
  }

  public Film createFilm() {
    return new Film(id, title, posterPath, overview);
  }
}