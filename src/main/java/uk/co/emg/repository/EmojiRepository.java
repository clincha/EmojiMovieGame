package uk.co.emg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.emg.entity.Emoji;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EmojiRepository extends CrudRepository<Emoji, String> {

    ArrayList<Emoji> findEmojiBySlugContains(String searchTerm);

    List<Emoji> findAllByEmojiGroup(String emojiGroup);

    List<Emoji> findAllBySubGroup(String emojiSubGroup);
}
