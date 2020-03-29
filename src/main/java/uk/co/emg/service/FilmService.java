package uk.co.emg.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.entity.Film;

@Service
public class FilmService {

  public static final String API_KEY = "https://api.themoviedb.org/3/movie/550?api_key=00fa70c9a0a3d46a9d2d76f0a9c395ea";
  private ApiService apiService;

  public FilmService(ApiService apiService) {
    this.apiService = apiService;
  }

  public Film getRandomFilm() {
    return parseTmdbJson();
  }

  private Film parseTmdbJson() {
    String rawJSON = apiService.makeApiRequest(API_KEY);
    JSONParser parser = new JSONParser();
    JSONObject movieData = null;

    try {
      movieData = (JSONObject) parser.parse(rawJSON);
      return new Film((String) movieData.get("title"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

}
