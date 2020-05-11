package ModelClasses.Receptors;

import Card.Card;
import ModelClasses.LiveReceptor;

import java.util.*;

public class Player extends LiveReceptor {
    private static final int STARTING_LIFE_POINTS = 50;
    private static final int NBR_INIT_CARDS = 3;
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;
    private static final int NBR_ACTION_POINTS_MAX = 15;

    private Deque<Card> deck = new ArrayDeque<>(NBR_CARDS_PER_DECK);
    private Deque<Card> hand = new ArrayDeque<>(NBR_CARDS_MAX_IN_HAND);

    // Historic of the player
    // the map is set as so : <trunNumber, cards played>
    // There can be multiple cards in one turn
    private HashMap<Integer, List<Card>> discard = new HashMap<>();

    private int actionPoints;
    private int nbEggDestroyed;

    public Player(String name, List<Card> deck) {
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

    public HashMap<Integer, List<Card>> getDiscard() {
        return discard;
    }

    public void giveCard(Card card) {
        if (hand.size() < NBR_CARDS_MAX_IN_HAND) {
            hand.add(card);
        }
    }

    public void discardCard(Card card) {
        hand.remove(card);
    }

    public void addToTopDeck(Card card) {
        deck.addFirst(card);
    }

    public Card drawCard() {
        Card card = deck.removeFirst();
        hand.add(card);
        return card;
    }

    public void removeFromHand(Card card) {
        hand.remove(card);
    }
}
