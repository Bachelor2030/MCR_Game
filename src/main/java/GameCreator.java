import Card.Card;
import ModelClasses.Client;
import Utils.CardsJsonParser;
import org.json.JSONException;

import java.util.ArrayList;

public class GameCreator extends Client {
    private ArrayList<Card> cards;

    public GameCreator(String fileAsString){

        try {
            cards = CardsJsonParser.parseJson(fileAsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
