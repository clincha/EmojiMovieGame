package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.EmojiBuilder;
import uk.co.emg.entity.Film;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Service
public class EmojiService {

  public static final String ACCESS_KEY = "access_key=d41710af6f06cd316334a2a8e337b984bddbb23f";
  private static final String EMOJI_API_URL = "https://emoji-api.com";
  private ApiService apiService;

  public EmojiService(ApiService apiService) {
    this.apiService = apiService;
  }

  public Emoji[] getEmojiBasedOnFilm(Film film) {
    String[] filmTitleWords = film.getTitle().split(" ");
    Emoji[] emojis = new Emoji[filmTitleWords.length];
    for (int i = 0; i < filmTitleWords.length; i++) {
      emojis[i] = search(filmTitleWords[i]).orElse(null);
    }
    return emojis;
  }

  private Optional<Emoji> search(String word) {
    String rawJSON = apiService.makeApiRequest(EMOJI_API_URL + "/emojis?search=" + word + "&" + ACCESS_KEY);
    try {
      Emoji[] emojis = parseOpenEmojiResponse(rawJSON);
      System.out.println(Arrays.toString(emojis));
      return Optional.of(emojis[0]);
    } catch (Exception e){
      return Optional.empty();
    }
  }

  public Emoji getRandomEmoji() {
    Random random = new Random();
    Emoji[] emojis = getAllEmojis();
    return emojis[random.nextInt(emojis.length)];
  }

  private Emoji[] getAllEmojis() {
    String rawJSON = apiService.makeApiRequest(EMOJI_API_URL + "/emojis?" + ACCESS_KEY);
    return parseOpenEmojiResponse(rawJSON);
  }

  public Emoji[] parseOpenEmojiResponse(String rawJSON) {
    JSONParser parser = new JSONParser();
    JSONArray allEmojiData = null;

    try {
      allEmojiData = (JSONArray) parser.parse(rawJSON);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Emoji[] emojis = new Emoji[allEmojiData == null ? 0 : allEmojiData.size()];

    for (int i = 0; i < emojis.length; i++) {
      JSONObject emojiJSON = (JSONObject) allEmojiData.get(i);
      Emoji emoji = new EmojiBuilder()
        .setSlug(emojiJSON.get("slug").toString())
        .setCharacter(emojiJSON.get("character").toString())
        .setUnicodeName(emojiJSON.get("unicodeName").toString())
        .setCodePoint(emojiJSON.get("codePoint").toString())
        .setGroup(emojiJSON.get("group").toString())
        .setSubGroup(emojiJSON.get("subGroup").toString())
        .build();
      emojis[i] = emoji;
    }
    return emojis;
  }

}
