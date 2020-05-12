package Game.Utils;

import Card.Card;

import java.util.ArrayList;


/**
 * Represents the client in the command model
 * This class creates the array of cards and links them with the correct command
 */
public class GameCreator {
    protected ArrayList<Card> cards;

    public GameCreator(String fileAsString){
        CardsJsonParser cardsJsonParser = new CardsJsonParser(fileAsString);
        cards = cardsJsonParser.getCards();
    }
}
