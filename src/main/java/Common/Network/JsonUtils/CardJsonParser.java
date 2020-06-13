package Common.Network.JsonUtils;

import Server.Game.Card.Card;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardJsonParser {
    private final JsonUtil jsonUtil = new JsonUtil();

    /**************************
     *  JSON utils for Cards  *
     **************************/
    public ArrayList<Card> parseJson(String file, ArrayList<Card> all) {
        ArrayList<Card> cards = new ArrayList<>();

        try {
            HashMap<Integer, Integer> indexQuantityList = new HashMap<>();

            JSONObject obj = new JSONObject(jsonUtil.getJsonContent(file));

            JSONArray arr = obj.getJSONArray("cards");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject card = arr.getJSONObject(i);

                int id = card.getInt("id");
                int cost = card.getInt("quantity");

                indexQuantityList.put(id, cost);

            }

            for(Map.Entry<Integer, Integer> entry : indexQuantityList.entrySet()) {
                int key = entry.getKey();
                int quantity = entry.getValue();
                for (Card c : all) {
                    if (c.getID() == key) {
                        for (int i = 0; i < quantity; i++) {
                            cards.add(c);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cards;
    }
}
