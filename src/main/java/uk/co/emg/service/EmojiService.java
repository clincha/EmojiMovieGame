package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.EmojiBuilder;

import java.util.Random;

@Service
public class EmojiService {

  public static final String ACCESS_KEY = "access_key=d41710af6f06cd316334a2a8e337b984bddbb23f";
  private static final String EMOJI_API_URL = "https://emoji-api.com";
  private ApiService apiService;

  public EmojiService(ApiService apiService) {
    this.apiService = apiService;
  }

  public Emoji getRandomEmoji() {
    Random random = new Random();
    Emoji[] emojis = getAllEmojis();
    return emojis[random.nextInt(emojis.length)];
  }

  private Emoji[] getAllEmojis() {
    String rawJSON = apiService.makeApiRequest(EMOJI_API_URL + "/emojis?" + ACCESS_KEY);
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
