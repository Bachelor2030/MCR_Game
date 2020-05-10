package Utils;

import Card.TrapCard;
import Card.Card;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsJsonParser {
    public static ArrayList<Card> parseJson(String json) throws JSONException {
        ArrayList<Card> cards = new ArrayList<Card>();

        JSONObject obj = new JSONObject(json);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println(pageName);

        JSONArray arr = obj.getJSONArray("cards");
        for (int i = 0; i < arr.length(); i++) {
            String name = arr.getJSONObject(i).getString("name");
            // TODO set all card variables correctly

            cards.add(new TrapCard());
            System.out.println(name);
        }

        return cards;
    }
}
