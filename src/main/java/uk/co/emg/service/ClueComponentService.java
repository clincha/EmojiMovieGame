package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.repository.ClueComponentRepository;

import java.util.ArrayList;

@Service
public class ClueComponentService {
  private final ClueComponentRepository clueRepository;

  public ClueComponentService(ClueComponentRepository clueRepository) {
    this.clueRepository = clueRepository;
  }

  public void saveAll(ArrayList<ClueComponent> clueComponents) {
    clueRepository.saveAll(clueComponents);
  }
}
