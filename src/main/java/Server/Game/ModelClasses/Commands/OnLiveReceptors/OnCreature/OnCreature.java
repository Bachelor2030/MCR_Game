package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnLiveReceptors;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class OnCreature extends OnLiveReceptors {
    public OnCreature(CommandName name) {
        super(name);
    }

    public void setCreature(Creature[] creatures) {
        setReceptors(creatures);
    }

    public Creature[] getCreature() {
        if(receptors != null) {
            return (Creature[])receptors;
        }
        return null;
    }
}
