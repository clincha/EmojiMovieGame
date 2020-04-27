package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.builder.FilmBuilder;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
  public static final String BASE_URL = "https://api.themoviedb.org/3/";
  public static final String API_KEY = "api_key=00fa70c9a0a3d46a9d2d76f0a9c395ea";
  public static final int INITIAL_CLUE_GENERATION_SIZE = 10;
  public static final int GENERATION_GUESS_THRESHOLD = 10;
  public static final int FILM_GENERATION_SIZE = 3;

  private final ApiService apiService;
  private final FilmRepository filmRepository;
  private final ClueService clueService;

  public FilmService(ApiService apiService, FilmRepository filmRepository, ClueService clueService) {
    this.apiService = apiService;
    this.filmRepository = filmRepository;
    this.clueService = clueService;
  }

  public void preLoad() throws ParseException {
    if (filmRepository.count() == 0) {
      String rawJSON = apiService.makeApiRequest(BASE_URL + "discover/movie/?language=en-UK&sort_by=popularity.desc&" + API_KEY);
      JSONParser parser = new JSONParser();
      JSONObject response = (JSONObject) parser.parse(rawJSON);
      JSONArray results = (JSONArray) response.get("results");

      ArrayList<Film> films = new ArrayList<>(results.size());

      for (int i = 0; i < FILM_GENERATION_SIZE; i++) {
        JSONObject result = (JSONObject) results.get(i);
        films.add(new FilmBuilder()
          .setId((Long) (result).get("id"))
          .setTitle((String) result.get("title"))
          .setOverview((String) result.get("overview"))
          .setPosterPath((String) result.get("poster_path"))
          .createFilm());
      }
      filmRepository.saveAll(films);

      for (Film film : getPopularFilms()) {
        for (int i = 0; i < INITIAL_CLUE_GENERATION_SIZE; i++) {
          clueService.createClue(film);
        }
      }
    }
  }

  public List<Film> getOptions(Clue clue) {
    List<Film> films = getRandomFilmsExcludingFilm(clue.getFilm());
    films.add(clue.getFilm());
    Collections.shuffle(films);
    return films;
  }

  public ArrayList<Film> getPopularFilms() {
    ArrayList<Film> popularFilms = new ArrayList<>();
    filmRepository.findAll()
      .forEach(popularFilms::add);
    return popularFilms;
  }

  public List<Film> getRandomFilmsExcludingFilm(Film film) {
    ArrayList<Film> films = getPopularFilms();
    films.remove(film);
    return films;
  }

  public Optional<Film> getFilm(Long id) {
    return filmRepository.findById(id);
  }

  void generationCheck(Film film) {
    List<Clue> cluesInGeneration = clueService.getAllCluesInGeneration(film.getGeneration());
    boolean generationComplete = true;
    for (Clue clue : cluesInGeneration) {
      if (clue.getGuesses()
        .size() < GENERATION_GUESS_THRESHOLD) {
        generationComplete = false;
        break;
      }
    }
    if (generationComplete) {
      for (Clue clue : cluesInGeneration) {
        clueService.calculateFitness(clue);
      }
      List<Clue> clues = clueService.getFittestInGeneration(film.getGeneration());
      clueService.createClue(clues.get(0), clues.get(1));
    }
  }
}
