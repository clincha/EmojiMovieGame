package uk.co.emg.entity;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClueComponent that = (ClueComponent) o;

        return Objects.equals(emoji, that.emoji);
    }

    @Override
    public int hashCode() {
        return emoji != null ? emoji.hashCode() : 0;
    }
}
