package ModelClasses;

import Card.Card;
import Game.Utils.CardsJsonParser;

import java.util.ArrayList;

/**
 * Represents the client in the command model
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
