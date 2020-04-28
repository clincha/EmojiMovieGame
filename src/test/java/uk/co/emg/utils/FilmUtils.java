package uk.co.emg.utils;

import uk.co.emg.entity.Film;
import utils.ClueUtils;

public class FilmUtils {

    public static Film getFilm() {
        Film film = new Film(1, "Test Title", "Test Poster Path", "Test Overview");
        film.setClues(ClueUtils.getClues());
        return film;
    }
}
