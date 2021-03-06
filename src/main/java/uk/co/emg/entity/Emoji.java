package uk.co.emg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Emoji {

    @Id
    private String slug;

    private String character;

    private String unicodeName;

    private String codePoint;

    private String emojiGroup;

    private String subGroup;

    public Emoji() {
    }

    public Emoji(String slug, String character, String unicodeName, String codePoint, String emojiGroup, String subGroup) {
        this.slug = slug;
        this.character = character;
        this.unicodeName = unicodeName;
        this.codePoint = codePoint;
        this.emojiGroup = emojiGroup;
        this.subGroup = subGroup;
    }

    @Override
    public String toString() {
        return character;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getUnicodeName() {
        return unicodeName;
    }

    public void setUnicodeName(String unicodeName) {
        this.unicodeName = unicodeName;
    }

    public String getCodePoint() {
        return codePoint;
    }

    public void setCodePoint(String codePoint) {
        this.codePoint = codePoint;
    }

    public String getEmojiGroup() {
        return emojiGroup;
    }

    public void setEmojiGroup(String group) {
        this.emojiGroup = group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emoji emoji = (Emoji) o;
        return slug.equals(emoji.slug) &&
                character.equals(emoji.character) &&
                unicodeName.equals(emoji.unicodeName) &&
                Objects.equals(codePoint, emoji.codePoint) &&
                emojiGroup.equals(emoji.emojiGroup) &&
                Objects.equals(subGroup, emoji.subGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, character, unicodeName, codePoint, emojiGroup, subGroup);
    }
}
