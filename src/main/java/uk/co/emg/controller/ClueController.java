package uk.co.emg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Film;
import uk.co.emg.service.ClueService;

import java.util.List;

@Controller
public class ClueController {

  private ClueService clueService;

  public ClueController(ClueService clueService) {
    this.clueService = clueService;
  }

  @GetMapping("/clue")
  public ModelAndView clue() {
    Clue clue = clueService.getClue();
    List<Film> options = clueService.getOptions(clue);
    return new ModelAndView("Clue")
      .addObject("clue", clue)
      .addObject("options", options);
  }

  @PostMapping("/guess")
  public ModelAndView guess(@RequestParam("option") String option, @RequestParam("clueId") Long clueId) {
    Clue clue = clueService.getClue(clueId).orElseThrow();
    return new ModelAndView("Guessed")
      .addObject("clue", clue)
      .addObject("correct", clueService.guess(clue, option));
  }

}
