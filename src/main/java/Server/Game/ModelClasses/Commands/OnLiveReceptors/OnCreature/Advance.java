package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.CommandName;

public class Advance extends OnCreature {
    public Advance() {
        super(CommandName.ADVANCE_CREATURE);
    }

    @Override
    public void execute() {
        if(receptors != null && receptors[0] != null) {
            ((Creature) receptors[0]).advance();
        }
    }

    @Override
    public void undo() {
        if(receptors != null && receptors[0] != null) {
            ((Creature) receptors[0]).retreat(((Creature) receptors[0]).getSteps());
        }
    }

}
