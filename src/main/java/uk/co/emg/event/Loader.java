package uk.co.emg.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.co.emg.repository.ClueRepository;
import uk.co.emg.repository.FilmRepository;
import uk.co.emg.service.ClueService;
import uk.co.emg.service.EmojiService;
import uk.co.emg.service.FilmService;

@Component
public class Loader {

  public static final int INITIAL_POPULATION = 3;
  private static final Logger logger = LoggerFactory.getLogger(Loader.class);
  private FilmService filmService;
  private ClueService clueService;
  private EmojiService emojiService;

  public Loader(FilmService filmService, ClueService clueService, EmojiService emojiService) {
    this.filmService = filmService;
    this.clueService = clueService;
    this.emojiService = emojiService;
  }

  @EventListener(ApplicationStartedEvent.class)
  public void createPopulation() throws Exception {
    logger.info("Application preload has begun");

    emojiService.preLoad();

    logger.info("Emoji preload complete");

    filmService.preLoad();

    logger.info("Film preload complete");

    clueService.preLoad();

    logger.info("Clue preload complete");

    logger.info("Application preload finished");
  }

}
