package ModelClasses.Receptors.Creature;

import Game.GameBoard.Position;
import ModelClasses.LiveReceptor;
import ModelClasses.Receptors.Trap;

import java.util.LinkedList;
import java.util.List;

public class Creature extends LiveReceptor {
    private Position position;
    private int steps;
    private int attackPoints;
    private String owner;

    public Creature(String name, int lifePoints, int steps, int attackPoints, String owner) {
        super(name, lifePoints);
        this.steps = steps;
        this.owner = owner;
        this.attackPoints = attackPoints;
    }

    public boolean isAlly(Creature creature) {
        return owner.equals(creature.owner);
    }

    public void place(Position position) {
        this.position = position;
        this.position.setOccupant(this);
    }

    public List<Action> advance() {
        LinkedList<Action> actions = new LinkedList<>();
        for (int step = 0; step < steps; ++step) {
            if (position.next().isEmpty()) {
                position.leave();
                position = position.next();
                if (position.getOccupant().getClass() == Trap.class) {
                    ((Trap)position.getOccupant()).trigger(this);
                    actions.add(Action.TRAPPED);
                }
            } else {
                break;
            }
        }
        if (lifePoints > 0 && !position.next().isEmpty()) {
            ((LiveReceptor)position.next().getOccupant()).hit(attackPoints);
            actions.add(Action.HIT);
        }

        return actions;
    }

    public void retreat() {
        // TODO move to position - steps
    }

    @Override
    public void playTurn(int turn) {
        advance();
    }

    public Position getPosition() {
        return position;
    }
}
