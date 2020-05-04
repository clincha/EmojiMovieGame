package uk.co.emg.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Clue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Film film;

    @OneToMany(mappedBy = "clue", cascade = CascadeType.ALL)
    private List<ClueComponent> clueComponents;

    @OneToMany(mappedBy = "clue", cascade = CascadeType.ALL)
    private List<Guess> guesses;

    private Double fitness;

    private Integer generation;

    @ManyToOne(cascade = CascadeType.ALL)
    private Clue mother;

    @ManyToOne(cascade = CascadeType.ALL)
    private Clue father;

    @OneToOne(cascade = CascadeType.ALL)
    private Mutation mutation;

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

    public Clue(Clue clue) {
        this.film = clue.getFilm();
        this.clueComponents = clue.getClueComponents().stream()
                .map(clueComponent -> new ClueComponent(this, clueComponent.getEmoji()))
                .collect(Collectors.toList());
        this.generation = clue.getGeneration() + 1;
        this.mother = clue;
    }

    public Clue(long id, Film film) {
        this.id = id;
        this.film = film;
        this.generation = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clue clue = (Clue) o;
        return Objects.equals(id, clue.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Clue{" +
                "film=" + film +
                ", guesses=" + guesses +
                ", generation=" + generation +
                '}';
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

    public Clue getMother() {
        return mother;
    }

    public void setMother(Clue mother) {
        this.mother = mother;
    }

    public Clue getFather() {
        return father;
    }

    public void setFather(Clue father) {
        this.father = father;
    }

    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }
}
