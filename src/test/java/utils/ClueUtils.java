package utils;

import uk.co.emg.entity.*;
import uk.co.emg.service.FilmService;

import java.util.ArrayList;
import java.util.List;

public class ClueUtils {
    public static Clue getClue() {
        Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        Clue clue = new Clue(1, film);
        clue.setClueComponents(List.of(
                new ClueComponent(clue, new Emoji("smiling-face", "☺", "263A FE0F", "smileys-emotion", "face-affection", "smiling face")),
                new ClueComponent(clue, new Emoji("frowning-face", "☹", "2639 FE0F", "smileys-emotion", "face-concerned", "frowning face"))
        ));
        ArrayList<Guess> guesses = new ArrayList<>();
        for (int i = 0; i < FilmService.GENERATION_GUESS_THRESHOLD; i++) {
            guesses.add(i, new Guess(clue, film));
        }
        clue.setGuesses(guesses);
        return clue;
    }

    public static List<Clue> getClues() {
        List<Clue> clues = new ArrayList<>(FilmService.INITIAL_CLUE_GENERATION_SIZE);
        for (int i = 0; i < FilmService.INITIAL_CLUE_GENERATION_SIZE; i++) {
            clues.add(getClue());
        }
        return clues;
    }
}
