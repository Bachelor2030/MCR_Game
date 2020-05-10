package Player;

import Card.Card;
import ModelClasses.LiveReceptor;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Player extends LiveReceptor {
    private static final int STARTING_LIFE_POINTS = 50;
    private static final int NBR_INIT_CARDS = 3;
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;
    private static final int NBR_ACTION_POINTS_MAX = 15;

    //faster than Stack or LinkedList
    private Deque<Card> deck = new ArrayDeque<>(NBR_CARDS_PER_DECK);
    private Deque<Card> hand = new ArrayDeque<>(NBR_CARDS_MAX_IN_HAND);

    private int actionPoints;

    public Player(String name, List<Card> deck) {
        super(name, STARTING_LIFE_POINTS);

        actionPoints = 0;

        // Shuffle the given deck
        Collections.shuffle(deck);
        if (!deck.isEmpty()) {
            this.deck.addAll(deck);
        }

        init();
    }

    private void init() {
        for (int i = 0; i < NBR_INIT_CARDS; ++i) {
            hand.add(deck.remove());
        }
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
}
