package uk.co.emg.entity;

import uk.co.emg.enumeration.MutationType;

import javax.persistence.*;

@Entity
public class Mutation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Clue clue;

    private MutationType mutationType;

}
