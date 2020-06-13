package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;
import Common.Receptors.Creature;

public class Create extends OnCreature {
    private Position[] positions;

    public Create(){
        super(CommandName.CREATE_CREATURE);
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            receptors[i].place(positions[i]);
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            receptors[i].place(null);
    }
}