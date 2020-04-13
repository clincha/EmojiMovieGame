package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.builder.FilmBuilder;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
  public static final String BASE_URL = "https://api.themoviedb.org/3/";
  public static final String API_KEY = "api_key=00fa70c9a0a3d46a9d2d76f0a9c395ea";
  public static final int FILM_GENERATION_SIZE = 3;

  private final ApiService apiService;
  private final FilmRepository filmRepository;

  public FilmService(ApiService apiService, FilmRepository filmRepository) {
    this.apiService = apiService;
    this.filmRepository = filmRepository;
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
    }
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
}
