package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Film;

import java.util.Optional;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {

    Optional<Film> findByTitleEquals(String title);

}
