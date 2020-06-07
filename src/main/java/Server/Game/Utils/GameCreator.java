package Server.Game.Utils;

import Server.Game.Card.Card;
import Server.Game.Utils.Parsers.CardsJsonParser;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * Represents the Client in the command model
 * This class creates the array of cards and links them with the correct command
 */
public class GameCreator {
    protected ArrayList<Card> cards;

    public GameCreator(String fileAsString){
        try {
            cards = CardsJsonParser.parseJson(fileAsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
