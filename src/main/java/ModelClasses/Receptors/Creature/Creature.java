package ModelClasses.Receptors.Creature;

import Game.GameBoard.Spot;
import ModelClasses.LiveReceptor;

public class Creature extends LiveReceptor {
    private Spot position;
    private int steps;
    private String owner;

    public Creature(String name, int lifePoints, int steps, String owner) {
        super(name, lifePoints);
        this.steps = steps;
        this.owner = owner;
    }

    public boolean isAlly(Creature creature) {
        return owner.equals(creature.owner);
    }

    public void place(Spot position) {
        this.position = position;
        this.position.setOccupant(this);
    }

    public void advance() {
        // TODO move to position + steps or hit
    }

    public void retreat() {
        // TODO move to position - steps
    }

    @Override
    public void playTurn(int turn) {
        advance();
    }

    public Spot getPosition() {
        return position;
    }
}
