package Player.Creature;

import ModelClasses.Receptor;

public class Creature extends Receptor {
    private int steps; // exemple

    public Creature(String name, int lifePoints, int steps) {
        super(name, lifePoints);
        this.steps = steps;
    }

    @Override
    public void action() {

    }

    @Override
    public void playTurn() {

    }
}
