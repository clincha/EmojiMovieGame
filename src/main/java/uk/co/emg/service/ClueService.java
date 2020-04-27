package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;
import uk.co.emg.entity.Guess;
import uk.co.emg.exception.NoCluesException;
import uk.co.emg.repository.ClueRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClueService {
  private final EmojiService emojiService;
  private final ClueComponentService clueComponentService;
  private final GuessService guessService;
  private final ClueRepository clueRepository;

  public ClueService(EmojiService emojiService, ClueComponentService clueComponentService, GuessService guessService, ClueRepository clueRepository) {
    this.emojiService = emojiService;
    this.clueRepository = clueRepository;
    this.clueComponentService = clueComponentService;
    this.guessService = guessService;
  }

  public void createClue(Film film) {
    Clue clue = new Clue(film);
    List<Emoji> emojis = emojiService.getEmojiBasedOnFilm(film);
    ArrayList<ClueComponent> clueComponents = new ArrayList<>(emojis.size());
    for (Emoji emoji : emojis) {
      clueComponents.add(new ClueComponent(clue, emoji));
    }
    clue.setClueComponents(clueComponents);
    clueRepository.save(clue);
    clueComponentService.saveAll(clueComponents);
  }

  public Optional<Clue> getClue(Long clueId) {
    return clueRepository.findById(clueId);
  }

  public Clue getClue() throws NoCluesException {
    return clueRepository.findAllByFitnessIsNull()
      .stream()
      .min(Comparator.comparing(clue -> guessService.getGuesses(clue)
        .size()))
      .orElseThrow(NoCluesException::new);
  }

  public double calculateFitness(Clue clue) {
    return guessService.getGuesses(clue)
      .stream()
      .map(Guess::isCorrect)
      .collect(Collectors.averagingDouble(value -> value ? 1 : 0));
  }

  public Clue breed(Clue mother, Clue father) {
    Clue child = new Clue(mother.getFilm(), mother.getGeneration() + 1);
    ArrayList<ClueComponent> clueComponents = new ArrayList<>();
    clueComponents.addAll(mother.getClueComponents()
      .subList(0, mother.getClueComponents()
        .size() / 2));
    clueComponents.addAll(father.getClueComponents()
      .subList(father.getClueComponents()
        .size() / 2, father.getClueComponents()
        .size()));
    child.setClueComponents(clueComponents);
    child = save(child);
    clueComponentService.saveAll(child.getClueComponents());
    return child;
  }

  public Clue save(Clue clue) {
    clue = clueRepository.save(clue);
    clueComponentService.saveAll(clue.getClueComponents());
    return clue;
  }

  public List<Clue> getAllClues(Film film) {
    return clueRepository.findAllByFilm(film);
  }
}
