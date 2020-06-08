package Server.Game.Card.Commands.OnLiveReceptors.OnCreature;

import Server.Game.Card.Commands.CommandName;
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
        if(receptors != null && receptors[0] != null) {
            ((Creature) receptors[0]).place(position);
        }
    }

    @Override
    public void undo() {
        if(receptors != null && receptors[0] != null) {
            ((Creature) receptors[0]).place(null);
        }
    }
}