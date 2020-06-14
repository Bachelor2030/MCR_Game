package gameLogic.Commands.OnLiveReceptors.OnCreature;

import gameLogic.Receptors.Creature;
import gameLogic.Commands.OnLiveReceptors.OnLiveReceptors;
import gameLogic.Commands.CommandName;

public abstract class OnCreature extends OnLiveReceptors {
    public OnCreature(CommandName name) {
        super(name);
    }

    public void setCreatures(Creature[] creatures) {
        setReceptors(creatures);
    }

    public Creature[] getCreatures() {
        if(receptors != null) {
            return (Creature[])receptors;
        }
        return null;
    }
}
