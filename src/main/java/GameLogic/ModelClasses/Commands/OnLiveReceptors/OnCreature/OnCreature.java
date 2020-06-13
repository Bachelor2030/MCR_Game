package GameLogic.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.ModelClasses.Commands.OnLiveReceptors.OnLiveReceptors;
import GameLogic.ModelClasses.Commands.CommandName;

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
