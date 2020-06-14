package gameLogic.Commands.OnLiveReceptors.OnCreature;

import gameLogic.Board.Spot;
import gameLogic.Receptors.Creature;
import gameLogic.Commands.CommandName;

public class Advance extends MoveCreature {
    public Advance() {
        super(CommandName.ADVANCE_CREATURE);
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;

        from = new Spot[receptors.length];
        to   = new Spot[receptors.length];
        for (int i = 0; i < receptors.length; i++) {
            from[i] = receptors[i].getPosition();
            ((Creature) receptors[i]).advance();
            from[i] = receptors[i].getPosition();
        }
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;

        from = new Spot[receptors.length];
        to   = new Spot[receptors.length];
        for (int i = 0; i < receptors.length; i++) {
            from[i] = receptors[i].getPosition();
            ((Creature) receptors[i]).retreat(((Creature) receptors[i]).getSteps());
            from[i] = receptors[i].getPosition();
        }
    }

}
