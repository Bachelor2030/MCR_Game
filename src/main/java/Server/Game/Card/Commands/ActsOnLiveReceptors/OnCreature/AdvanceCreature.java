package Server.Game.Card.Commands.ActsOnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.CommandName;

public class AdvanceCreature extends ActOnCreature {
    public AdvanceCreature() {
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
