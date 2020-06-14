package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.OnLiveReceptors.OnLiveReceptor;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.LiveReceptor;
import GameLogic.Receptors.Receptor;

public abstract class OnCreature extends OnLiveReceptor {
    public OnCreature(CommandName name) {
        super(name);
    }

    public Creature getCreatures() {
        return (Creature)receptor;
    }

    public abstract void execute(Creature creature);
    public abstract void undo(Creature creature);

    @Override
    public void execute(LiveReceptor receptor) {
        this.receptor = (Creature) receptor;
        execute((Creature)receptor);
    }

    @Override
    public void undo(LiveReceptor receptor) {
        this.receptor = (Creature) receptor;
        undo((Creature)receptor);
    }

}
