package Common.Receptors;

import Server.Game.Position;
import Server.Game.ModelClasses.LiveReceptor;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Receptor;

public class Trap extends Receptor {
    private Macro effect;
    private Position position;

    public Trap(String name, Macro effect) {
        super(name);
        this.effect = effect;
    }

    public void trigger(LiveReceptor victim) {
        if (effect != null){
            effect.execute();
        }
        position.leave();
        position = null;
    }

    @Override
    public void playTurn(int turn) {}

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
