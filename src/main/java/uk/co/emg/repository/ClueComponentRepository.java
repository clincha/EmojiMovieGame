package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.ClueComponent;

@Repository
public interface ClueComponentRepository extends CrudRepository<ClueComponent, Long> {
}
