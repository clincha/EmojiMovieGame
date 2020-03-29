package uk.co.emg.entity;

public class EmojiBuilder {
  private String slug;
  private String character;
  private String unicodeName;
  private String codePoint;
  private String group;
  private String subGroup;

  public EmojiBuilder setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public EmojiBuilder setCharacter(String character) {
    this.character = character;
    return this;
  }

  public EmojiBuilder setUnicodeName(String unicodeName) {
    this.unicodeName = unicodeName;
    return this;
  }

  public EmojiBuilder setCodePoint(String codePoint) {
    this.codePoint = codePoint;
    return this;
  }

  public EmojiBuilder setGroup(String group) {
    this.group = group;
    return this;
  }

  public EmojiBuilder setSubGroup(String subGroup) {
    this.subGroup = subGroup;
    return this;
  }

  public Emoji build() {
    return new Emoji(slug, character, unicodeName, codePoint, group, subGroup);
  }
}