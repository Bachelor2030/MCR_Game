package GameLogic.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import GameLogic.ModelClasses.Commands.CommandName;
import GameLogic.Position;

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