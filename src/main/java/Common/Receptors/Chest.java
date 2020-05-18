package Common.Receptors;

import Server.Game.ModelClasses.LiveReceptor;

public class Chest extends LiveReceptor {
    private static final int CHEST_LIFE_POINTS = 5;
    private boolean alive;

    public Chest(String name) {
        super(name, CHEST_LIFE_POINTS);
        alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void playTurn(int turn) {}

    @Override
    public void hit(int points) {
        super.hit(points);
        if (lifePoints <= 0) {
            alive = false;
        }
    }
}
