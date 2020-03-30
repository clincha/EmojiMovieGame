package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;

@Service
public class ClueService {
  private FilmService filmService;
  private EmojiService emojiService;

  public ClueService(FilmService filmService, EmojiService emojiService) {
    this.filmService = filmService;
    this.emojiService = emojiService;
  }

  public Clue getRandomClue() {
    Film film = filmService.getRandomFilm();
    Emoji[] emojis = emojiService.getEmojiBasedOnFilm(film);
    return new Clue(film, emojis);
  }
}
