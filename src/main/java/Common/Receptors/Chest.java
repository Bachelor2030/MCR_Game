package Common.Receptors;

import Server.Game.ModelClasses.LiveReceptor;

public class Chest extends LiveReceptor {
    private static final int CHEST_LIFE_POINTS = 5;
    private boolean closed;

    public Chest(String name, Player owner) {
        super(name, CHEST_LIFE_POINTS, owner, "Chest");
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void playTurn(int turn) {}

    @Override
    public void hit(int points) {
        super.hit(points);
        if (lifePoints <= 0) {
            closed = false;
        }
    }
}
