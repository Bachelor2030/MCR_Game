package GameLogic.Receptors;

import GameLogic.Invocator.Card.Card;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creature class, modelises the creatures that move on the board of the game and fight each other
 */
public class Creature extends LiveReceptor {
    private int steps;               // Number of stpes the creature can do in one turn
    private int attackPoints;        // Number of points the creature takes from an ennemi when hitting it
    private Card originCard;         // The card that created the creature
    private boolean asleep;

    /**
     * Creates a creature with the given information
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

    /**
     * Moves the creature of it's number of steps and hits the first ennemy encountered
     */
    public void advance() {
        for (int step = 0; step < steps; ++step) {
            if (!position.next().isValid()) {
                returnToDeck();
            }

            if (position.next().isEmpty()) {
                position.leave();
                position = position.next();
                if (position.isTrapped()) {
                    ((Trap)position.getOccupant()).trigger(this);
                }
            } else {
                break;
            }
        }
        if (lifePoints > 0 && !position.next().isEmpty() && position.next().isValid()) {
            if(!((LiveReceptor) position.next().getOccupant()).isAlly(this)) {

                ((LiveReceptor) position.next().getOccupant()).loseLifePoints(attackPoints);
                if(((LiveReceptor) position.next().getOccupant()).getType().equals(this.getType())) {
                    this.loseLifePoints(((Creature) position.next().getOccupant()).getAttackPoints());
                }
            }
        }
    }

    /**
     * Returns the creature creating card in it's owner's deck
     */
    private void returnToDeck() {
        owner.addToDeck(originCard);
    }

    /**
     * Moves the creature backwards of the given number of steps
     * @param distance the number of steps the creature must do backwards
     */
    public void retreat(int distance) {
        for (int step = 0; step < distance; ++step) {
            if (position.previous().isEmpty()) {
                position.leave();
                position = position.previous();
                if (position.isTrapped()) {
                    ((Trap)position.getOccupant()).trigger(this);
                }
            } else {
                break;
            }
        }
    }

    /**
     * Returns the number of steps the creature can do in a turn
     * @return steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Returns the number of attack points the creature has
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
    public void playTurn(int turn) {
        if (!asleep) {
            advance();
        }
        asleep = false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject creature =  super.toJson();

        try {
            creature.put("steps", steps);
            creature.put("attackpoints", attackPoints);
            creature.put("cardid", originCard.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return creature;
    }
}