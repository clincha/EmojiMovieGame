package uk.co.emg.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String posterPath;

    @Column(length = 100000)
    String overview;

    @OneToMany(mappedBy = "film")
    List<Clue> clues;

    Integer generation;

    public Film() {
    }

    public Film(long id, String title, String posterPath, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.generation = 0;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
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

    public void addGeneration(List<Clue> newGenerationClues) {
        generation++;
        clues.addAll(newGenerationClues);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + overview.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (!title.equals(film.title)) return false;
        if (posterPath != null ? !posterPath.equals(film.posterPath) : film.posterPath != null) return false;
        return overview.equals(film.overview);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                '}';
    }
}
