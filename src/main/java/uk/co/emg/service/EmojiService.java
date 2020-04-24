package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.builder.EmojiBuilder;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.EmojiRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmojiService {

  public static final String ACCESS_KEY = "access_key=d41710af6f06cd316334a2a8e337b984bddbb23f";
  private static final String EMOJI_API_URL = "https://emoji-api.com";
  private final ApiService apiService;
  private final EmojiRepository emojiRepository;

  public EmojiService(ApiService apiService, EmojiRepository emojiRepository) {
    this.apiService = apiService;
    this.emojiRepository = emojiRepository;
  }

  /**
   * Check to see if there is emoji data in the database.
   * Get data from API if there isn't any data
   *
   * @throws Exception preload failed
   */
  public void preLoad() throws Exception {
    if (emojiRepository.count() == 0) {
      String rawJSON = apiService.makeApiRequest(EMOJI_API_URL + "/emojis?" + ACCESS_KEY);
      List<Emoji> emojis = parseOpenEmojiResponse(rawJSON);
      emojiRepository.saveAll(emojis);
    }
  }

  public List<Emoji> getEmojiBasedOnFilm(Film film) {
    ArrayList<Emoji> emojis = new ArrayList<>();
    for (String filmTitleWord : film.getTitle()
      .split(" ")) {
      ArrayList<Emoji> searchResults = emojiRepository.findEmojiBySlugContains(filmTitleWord);
      if (searchResults.isEmpty()) {
        emojiRepository.findAll()
          .forEach(searchResults::add);
      }
      Collections.shuffle(searchResults);
      emojis.add(searchResults.get(0));
    }
    return emojis;
  }

  private List<Emoji> parseOpenEmojiResponse(String rawJSON) throws ParseException {
    JSONParser parser = new JSONParser();

    JSONArray allEmojiData = (JSONArray) parser.parse(rawJSON);

    ArrayList<Emoji> emojis = new ArrayList<>(allEmojiData.size());

    for (Object emojiJSONObject : allEmojiData) {
      JSONObject emojiJSON = (JSONObject) emojiJSONObject;
      Emoji emoji = new EmojiBuilder()
        .setSlug(emojiJSON.get("slug")
          .toString())
        .setCharacter(emojiJSON.get("character")
          .toString())
        .setUnicodeName(emojiJSON.get("unicodeName")
          .toString())
        .setCodePoint(emojiJSON.get("codePoint")
          .toString())
        .setGroup(emojiJSON.get("group")
          .toString())
        .setSubGroup(emojiJSON.get("subGroup")
          .toString())
        .build();
      emojis.add(emoji);
    }
    return emojis;
  }

}
