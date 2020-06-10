package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.CommandName;

public class Retreat extends OnCreature {
    public Retreat() {
        super(CommandName.RETREAT_CREATURE);
    }

    @Override
    public void execute() {
        if(receptors != null && receptors[0] != null) {
            ((Creature)receptors[0]).retreat(((Creature)receptors[0]).getSteps());
        }
    }

    @Override
    public void undo() {
        if(receptors != null && receptors[0] != null) {
            ((Creature)receptors[0]).advance();
        }
    }
}
