package Server.Game.ModelClasses;

import Server.Game.Card.Card;
import Server.Game.Utils.CardsJsonParser;

import java.util.ArrayList;

/**
 * Represents the Client in the command model
 * This class creates the array of cards and links them with the correct command
 */
public class GameClient {
    protected ArrayList<Card> cards;

    public GameClient(String fileAsString){
        CardsJsonParser cjp = new CardsJsonParser(fileAsString);
        cards = cjp.getCards();
    }

    public void createCommands() {
        for (Card card : cards) {

        }
    }
}
