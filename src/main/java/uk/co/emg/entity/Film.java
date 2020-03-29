package uk.co.emg.entity;

public class Film {

  String title;

  public Film(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "Film{" +
      "title='" + title + '\'' +
      '}';
  }
}
