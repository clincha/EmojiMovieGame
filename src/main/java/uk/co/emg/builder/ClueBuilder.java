package uk.co.emg.builder;

import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.entity.Mutation;

import java.util.List;

public final class ClueBuilder {
    private Long id;
    private List<ClueComponent> clueComponents;
    private List<Guess> guesses;
    private Double fitness;
    private Clue mother;
    private Clue father;
    private Mutation mutation;
    private Film film;
    private Integer generation;

    public ClueBuilder() {
    }

    public static ClueBuilder aClue() {
        return new ClueBuilder();
    }

    public ClueBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ClueBuilder setClueComponents(List<ClueComponent> clueComponents) {
        this.clueComponents = clueComponents;
        return this;
    }

    public ClueBuilder setGuesses(List<Guess> guesses) {
        this.guesses = guesses;
        return this;
    }

    public ClueBuilder setFitness(Double fitness) {
        this.fitness = fitness;
        return this;
    }

    public ClueBuilder setMother(Clue mother) {
        this.mother = mother;
        return this;
    }

    public ClueBuilder setFather(Clue father) {
        this.father = father;
        return this;
    }

    public ClueBuilder setMutation(Mutation mutation) {
        this.mutation = mutation;
        return this;
    }

    public ClueBuilder setFilm(Film film) {
        this.film = film;
        return this;
    }

    public ClueBuilder setGeneration(Integer generation) {
        this.generation = generation;
        return this;
    }

    public Clue build() {
        Clue clue = new Clue(film, generation);
        clue.setId(id);
        clue.setClueComponents(clueComponents);
        clue.setGuesses(guesses);
        clue.setFitness(fitness);
        clue.setMother(mother);
        clue.setFather(father);
        clue.setMutation(mutation);
        return clue;
    }
}
