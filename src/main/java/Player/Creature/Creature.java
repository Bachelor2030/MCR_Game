package Player.Creature;

import ModelClasses.LiveReceptor;

public class Creature extends LiveReceptor {
    private int steps; // exemple

    public Creature(String name, int lifePoints, int steps) {
        super(name, lifePoints);
        this.steps = steps;
    }

    @Override
    public void playTurn(int turn) {

    }
}
