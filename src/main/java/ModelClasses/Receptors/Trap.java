package ModelClasses.Receptors;

import ModelClasses.Macro;
import Game.GameBoard.Spot;
import ModelClasses.Receptor;

public class Trap extends Receptor {
    private Macro effect;
    private Spot position;

    public Trap(String name, Macro effect) {
        super(name);
        this.effect = effect;
    }

    @Override
    public void playTurn(int turn) {
        effect.execute();
    }

    public void setPosition(Spot position) {
        this.position = position;
    }
}
