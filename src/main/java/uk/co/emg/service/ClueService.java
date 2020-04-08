package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.ClueComponentRepository;
import uk.co.emg.repository.ClueRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClueService {
  public final int CLUE_GENERATION_SIZE = 10;
  private EmojiService emojiService;
  private FilmService filmService;
  private ClueRepository clueRepository;
  private ClueComponentRepository clueComponentRepository;

  public ClueService(EmojiService emojiService, ClueRepository clueRepository, FilmService filmService, ClueComponentRepository clueComponentRepository) {
    this.emojiService = emojiService;
    this.clueRepository = clueRepository;
    this.filmService = filmService;
    this.clueComponentRepository = clueComponentRepository;
  }

  public void preLoad() {
    if (clueRepository.count() == 0) {
      for (Film film : filmService.getPopularFilms()) {
        for (int i = 0; i < CLUE_GENERATION_SIZE; i++) {
          clueRepository.save(createClue(film));
        }
      }
    }
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
    List<Clue> clues = getAllClues();
    Collections.shuffle(clues);
    return clues.get(0);
  }

  private List<Clue> getAllClues() {
    ArrayList<Clue> clues = new ArrayList<>();
    clueRepository.findAll().forEach(clues::add);
    return clues;
  }

  public List<Film> getOptions(Clue clue) {
    List<Film> films = filmService.getRandomFilmsExcludingFilm(clue.getFilm());
    films.add(clue.getFilm());
    Collections.shuffle(films);
    return films;
  }

  public Optional<Clue> getClue(Long clueId) {
    return clueRepository.findById(clueId);
  }

  public Boolean guess(Clue clue, String option) {
    return clue.getFilm().getTitle().equals(option);
  }

}
