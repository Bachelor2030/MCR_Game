package Player;

import Card.Card;
import ModelClasses.Receptor;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Player extends Receptor {
    private static final int STARTING_LIFE_POINTS = 50;
    private static final int NBR_INIT_CARDS = 3;
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;
    private static final int NBR_ACTION_POINTS_MAX = 15;

    //faster than Stack or LinkedList
    private Deque<Card> deck = new ArrayDeque<Card>(NBR_CARDS_PER_DECK);
    private Deque<Card> hand = new ArrayDeque<Card>(NBR_CARDS_MAX_IN_HAND);

    private int nbEggDestroyed;
    private int actionPoints;

    public Player(String name, Deque<Card> deck) {
        super(name, STARTING_LIFE_POINTS);

        actionPoints = 0;
      if(deck != null)
      {
         // Shuffle the given deck
        Collections.shuffle(deck);
        if (!deck.isEmpty()) {
            this.deck.addAll(deck);
        }

        init();
      }
    }

    private void init() {
        for (int i = 0; i < NBR_INIT_CARDS; ++i) {
            hand.add(deck.remove());
        }

        nbEggDestroyed = 0;
    }

    @Override
    public void playTurn(int turn) {
        // Takes a card if possible otherwise
        // one card of the deck is thrown away
        if (hand.size() < NBR_CARDS_MAX_IN_HAND) {
            hand.add(deck.remove());
        } else {
            deck.remove();
        }

        if(turn <= NBR_ACTION_POINTS_MAX) {
            actionPoints = turn;
        } else {
            actionPoints = NBR_ACTION_POINTS_MAX;
        }

        // TODO : allow a certain amount of time to play
    }

    public int getNbrCardsInHand() {
        return hand.size();
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public int getNbEggDestroyed() {
        return nbEggDestroyed;
    }
}
