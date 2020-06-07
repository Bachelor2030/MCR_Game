package Common.Receptors;

import Server.Game.Card.Card;
import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.Receptor;

import java.util.*;

public class Player extends Receptor {
    private static final int NBR_INIT_CARDS = 3;
    private static final int NBR_CHESTS = 5;
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;
    private static final int NBR_ACTION_POINTS_MAX = 15;

    private Deque<Card> deck = new ArrayDeque<>(NBR_CARDS_PER_DECK);
    private Deque<Card> hand = new ArrayDeque<>(NBR_CARDS_MAX_IN_HAND);

    // Historic of the player
    // the map is set as so : <trunNumber, cards played>
    // There can be multiple cards in one turn
    private HashMap<Integer, List<Card>> discard = new HashMap<>();

    private List<Chest> chests = new LinkedList<>();

    private ArrayList<Creature> creatures = new ArrayList<>();

    private int actionPoints;

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

    private void init() {
        for (int i = 0; i < NBR_INIT_CARDS; ++i) {
            hand.add(deck.remove());
        }

        for (int i = 0; i < NBR_CHESTS; ++i) {
            chests.add(new Chest(name + " - Chest " + (i + 1), this));
        }
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
                for (Chest chest : chests) {
                    System.out.println(name + "'s chests lose a life");
                    chest.hit(1);
                }
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
            if(hand.size() != 0) {
                int chosenCard = 0;
                playCard(chosenCard);
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

    public int getNbrCardsInHand() {
        return hand.size();
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public int getNbChestsDestroyed() {
        int count = 0;
        for (Chest chest : chests) {
            if(!chest.isClosed()) {
                ++count;
            }
        }
        return count;
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

            if(cardToPlay.getCommand() != null && cardToPlay.getCommand().getName() == CommandName.CREATE_CREATURE) {

            }

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

    public static int getStartingNbrChests() {
        return NBR_CHESTS;
    }

    public int getNbChests() {
        return chests.size();
    }

    public void hitChest(int chestsIndex, int attackPoints) {
        chests.get(chestsIndex).hit(attackPoints);
    }

    public List<Chest> getChests() {
        return chests;
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

    public void addToDeck(Card originCard) {
        deck.addLast(originCard);
        ArrayList<Card> d = new ArrayList<>();
        d.addAll(deck);
        Collections.shuffle(d);
        deck.clear();
        deck.addAll(d);
    }
}
