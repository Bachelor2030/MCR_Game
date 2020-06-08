package Server.Game.Card.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.OnLiveReceptors.OnLiveReceptors;
import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class OnCreature extends OnLiveReceptors {
    public OnCreature(CommandName name) {
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
