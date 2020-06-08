package Common.Receptors;

import Server.Game.Card.Card;
import Server.Game.ModelClasses.Receptor;

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

    private Deque<Card> deck = new ArrayDeque<>(NBR_CARDS_PER_DECK);
    private Deque<Card> hand = new ArrayDeque<>(NBR_CARDS_MAX_IN_HAND);

    // Historic of the player
    private HashMap<Integer, List<Card>> discard = new HashMap<>();

    private List<Chest> chests = new LinkedList<>();

    private ArrayList<Creature> creatures = new ArrayList<>();

    private int actionPoints;

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

    /**
     * Initializes the player
     */
    private void init() {
        for (int i = 0; i < NBR_INIT_CARDS; ++i) {
            hand.add(deck.remove());
        }

        for (int i = 0; i < NBR_CHESTS; ++i) {
            chests.add(new Chest(name + " - Chest " + (i + 1), this));
        }
    }

    /**
     * Returns the number of cards the player has in his/her hand
     * @return size of the hand list of cards
     */
    public int getNbrCardsInHand() {
        return hand.size();
    }

    /**
     * Returns the number of action points the player currently has
     * @return actionsPoints
     */
    public int getActionPoints() {
        return actionPoints;
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
     * Makes the player play the card at the given index in his/her hand
     * @param index index of the card to play
     * @return true if the card can be played, false otherwise
     */
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

            /*
            if(cardToPlay.getCommand() != null && cardToPlay.getCommand().getName() == CommandName.CREATE_CREATURE) {
                // TODO
            }
            */

            actionPoints -= cardToPlay.getCost();
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
     * Get the initial number of chests
     * @return the number of chests the player started with
     */
    public static int getStartingNbrChests() {
        return NBR_CHESTS;
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

    @Override
    public void playTurn(int turn) {
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

        boolean keepPlaying = true;
        while (keepPlaying) {
            System.out.println(name + " is playing...");
            /*
             * TODO get the players actions (card)
             *  /!\ the player has a button indication if he has finished his turn, if the player selects the button then...
             */
            Random rand = new Random();
            if(hand.size() != 0) {
                int max = hand.size();
                int chosenCard;
                do {
                    chosenCard = rand.nextInt() % hand.size();
                } while (!playCard(chosenCard) && --max > 0);
                keepPlaying = false;
            }
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
