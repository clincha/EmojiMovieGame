package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.ClueComponentRepository;
import uk.co.emg.repository.ClueRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClueService {
  private EmojiService emojiService;
  private ClueRepository clueRepository;
  private FilmService filmService;
  private ClueComponentRepository clueComponentRepository;

  public ClueService(EmojiService emojiService, ClueRepository clueRepository, FilmService filmService, ClueComponentRepository clueComponentRepository) {
    this.emojiService = emojiService;
    this.clueRepository = clueRepository;
    this.filmService = filmService;
    this.clueComponentRepository = clueComponentRepository;
  }

  public Clue createClue(Film film) {
    Clue clue = new Clue(film);

    //Create clue components
    List<Emoji> emojis = emojiService.getEmojiBasedOnFilm(film);
    ArrayList<ClueComponent> clueComponents = new ArrayList<>(emojis.size());
    for (Emoji emoji : emojis) {
      clueComponents.add(new ClueComponent(clue, emoji));
    }
    clue.setClueComponents(clueComponents);
    clue = clueRepository.save(clue);
    clueComponentRepository.saveAll(clueComponents);
    return clue;
  }

  public Clue getClue() {
    return clueRepository.findById(1L).orElseGet(() -> createClue(filmService.getRandomFilm()));
  }
}
