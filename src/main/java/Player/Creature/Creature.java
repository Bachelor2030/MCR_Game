package Player.Creature;

import ModelClasses.LiveReceptor;

public class Creature extends LiveReceptor {
    private Spot position;
    private int steps; // exemple

    public Creature(String name, int lifePoints, int steps, Spot position) {
        super(name, lifePoints);
        this.steps = steps;
        this.position = position;
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
}
