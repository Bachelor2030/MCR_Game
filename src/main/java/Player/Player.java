package Player;

import Card.Card;
import ModelClasses.Receptor;

import java.util.ArrayDeque;
import java.util.Deque;

public class Player extends Receptor {
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;

    //faster than Stack or LinkedList
    Deque<Card> deck = new ArrayDeque<Card>(NBR_CARDS_PER_DECK);
    Deque<Card> hand = new ArrayDeque<Card>(NBR_CARDS_MAX_IN_HAND);

    public Player(String name, int lifePoints, Deque<Card> deck) {
        super(name, lifePoints);
        if(deck != null && deck.size() > 0)
        {
            for(Card card : deck)
            {
                deck.add(card);
            }
        }
    }

    @Override
    public void action() {

    }

    @Override
    public void playTurn() {

    }
}
