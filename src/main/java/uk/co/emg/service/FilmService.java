package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.builder.FilmBuilder;
import uk.co.emg.entity.Film;

import java.util.Arrays;
import java.util.Random;

@Service
public class FilmService {
  public static final String BASE_URL = "https://api.themoviedb.org/3/";
  public static final String API_KEY = "api_key=00fa70c9a0a3d46a9d2d76f0a9c395ea";
  private ApiService apiService;

  public FilmService(ApiService apiService) {
    this.apiService = apiService;
  }

  public Film getRandomFilmExcluding() {
    Random random = new Random();
    Film[] films = getPopularMovies();
    return films[random.nextInt(films.length)];
  }

  public Film[] getPopularMovies() {
    String rawJSON = apiService.makeApiRequest(BASE_URL + "movie/popular?" + API_KEY);
    return parseTheMovieDatabaseJson(rawJSON);
  }

  private Film[] parseTheMovieDatabaseJson(String rawJSON) {
    JSONParser parser = new JSONParser();
    try {
      JSONObject response = (JSONObject) parser.parse(rawJSON);
      JSONArray results = (JSONArray) response.get("results");
      Film[] films = new Film[results.size()];
      for (int i = 0; i < films.length; i++) {
        films[i] = new FilmBuilder()
          .setId((Long) ((JSONObject) results.get(i)).get("id"))
          .setTitle((String) ((JSONObject) results.get(i)).get("title"))
          .setOverview((String) ((JSONObject) results.get(i)).get("overview"))
          .setPosterPath((String) ((JSONObject) results.get(i)).get("poster_path"))
          .createFilm();
      }
      return films;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Iterable<Film> getFilms(int number) {
    return Arrays.asList(getPopularMovies()).subList(0, number);
  }

  public Film getRandomFilmExcluding(Film film) {
    Film chosen = film;
    while (film == chosen) {
      chosen = getRandomFilmExcluding();
    }
    return chosen;
  }
}
