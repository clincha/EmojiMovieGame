package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Clue;

@Repository
public interface ClueRepository extends CrudRepository<Clue, Long> {
}
