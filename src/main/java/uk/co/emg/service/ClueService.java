package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.ClueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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

    public Clue getClue(List<Clue> alreadySeenClues) {
        Random random = new Random();
        List<Clue> currentGenerationClues = clueRepository.findAllByFitnessIsNull();
        List<Clue> cluesNotYetSeen = currentGenerationClues.stream()
                .filter(clue -> !alreadySeenClues.contains(clue))
                .collect(Collectors.toList());
        if (cluesNotYetSeen.isEmpty()) {
            return currentGenerationClues.get(random.nextInt(currentGenerationClues.size()));
        }
        return cluesNotYetSeen.get(random.nextInt(cluesNotYetSeen.size()));
    }

    public double calculateFitness(Clue clue) {
        return guessService.getGuesses(clue).stream().collect(Collectors.averagingDouble(guess -> guess.isCorrect() ? 1 : 0));
    }

    public Clue breed(Clue mother, Clue father) {
        // TODO - Change this behaviour to set the mother and father of the clue
        Clue child = new Clue(mother.getFilm(), mother.getGeneration() + 1);
        ArrayList<ClueComponent> clueComponents = new ArrayList<>();
        clueComponents.addAll(mother.getClueComponents()
                .subList(0, mother.getClueComponents()
                        .size() / 2));
        clueComponents.addAll(father.getClueComponents()
                .subList(father.getClueComponents()
                        .size() / 2, father.getClueComponents()
                        .size()));
        if (clueComponents.size() == 0) {
            clueComponents.addAll(mother.getClueComponents());
            clueComponents.addAll(father.getClueComponents());
        }
        for (int i = 0; i < clueComponents.size(); i++) {
            clueComponents.set(i, new ClueComponent(child, clueComponents.get(i).getEmoji()));
        }
        child.setClueComponents(clueComponents);
        child = save(child);
        return child;
    }

    public Clue save(Clue clue) {
        clue = clueRepository.save(clue);
        clue.setClueComponents(clueComponentService.saveAll(clue.getClueComponents()));
        return clue;
    }

    public List<Clue> getAllClues(Film film) {
        return clueRepository.findAllByFilm(film);
    }
}
