package Server.Game.Card.Commands.ActsOnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.ActsOnLiveReceptors.ActOnLiveReceptors;
import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class ActOnCreature extends ActOnLiveReceptors {
    public ActOnCreature(CommandName name) {
        super(name);
    }

    public void setCreature(Creature creature) {
        setReceptors(new LiveReceptor[]{creature});
    }

    public Creature getCreature() {
        if(receptors != null && receptors[0] != null) {
            return ((Creature) receptors[0]);
        }
        return null;
    }
}
