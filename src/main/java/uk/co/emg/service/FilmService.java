package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.co.emg.builder.FilmBuilder;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public String BASE_URL = "https://api.themoviedb.org/3/";
    @Value("${api.tmdb}")
    public String API_KEY;
    public static final int INITIAL_CLUE_GENERATION_SIZE = 10;
    public static final int GENERATION_GUESS_THRESHOLD = 5;
    public static final int FILM_GENERATION_SIZE = 3;

    private final ApiService apiService;
    private final ClueService clueService;
    private final FilmRepository filmRepository;

    public FilmService(ApiService apiService, FilmRepository filmRepository, ClueService clueService) {
        this.apiService = apiService;
        this.filmRepository = filmRepository;
        this.clueService = clueService;
    }

    public void preLoad() throws ParseException {
        if (filmRepository.count() == 0) {
            String rawJSON = apiService.makeApiRequest(BASE_URL + "discover/movie/?language=en-UK&sort_by=popularity.desc&api_key=" + API_KEY);
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

    public void generationCheck(Film film) {
        if (isGenerationComplete(film)) {
            List<Clue> newGenerationClues = createNewGeneration(film);
            film.addGeneration(newGenerationClues);
            filmRepository.save(film);
        }
    }

    public List<Clue> createNewGeneration(Film film) {
        List<Clue> clues = clueService.getAllClues(film)
                .stream()
                .filter(clue -> clue.getGeneration().equals(film.getGeneration()))
                .peek(clue -> clue.setFitness(clueService.calculateFitness(clue)))
                .peek(clueService::save)
                .sorted(Comparator.comparing(Clue::getFitness))
                .collect(Collectors.toList());
        ArrayList<Clue> newGenerationClues = new ArrayList<>();

        // Breeding
        for (int i = 1; i < 6; i++) newGenerationClues.add(clueService.breed(clues.get(0), clues.get(i)));
        newGenerationClues.add(clueService.breed(clues.get(1), clues.get(2)));
        newGenerationClues.add(clueService.breed(clues.get(2), clues.get(3)));

        // Direct
        newGenerationClues.addAll(clues.subList(0, 3).stream()
                .map(Clue::new)
                .peek(clueService::save)
                .collect(Collectors.toList()));
        return newGenerationClues;
    }

    public boolean isGenerationComplete(Film film) {
        final int filmGeneration = film.getGeneration();
        List<Clue> currentGenerationClues = clueService.getAllClues(film).stream()
                .filter(clue -> clue.getGeneration().equals(filmGeneration))
                .collect(Collectors.toList());

        if (currentGenerationClues.isEmpty())
            return false;

        boolean generationComplete = true;
        for (Clue clue : currentGenerationClues) {
            if (clue.getGuesses().size() < GENERATION_GUESS_THRESHOLD) {
                generationComplete = false;
                break;
            }
        }
        return generationComplete;
    }
}
