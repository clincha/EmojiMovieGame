package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.Guess;

import java.util.List;

@Repository
public interface GuessRepository extends CrudRepository<Guess, Long> {

    List<Guess> findAllByClue(Clue clue);

}
