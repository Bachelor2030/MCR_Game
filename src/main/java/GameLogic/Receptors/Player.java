package GameLogic.Receptors;

import GameLogic.Invocator.Card.Card;
import GameLogic.Commands.OnLiveReceptors.OnCreature.Create;
import GameLogic.Commands.PlayersAction.PlayersAction;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Modelises a player of the game
 */
public class Player extends Receptor {
    private static final int NBR_INIT_CARDS = 3;
    private static final int NBR_CHESTS = 5;
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;
    private static final int NBR_ACTION_POINTS_MAX = 15;

    // Players deck of cards
    private Deque<Card> deck = new ArrayDeque<>(NBR_CARDS_PER_DECK);
    // Players hand (cards he/she can play in the current turn)
    private Deque<Card> hand = new ArrayDeque<>(NBR_CARDS_MAX_IN_HAND);

    // Historic of the player
    private HashMap<Integer, List<Card>> discard = new HashMap<>();
    // Chests the player must protect
    private List<Chest> chests = new LinkedList<>();
    // Players creatures that move on the board
    private ArrayList<Creature> creatures = new ArrayList<>();

    private int
            actionPoints,   // Players action points
            currentTurn;    // Current turn the player is in
    private boolean
            abandoned,      // True if the player abandons the game
            play;           // False when the player ends his/her turn

    /**
     * Creates a player with the given name and deck
     * @param name the name of the player
     * @param deck the dock of cards the player has
     */
    public Player(String name, List<Card> deck) {
        super(name);
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

    public Player() {
        super();
    }

    /**
     * Initializes the player
     */
    private void init() {
        abandoned = false;
        play = true;
        currentTurn = 0;
        for (int i = 0; i < NBR_INIT_CARDS; ++i) {
            hand.add(deck.remove());
        }

        for (int i = 0; i < NBR_CHESTS; ++i) {
            chests.add(new Chest(name + " - Chest " + (i + 1), this));
        }
    }

    public Deque<Card> getHand() {
        return hand;
    }

    /**
     * Returns the number of cards the player has in his/her hand
     * @return size of the hand list of cards
     */
    public int getNbrCardsInHand() {
        return hand.size();
    }

    /**
     * Returns the number of the player's chests that have been opened
     * @return nbrOpenedChests
     */
    public int getNbChestsDestroyed() {
        int count = 0;
        for (Chest chest : chests) {
            if(!chest.isClosed()) {
                ++count;
            }
        }
        return count;
    }

    /**
     * Makes the player play given the card
     * @param card the card to play
     * @return true if the card can be played, false otherwise
     */
    public boolean playCard(Card card) {
        if (!hand.contains(card))
            return false;

        if(card != null &&
            actionPoints >= card.getCost()) {

            card.play();

            ArrayList<Create> createCreatures = card.getCommand().getCreateCreature();
            for (Create create : createCreatures) {
                creatures.addAll(Arrays.asList(create.getCreatures()));
            }

            discard.get(currentTurn).add(card);
            actionPoints -= card.getCost();
            hand.remove(card);
            return true;
        }
        return false;
    }

    /**
     * Get the discard pile of the player
     * @returna hashmap representing the discrad pile
     */
    public HashMap<Integer, List<Card>> getDiscard() {
        return discard;
    }

    /**
     * Puts the given card in the players hand
     * @param card the card to give to the player
     */
    public void giveCard(Card card) {
        if (hand.size() < NBR_CARDS_MAX_IN_HAND) {
            hand.add(card);
        }
    }

    /**
     * Makes the player discard the given cardd, it will put it in the discard pile
     * @param card the card to discard
     */
    public void discardCard(Card card) {
        hand.remove(card);
    }

    /**
     * Add a card to the top of the players deck
     * @param card the card to add to the players deck
     */
    public void addToTopDeck(Card card) {
        deck.addFirst(card);
    }

    /**
     * Makes the player draw a card in his/her deck
     * @return the card that went from the players deck to his/her hand
     */
    public Card drawCard() {
        Card card = deck.removeFirst();
        hand.add(card);
        return card;
    }

    /**
     * Removes the given card from the players hand
     * @param card the card to remove from the players hand
     */
    public void removeFromHand(Card card) {
        hand.remove(card);
    }

    /**
     * Adds the given card to the players deck
     * @param card the card to add in the deck at a random position
     */
    public void addToDeck(Card card) {
        deck.addLast(card);
        ArrayList<Card> d = new ArrayList<>();
        d.addAll(deck);
        Collections.shuffle(d);
        deck.clear();
        deck.addAll(d);
    }

    public boolean hasAbandoned() {
        return abandoned;
    }

    public void abandon() {
        abandoned = true;
    }

    public void undoAbandon() {
        abandoned = false;
    }

    public void endTurn() {
        play = false;
    }

    public void continueTurn() {
        play = true;
    }

    public Card lastCardPlayed() {
        int turn = 0;
        Card card = null;
        for (HashMap.Entry<Integer, List<Card>> turnCards : discard.entrySet()) {
            if (turnCards.getKey() < turn) {
                turn = turnCards.getKey();
                card = turnCards.getValue().get(turnCards.getValue().size()-1);
            }
        }
        return card;
    }

    public void undoCard(Card cardToUndo) {
        if (discard.get(currentTurn).remove(cardToUndo)) {
            hand.add(cardToUndo);
            cardToUndo.undo();
            actionPoints += cardToUndo.getCost();
        }
    }

    @Override
    public void playTurn(int turn) {
        play = true;
        currentTurn = turn;
        // Takes a card if possible otherwise
        // one card of the deck is thrown away
        if (hand.size() < NBR_CARDS_MAX_IN_HAND) {
            hand.add(deck.remove());
        } else {
            if(!deck.isEmpty()) {
                deck.remove();
            } else {
                System.out.println("Chests to lose a life : ");
                for (Chest chest : chests) {
                    System.out.println("  " + chest.getName());
                    chest.loseLifePoints(1);
                }
                System.out.println();
            }
        }

        if(turn <= NBR_ACTION_POINTS_MAX) {
            actionPoints = turn;
        } else {
            actionPoints = NBR_ACTION_POINTS_MAX;
        }

        while (play && !abandoned) {
            System.out.println(name + " is playing...");
            PlayersAction.askAction(this).execute();
        }

        for (Creature creature : creatures) {
            creature.playTurn(turn);
            if (!creature.isAlive()) {
                creatures.remove(creature);
            }
        }

        System.out.println(name + " finished his/her turn.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return  name.equals(player.name) &&
                actionPoints == player.actionPoints &&
                Objects.equals(deck, player.deck) &&
                Objects.equals(hand, player.hand) &&
                Objects.equals(discard, player.discard) &&
                Objects.equals(chests, player.chests);
    }
}
