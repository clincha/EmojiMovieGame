package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.emg.entity.Mutation;

public interface MutationRepository extends CrudRepository<Mutation, Long> {
}
