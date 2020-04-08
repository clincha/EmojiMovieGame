package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Emoji;

@Repository
public interface EmojiRepository extends CrudRepository<Emoji, String> {
}
