package ModelClasses;

import Card.Card;
import org.json.JSONException;

import java.util.ArrayList;

import static Card.Utils.CardsJsonParser.parseJson;

/**
 * Represents the client in the command model
 * This class creates the array of cards and links them with the correct command
 */
public class GameClient {
    protected ArrayList<Card> cards;

    public GameClient(String fileAsString){
        try {
            cards = parseJson(fileAsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
