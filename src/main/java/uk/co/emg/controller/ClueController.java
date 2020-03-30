package uk.co.emg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.emg.service.ClueService;
import uk.co.emg.service.EmojiService;
import uk.co.emg.service.FilmService;

@Controller
public class ClueController {

  private EmojiService emojiService;
  private FilmService filmService;
  private ClueService clueService;

  public ClueController(EmojiService emojiService, FilmService filmService, ClueService clueService) {
    this.emojiService = emojiService;
    this.filmService = filmService;
    this.clueService = clueService;
  }

  @GetMapping("/emoji")
  public ModelAndView emoji() {
    return new ModelAndView("Emoji")
      .addObject("emoji", emojiService.getRandomEmoji());
  }

  @GetMapping("/movie")
  public ModelAndView movie() {
    return new ModelAndView("Movie")
      .addObject("movie", filmService.getRandomFilm());
  }

  @GetMapping("/clue")
  public ModelAndView clue() {
    return new ModelAndView("Clue")
      .addObject("clue", clueService.getRandomClue());
  }

}
