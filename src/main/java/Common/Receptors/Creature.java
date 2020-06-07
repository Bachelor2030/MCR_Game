package Common.Receptors;

import Server.Game.Card.Card;
import Server.Game.Position;
import Server.Game.ModelClasses.LiveReceptor;

public class Creature extends LiveReceptor {
    private Position position;
    private int steps;
    private int attackPoints;
    private Card originCard;

    public Creature(String name, int lifePoints, int steps, int attackPoints, Player owner) {
        super(name, lifePoints, owner, "Creature");
        this.steps = steps;
        this.attackPoints = attackPoints;
    }

    public void place(Position position) {
        if(position != null) {
            this.position = position;
            this.position.setOccupant(this);
        }
    }

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
        if (lifePoints > 0 && !position.next().isEmpty()) {
            if(!((LiveReceptor) position.next().getOccupant()).isAlly(this)) {

                ((LiveReceptor) position.next().getOccupant()).hit(attackPoints);
                if(((LiveReceptor) position.next().getOccupant()).getType().equals(this.getType())) {
                    this.hit(((Creature) position.next().getOccupant()).getAttackPoints());
                }
            }
        }
    }

    private void returnToDeck() {
        owner.addToDeck(originCard);
    }

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

    public Position getPosition() {
        return position;
    }

    public int getSteps() {
        return steps;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setOriginCard(Card card) {
        this.originCard = card;
    }

    @Override
    public void playTurn(int turn) {
        advance();
    }

}