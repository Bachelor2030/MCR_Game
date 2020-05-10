package Player;

import Card.Card;
import ModelClasses.Receptor;

import java.util.*;

public class Player extends Receptor {
    private static final int STARTING_LIFE_POINTS = 50;
    private static final int NBR_INIT_CARDS = 3;
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;
    private static final int NBR_ACTION_POINTS_MAX = 15;

    private Deque<Card> deck = new ArrayDeque<>(NBR_CARDS_PER_DECK);
    private Deque<Card> hand = new ArrayDeque<>(NBR_CARDS_MAX_IN_HAND);

    // Historic of the player
    // the map is set as so : <trunNumber, cardPlayed>
    // There can be multiple cards in one turn
    private HashMap<Integer, Card> history = new HashMap<>();

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

    public boolean playCard(int index) {
        Card cardToPlay = null;
        int i = 0;
        for(Card card : hand) {
            if (i++ == index) {
                cardToPlay = card;
                break;
            }
        }

        if(cardToPlay != null &&
            actionPoints >= cardToPlay.getCost()) {

            cardToPlay.play();
            actionPoints -= cardToPlay.getCost();
            return true;
        }
        return false;
    }
}
