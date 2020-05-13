package ModelClasses.Receptors;

import Game.GameBoard.Position;
import ModelClasses.LiveReceptor;
import ModelClasses.Macro;
import ModelClasses.Receptor;

public class Trap extends Receptor {
    private Macro effect;
    private Position position;

    public Trap(String name, Macro effect) {
        super(name);
        this.effect = effect;
    }

    public void trigger(LiveReceptor victim) {
        effect.execute();
        position.leave();
        position = null;
    }

    @Override
    public void playTurn(int turn) {}

    public void setPosition(Position position) {
        this.position = position;
    }
}
