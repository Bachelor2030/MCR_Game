package Common;

import Server.Game.Card.Card;
import Server.Game.Utils.CardsJsonParser;

import java.util.ArrayList;

/**
 * Represents the client in the command model
 * This class creates the array of cards and links them with the correct command
 */
public class GameCreator {
    protected ArrayList<Card> cards;

    public GameCreator(String fileAsString){
        CardsJsonParser cjp = new CardsJsonParser(fileAsString);
        cards = cjp.getCards();
    }
}
