package Common.Receptors;

import Server.Game.Position;
import Server.Game.ModelClasses.LiveReceptor;

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

    public List<Action> retreat(int distance) {
        LinkedList<Action> actions = new LinkedList<>();
        for (int step = 0; step < distance; ++step) {
            if (position.previous().isEmpty()) {
                position.leave();
                position = position.previous();
                if (position.getOccupant().getClass() == Trap.class) {
                    ((Trap)position.getOccupant()).trigger(this);
                    actions.add(Action.TRAPPED);
                }
            } else {
                break;
            }
        }
        return actions;
    }

    public Position getPosition() {
        return position;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public void playTurn(int turn) {
        advance();
    }
}
