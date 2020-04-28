package uk.co.emg.entity;

import javax.persistence.*;

@Entity
public class ClueComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    Clue clue;

    @ManyToOne
    Emoji emoji;

    public ClueComponent() {
    }

    public ClueComponent(Clue clue, Emoji emoji) {
        this.clue = clue;
        this.emoji = emoji;
    }

    public ClueComponent(ClueComponent clueComponent) {
        this.clue = clueComponent.getClue();
        this.emoji = clueComponent.getEmoji();
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

    public Emoji getEmoji() {
        return emoji;
    }

    public void setEmoji(Emoji emoji) {
        this.emoji = emoji;
    }
}
