package uk.co.emg.entity;

import uk.co.emg.enumeration.MutationType;

import javax.persistence.*;

@Entity
public class Mutation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Clue clue;

    private MutationType mutationType;

    public Mutation() {
    }

    public Mutation(Clue child, MutationType mutationType) {
        this.clue = child;
        this.mutationType = mutationType;
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

    public MutationType getMutationType() {
        return mutationType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }
}
