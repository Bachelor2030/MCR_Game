package Server.Game.Utils;

import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.Card.Commands.CommandName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsJsonParser {
    private String json;
    private ArrayList<Card> cards = new ArrayList<>();

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

            JSONArray commands = card.getJSONArray("commands");
            CommandName[] commandNames = new CommandName[commands.length()];
            for (int j = 0; j < commandNames.length; ++j) {
                commandNames[j] = CommandName.getCommandName((String) commands.get(j));
            }

            cards.add(new Card(name, type, cost, commandNames));
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
