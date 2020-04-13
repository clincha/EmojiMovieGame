package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Guess;

@Repository
public interface GuessRepository extends CrudRepository<Guess, Long> {
}
