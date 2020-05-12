package ModelClasses.Receptors;

import ModelClasses.LiveReceptor;

public class Egg extends LiveReceptor {
    private static final int EGG_LIFE_POINTS = 5;
    private boolean alive;

    public Egg(String name) {
        super(name, EGG_LIFE_POINTS);
        alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public String toString() {
        return name + " is " + (alive ? "alive" : "dead");
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
