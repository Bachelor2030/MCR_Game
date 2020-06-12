package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;
import Common.Receptors.Creature;

public class Create extends OnCreature {
    private Position position;

    public Create(){
        super(CommandName.CREATE_CREATURE);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).place(position);
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).place(null);
    }
}