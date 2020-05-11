package Game.Utils;

import Card.Card;
import Card.CardType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CardsJsonParser {
    private String json;
    private ArrayList<Card> cards = new ArrayList<>();
    private HashMap<String, String> commands = new HashMap<>();

    public CardsJsonParser(String json) {
        this.json = json;
        try {
            parseJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJson() throws JSONException {
        JSONObject obj = new JSONObject(json);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println(pageName);

        JSONArray arr = obj.getJSONArray("cards");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject card = arr.getJSONObject(i);

            String name = card.getString("name");
            CardType type = CardType.getType(card.getString("type"));
            int cost = card.getInt("cost");

            commands.put("", null);

            cards.add(new Card(name, type, cost));
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
