package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;

public class Advance extends MoveCreature {
    public Advance() {
        super(CommandName.ADVANCE_CREATURE);
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;

        from = new Position[receptors.length];
        to   = new Position[receptors.length];
        for (int i = 0; i < receptors.length; i++) {
            from[i] = receptors[i].getPosition();
            ((Creature) receptors[i]).advance();
            from[i] = receptors[i].getPosition();
        }
    }

    @Override
    public void undo() {
        if(receptors != null && receptors[0] != null) {
            ((Creature) receptors[0]).retreat(((Creature) receptors[0]).getSteps());
        }
    }

}
