package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnLiveReceptors;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class OnCreature extends OnLiveReceptors {
    public OnCreature(CommandName name) {
        super(name);
    }

    public void setCreature(Creature creature) {
        setReceptors(new LiveReceptor[]{(LiveReceptor) creature});
    }

    public Creature getCreature() {
        if(receptors != null && receptors[0] != null) {
            return ((Creature) receptors[0]);
        }
        return null;
    }

    @Override
    public String toJson() {
        return  "{\"type\" : \"Command " + name + "\", \"player\" : " + ((LiveReceptor) receptors[0]).getOwnerName() +
                "}";
    }
}
