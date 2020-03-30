package uk.co.emg.entity;

public class Emoji {

  private String slug;
  private String character;
  private String unicodeName;
  private String codePoint;
  private String group;
  private String subGroup;

  public Emoji(String slug, String character, String unicodeName, String codePoint, String group, String subGroup) {
    this.slug = slug;
    this.character = character;
    this.unicodeName = unicodeName;
    this.codePoint = codePoint;
    this.group = group;
    this.subGroup = subGroup;
  }

  @Override
  public String toString() {
    return getSlug() + ":" + getCharacter();
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

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getSubGroup() {
    return subGroup;
  }

  public void setSubGroup(String subGroup) {
    this.subGroup = subGroup;
  }
}
