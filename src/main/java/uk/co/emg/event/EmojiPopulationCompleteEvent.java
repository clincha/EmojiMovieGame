package uk.co.emg.event;

import org.springframework.context.ApplicationEvent;

public class EmojiPopulationCompleteEvent extends ApplicationEvent {
  public EmojiPopulationCompleteEvent(Object source) {
    super(source);
  }
}
