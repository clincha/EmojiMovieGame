package uk.co.emg.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.co.emg.repository.EmojiRepository;
import uk.co.emg.service.EmojiService;

import java.util.Arrays;

@Component
public class EmojiPopulateEvent {

  Logger logger = LoggerFactory.getLogger(EmojiPopulateEvent.class);
  private EmojiRepository emojiRepository;
  private EmojiService emojiService;
  private ApplicationEventPublisher applicationEventPublisher;

  public EmojiPopulateEvent(EmojiRepository emojiRepository, EmojiService emojiService, ApplicationEventPublisher applicationEventPublisher) {
    this.emojiRepository = emojiRepository;
    this.emojiService = emojiService;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void getEmojiData() {
    if (!emojiDataPopulated()) {
      logger.info("No emoji data, populating");
      populateEmojiData();
      logger.info("Populated emoji data successful");
    }
    applicationEventPublisher.publishEvent(new EmojiPopulationCompleteEvent(this));
  }

  private boolean emojiDataPopulated() {
    return emojiRepository.count() > 0;
  }

  private void populateEmojiData() {
    emojiRepository.saveAll(Arrays.asList(emojiService.getAllEmojis()));
  }

}
