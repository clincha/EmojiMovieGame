package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Clue;

import java.util.List;

@Repository
public interface ClueRepository extends CrudRepository<Clue, Long> {
  List<Clue> findAllByGeneration(int generation);

  List<Clue> findAllByGenerationOrderByFitnessDesc(Integer generation);
}
