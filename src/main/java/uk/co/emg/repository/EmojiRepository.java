package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;

import java.util.ArrayList;

@Repository
public interface EmojiRepository extends CrudRepository<Emoji, String> {

  ArrayList<Emoji> findEmojiBySlugContains(String searchTerm);

}
