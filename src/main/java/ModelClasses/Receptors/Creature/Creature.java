package ModelClasses.Receptors.Creature;

import Game.GameBoard.Spot;
import ModelClasses.LiveReceptor;

public class Creature extends LiveReceptor {
    private Spot position;
    private int steps; // exemple

    public Creature(String name, int lifePoints, int steps) {
        super(name, lifePoints);
        this.steps = steps;
    }

    public void place(Spot position) {
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

    public Spot getPosition() {
        return position;
    }
}
