package uk.co.emg.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;
import uk.co.emg.service.ClueService;
import uk.co.emg.service.FilmService;

@Component
public class BigBangEvent {

  public static final int INITIAL_POPULATION = 3;
  private static final Logger logger = LoggerFactory.getLogger(BigBangEvent.class);
  private FilmRepository filmRepository;
  private FilmService filmService;
  private ClueService clueService;

  public BigBangEvent(FilmRepository filmRepository, FilmService filmService, ClueService clueService) {
    this.filmRepository = filmRepository;
    this.filmService = filmService;
    this.clueService = clueService;
  }

  @EventListener(EmojiPopulationCompleteEvent.class)
  public void createPopulation() {
    if (!filmDataIsLoaded()) {
      logger.info("No film data, populating");
      loadFilmData();
    }
    logger.info("Creating clues");
    for (Film film : filmRepository.findAll()) {
      clueService.createClue(film);
    }
    logger.info("Populated fim and clue data");
  }

  private boolean filmDataIsLoaded() {
    return filmRepository.count() > 0;
  }

  private void loadFilmData() {
    filmRepository.saveAll(filmService.getFilms(INITIAL_POPULATION));
  }

}
