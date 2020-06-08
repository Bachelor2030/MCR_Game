package Common.Receptors;

import Server.Game.ModelClasses.LiveReceptor;

/**
 * Chest owned by a player. Said player must protect it by using creatures and spells
 */
public class Chest extends LiveReceptor {
    // Max life points for a chest
    private static final int CHEST_LIFE_POINTS = 5;

    /**
     * Create a chest with the given name and owner
     * @param name the name of the current chest
     * @param owner the player that owns the current chest
     */
    public Chest(String name, Player owner) {
        super(name, CHEST_LIFE_POINTS, "Chest");
    }

    /**
     * Checks if the current chest is closed
     * A chest is opened if it has no lifePoints left
     * @return true if the chest still has life points left
     */
    public boolean isClosed() {
        return isAlive();
    }

    @Override
    public void playTurn(int turn) {}
}
