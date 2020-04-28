package uk.co.emg.utils;

import uk.co.emg.entity.*;
import uk.co.emg.service.FilmService;

import java.util.ArrayList;
import java.util.List;

public class FilmUtils {

    public static Film getFilm() {
        Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        List<Clue> clues = new ArrayList<>(FilmService.INITIAL_CLUE_GENERATION_SIZE);
        for (int i = 0; i < FilmService.INITIAL_CLUE_GENERATION_SIZE; i++) {
            Clue clue = new Clue(film);
            List<ClueComponent> clueComponents = new ArrayList<>();
            clueComponents.add(new ClueComponent(clue, new Emoji("smiling-face", "☺", "263A FE0F", "smileys-emotion", "face-affection", "smiling face")));
            clueComponents.add(new ClueComponent(clue, new Emoji("frowning-face", "☹", "2639 FE0F", "smileys-emotion", "face-concerned", "frowning face")));
            clue.setClueComponents(clueComponents);
            ArrayList<Guess> guesses = new ArrayList<>();
            for (int j = 0; j < FilmService.GENERATION_GUESS_THRESHOLD; j++) {
                guesses.add(j, new Guess(clue, film, "1234"));
            }
            clue.setGuesses(guesses);
            clues.add(clue);
        }
        film.setClues(clues);
        return film;
    }
}
