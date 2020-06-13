package Common.Receptors;

import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnLiveReceptors;
import Server.Game.Position;
import Server.Game.ModelClasses.LiveReceptor;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Receptor;

/**
 * Modelizes a trap in the game
 */
public class Trap extends Receptor {
    private Macro effect;           // the eggect the trap has on the first creature that lands on it
    private Position position;      // the position at which the trap is

    /**
     * Creates the trap with the given name and effect
     * @param name the name of the trap
     * @param effect teh effect the trap has
     */
    public Trap(String name, Macro effect) {
        super("Trap " + name);
        this.effect = effect;
    }

    /**
     * Triggers the trap on the given victim
     */
    public void trigger(Creature creature) {
        setVictim(creature);
        effect.execute();
        if (position != null)
            position.leave();
        position = null;
    }

    /**
     * Sets the position of the trap which places it on the game board
     * @param position the position of the trap on the board
     */
    public void setPosition(Position position) {
        if (position != null)
            position.setOccupant(this);
        this.position = position;
    }

    @Override
    public void playTurn(int turn) {}

    public Position getPosition() {
        return position;
    }

    private void setVictim(Creature creature) {
        for (ConcreteCommand c :
                effect.getCommands()) {
            ((OnLiveReceptors)c).setReceptors(new LiveReceptor[]{creature});
        }
    }
}
