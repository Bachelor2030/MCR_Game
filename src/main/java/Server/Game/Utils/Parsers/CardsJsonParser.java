package Server.Game.Utils.Parsers;

import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.Card.Commands.CommandName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsJsonParser {
    public static ArrayList<Card> parseJson(String json) throws JSONException {
        ArrayList<Card> cards = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        JSONArray arr = obj.getJSONArray("cards");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject card = arr.getJSONObject(i);

            int id = card.getInt("id");
            String name = card.getString("name");
            CardType type = CardType.getType(card.getString("type"));
            int cost = card.getInt("cost");

            JSONArray commandsJSON = card.getJSONArray("commands");
            CommandName[] commandNames = new CommandName[commandsJSON.length()];
            for (int index = 0; index < commandsJSON.length(); ++index) {
                commandNames[index] = CommandName.getCommand(commandsJSON.getString(index));
            }

            cards.add(new Card(id, name, type, cost, commandNames));
        }

        return cards;
    }

}
