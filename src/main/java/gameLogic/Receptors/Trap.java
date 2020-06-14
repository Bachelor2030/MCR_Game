package gameLogic.receptors;

import gameLogic.board.Spot;
import gameLogic.commands.Macro;
import gameLogic.commands.playersAction.PlayersAction;
import network.Messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Modelizes a trap in the game
 */
public class Trap extends Receptor {
    private Macro effect;           // the effect the trap has on the first creature that lands on it
    private Spot position;      // the position at which the trap is

    /**
     * Creates the trap with the given name and effect
     * @param name the name of the trap
     * @param effect teh effect the trap has
     */
    public Trap(String name, Macro effect) {
        super("Trap " + name);
        this.effect = effect;
    }

    public Macro getEffect() {
        return effect;
    }

    /**
     * Triggers the trap on the given victim
     */
    public void trigger(Creature creature) {
        effect.execute(creature);
        if (position != null)
            position.leave();
        position = null;
    }

    /**
     * Sets the position of the trap which places it on the game board
     * @param position the position of the trap on the board
     */
    public void setPosition(Spot position) {
        if (position != null)
            position.setOccupant(this);
        this.position = position;
    }

    public Spot getPosition() {
        return position;
    }

    @Override
    public void playTurn(int turn, PlayersAction action) {}

    @Override
    public JSONObject toJson() {
        JSONObject trap = super.toJson();
        try {
            trap.put(Messages.JSON_TYPE_EFFECT, effect.toJson());
            if (position != null) {
                trap.put(Messages.JSON_TYPE_POSITION, position.toJson());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trap;
    }
}
