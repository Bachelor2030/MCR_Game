package Server.Game.Card.Commands.OnLiveReceptors;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.ConcreteCommand;
import Server.Game.Card.Commands.OnLiveReceptors.OnCreature.ChangeAttackPoints;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class OnLiveReceptors extends ConcreteCommand {
    protected LiveReceptor[] receptors;

    public OnLiveReceptors(CommandName name) {
        super(name);
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
        if (this.getName() == CommandName.KILL) {
            int[] lp = new int[receptors.length];
            for (int i = 0; i < receptors.length; i++) {
                lp[i] = ((Creature)receptors[i]).getLifePoints();
            }
            ((Kill)this).setLifePoints(lp);
        } else if (this.getName() == CommandName.CHANGE_AP) {
            ((ChangeAttackPoints)this).setAttackPoints(((Creature)receptors[0]).getAttackPoints());
        }
    }
}
