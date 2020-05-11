import Card.Card;
import org.json.JSONException;

import java.util.ArrayList;

import static Utils.CardsJsonParser.parseJson;

/**
 * Represents the client in the command model
 * This class creates the array of cards and links them with the correct command
 */
public class GameCreator {
    protected ArrayList<Card> cards;

    public GameCreator(String fileAsString){

        try {
            cards = parseJson(fileAsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
