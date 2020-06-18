package gameLogic.receptors;

import gameLogic.Game;
import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.commands.CreateTrap;
import gameLogic.commands.Create;
import gameLogic.commands.playersAction.PlayCard;
import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.invocator.card.Card;
import network.states.ServerSharedState;

import java.util.*;

/** Modelises a Player of the game */
public class Player extends Receptor {
  private static final int NBR_INIT_CARDS = 3;
  private static final int NBR_CHESTS = 5;
  private static final int NBR_CARDS_PER_DECK = 50;
  private static final int NBR_CARDS_MAX_IN_HAND = 10;
  private static final int NBR_ACTION_POINTS_MAX = 15;
  private static final int ACTION_POINTS_START = 15;

  // Players deck of cards
  private ArrayList<Card> deck = new ArrayList<>(NBR_CARDS_PER_DECK);
  // Players hand (cards he/she can play in the current turn)
  private ArrayList<Card> hand = new ArrayList<>(NBR_CARDS_MAX_IN_HAND);

  // Historic of the Player
  private HashMap<Integer, List<Card>> discard = new HashMap<>();
  // Chests the Player must protect
  private List<Chest> chests = new LinkedList<>();
  // Players creatures that move on the board
  private ArrayList<Creature> creatures = new ArrayList<>();

  private int actionPoints, // Players action points
      currentTurn; // Current turn the Player is in
  private boolean abandoned, // True if the Player abandons the game
      play; // False when the Player ends his/her turn

  private Game game;
  private int id;

  /**
   * Creates a Player with the given name and deck
   *
   * @param name the name of the Player
   * @param deck the dock of cards the Player has
   */
  public Player(String name, List<Card> deck, Game game, ServerSharedState serverSharedState) {
    super(name, serverSharedState);
    this.game = game;
    actionPoints = 0;
    this.setImgPath("src/main/resources/design/images/characters/character.png");
    if (deck != null) {
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

  /** Initializes the Player */
  private void init() {
    abandoned = false;
    play = true;
    currentTurn = 0;

    for (Card card : deck) {
      for (ConcreteCommand command : card.getCommand().getCommands()) {
        if (command.getClass() == CreateTrap.class) {
          ((CreateTrap) command).setPlayer(this);
        }
        if (command.getName() == CommandName.CREATE_CREATURE) {
          ((Create) command).getCreature().setOwner(this);
        }
      }
    }

    for (int i = 0; i < NBR_INIT_CARDS; ++i) {
      hand.add(deck.remove(deck.size() - 1));
    }

    for (int i = 0; i < NBR_CHESTS; ++i) {
      chests.add(new Chest(name + " - Chest " + (i + 1), this));
    }
  }

  public ArrayList<Card> getHand() {
    return hand;
  }

  /**
   * Returns the number of the Player's chests that have been opened
   *
   * @return nbrOpenedChests
   */
  public int getNbChestsDestroyed() {
    int count = 0;
    for (Chest chest : chests) {
      if (!chest.isClosed()) {
        ++count;
      }
    }
    return count;
  }

  /**
   * Makes the Player play given the card
   *
   * @param card the card to play
   * @return true if the card can be played, false otherwise
   */
  public boolean playCard(Card card, Spot spot) {
    if (!hand.contains(card)) return false;

    if (card != null && actionPoints >= card.getCost()) {

      card.play(spot);

      ArrayList<Create> createCreatures = card.getCommand().getCreateCreature();
      for (Create create : createCreatures) {
        creatures.addAll(Arrays.asList(create.getCreature()));
      }

      discard.get(currentTurn).add(card);
      actionPoints -= card.getCost();
      hand.remove(card);
      return true;
    }
    return false;
  }

  /**
   * Get the discard pile of the Player
   *
   * @returna hashmap representing the discrad pile
   */
  public HashMap<Integer, List<Card>> getDiscard() {
    return discard;
  }

  /**
   * Puts the given card in the players hand
   *
   * @param card the card to give to the Player
   */
  public void giveCard(Card card) {
    if (hand.size() < NBR_CARDS_MAX_IN_HAND) {
      hand.add(card);
    }
  }

  /**
   * Makes the Player discard the given cardd, it will put it in the discard pile
   *
   * @param card the card to discard
   */
  public void discardCard(Card card) {
    hand.remove(card);
  }

  /**
   * Add a card to the top of the players deck
   *
   * @param card the card to add to the players deck
   */
  public void addToTopDeck(Card card) {
    deck.add(0, card);
  }

  /**
   * Makes the Player draw a card in his/her deck
   *
   * @return the card that went from the players deck to his/her hand
   */
  public Card drawCard() {
    Card card = deck.remove(0);
    hand.add(card);
    return card;
  }

  /**
   * Removes the given card from the players hand
   *
   * @param card the card to remove from the players hand
   */
  public void removeFromHand(Card card) {
    hand.remove(card);
  }

  /**
   * Adds the given card to the players deck
   *
   * @param card the card to add in the deck at a random position
   */
  public void addToDeck(Card card) {
    deck.add(deck.size() - 1, card);
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

  public void continueTurn() {
    play = true;
  }

  public Card lastCardPlayed() {
    int turn = 0;
    Card card = null;
    for (HashMap.Entry<Integer, List<Card>> turnCards : discard.entrySet()) {
      if (turnCards.getKey() < turn) {
        turn = turnCards.getKey();
        card = turnCards.getValue().get(turnCards.getValue().size() - 1);
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

  public void endTurn() {
    play = false;

    for (Creature creature : creatures) {
      creature.playTurn(currentTurn, new PlayCard());
      if (!creature.isAlive()) {
        creatures.remove(creature); // dead
      }
    }

    System.out.println(name + " finished his/her turn.");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return name.equals(player.name)
        && actionPoints == player.actionPoints
        && Objects.equals(deck, player.deck)
        && Objects.equals(hand, player.hand)
        && Objects.equals(discard, player.discard)
        && Objects.equals(chests, player.chests);
  }

  private void setTurn(int turn) {
    play = true;
    currentTurn = turn;

    discard.put(currentTurn, new ArrayList<>());

    // Takes a card if possible otherwise
    // one card of the deck is thrown away
    if (hand.size() < NBR_CARDS_MAX_IN_HAND) {
      hand.add(deck.remove(deck.size() - 1));
    } else {
      if (!deck.isEmpty()) {
        deck.remove(deck.size() - 1);
      } else {
        System.out.println("Chests to lose a life : ");
        for (Chest chest : chests) {
          System.out.println("  " + chest.getName());
          chest.loseLifePoints(1);
        }
        System.out.println();
      }
    }

    int points = turn + ACTION_POINTS_START;
    if (points <= NBR_ACTION_POINTS_MAX) {
      actionPoints = points;
    } else {
      actionPoints = NBR_ACTION_POINTS_MAX;
    }
  }

  @Override
  public void playTurn(int turn, PlayersAction action) {
    if (currentTurn != turn) {
      setTurn(turn);
    }
    action.execute(this);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
