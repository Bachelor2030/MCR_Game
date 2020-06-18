package gameLogic.receptors;

import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.invocator.card.Card;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creature class, modelises the creatures that move on the board of the game and fight each other
 */
public class Creature extends LiveReceptor {
  private int steps; // Number of steps the creature can do in one turn
  private int attackPoints; // Number of points the creature takes from an ennemi when hitting it
  private Card originCard; // The card that created the creature
  private boolean asleep;

  /**
   * Creates a creature with the given information
   *
   * @param name the name of the creature
   * @param lifePoints the nombre of life points the creature starts with
   * @param steps the number of steps the creature can do in one move
   * @param attackPoints the number of life points the creature removes form an ennemy
   */
  public Creature(String name, int lifePoints, int steps, int attackPoints) {
    super(name, lifePoints, "Creature");
    this.steps = steps;
    this.attackPoints = attackPoints;
    asleep = false;
  }

  public void setOriginCard(Card originCard) {
    this.originCard = originCard;
  }

  public Card getOriginCard() {
    return originCard;
  }

  /** Moves the creature of it's number of steps and hits the first ennemy encountered */
  public int advance(ServerSharedState serverSharedState) {
    int counter = 0;
    for (int step = 0; step < steps; ++step) {
      if (position.next(owner.getId(), serverSharedState) == null) {
        returnToDeck();
      }
      if (position.next(owner.getId(), serverSharedState) == null) {
        return counter;
      }

      if (position.next(owner.getId(), serverSharedState).isEmpty()) {
        position.leave();
        position = position.next(owner.getId(), serverSharedState);
        if (position.isTrapped()) {
          ((Trap) position.getOccupant()).trigger(this, serverSharedState);
        }
        ++counter;
      } else {
        break;
      }
    }
    if (lifePoints > 0 && !position.next(owner.getId(), serverSharedState).isEmpty() && position.next(owner.getId(), serverSharedState) != null) {
      if (!((LiveReceptor) position.next(owner.getId(), serverSharedState).getOccupant()).isAlly(this)) {

        ((LiveReceptor) position.next(owner.getId(), serverSharedState).getOccupant()).loseLifePoints(attackPoints);
        if (((LiveReceptor) position.next(owner.getId(), serverSharedState).getOccupant()).getType().equals(this.getType())) {
          this.loseLifePoints(((Creature) position.next(owner.getId(), serverSharedState).getOccupant()).getAttackPoints());
        }
      }
    }
    return counter;
  }

  /** Returns the creature creating card in it's owner's deck */
  private void returnToDeck() {
    lifePoints = 0;
    owner.addToDeck(originCard);
  }

  /**
   * Moves the creature backwards of the given number of steps
   *
   * @param distance the number of steps the creature must do backwards
   */
  public void retreat(int distance, ServerSharedState serverSharedState) {
    for (int step = 0; step < distance; ++step) {
      if (position.previous(owner.getId(), serverSharedState).isEmpty()) {
        position.leave();
        position = position.previous(owner.getId(), serverSharedState);
        if (position.isTrapped()) {
          ((Trap) position.getOccupant()).trigger(this, serverSharedState);
        }
      } else {
        break;
      }
    }
  }

  /**
   * Returns the number of steps the creature can do in a turn
   *
   * @return steps
   */
  public int getSteps() {
    return steps;
  }

  /**
   * Returns the number of attack points the creature has
   *
   * @return attackPoints
   */
  public int getAttackPoints() {
    return attackPoints;
  }

  public void wakeUp() {
    asleep = false;
  }

  public void knockOut() {
    asleep = true;
  }

  public void setAttackPoints(int newAP) {
    attackPoints = newAP;
  }

  public void setMovementsPoints(int newMP) {
    steps = newMP;
  }

  @Override
  public void playTurn(int turn, PlayersAction action, ServerSharedState serverSharedState) {
    if (!asleep) {
      advance(serverSharedState);
    }
    asleep = false;
  }

  @Override
  public JSONObject toJson() {
    JSONObject creature = super.toJson();

    try {
      creature.put(Messages.JSON_TYPE_MP, steps);
      creature.put(Messages.JSON_TYPE_AP, attackPoints);
      if (originCard != null) creature.put(Messages.JSON_TYPE_CARD_ID, originCard.getID());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return creature;
  }
}
