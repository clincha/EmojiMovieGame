package uk.co.emg.entity;

import javax.persistence.*;

@Entity
public class Guess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Clue clue;

    @ManyToOne
    private Film film;

    private String sessionId;

    public Guess() {
    }

    public Guess(Clue clue, Film film, String session) {
        this.clue = clue;
        this.film = film;
        this.sessionId = session;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isCorrect() {
        return clue.getFilm()
                .equals(film);
    }
}
