package Server.Game.Card.Commands.ActsOnLiveReceptors.OnCreature;

import Server.Game.Card.Commands.CommandName;
import Server.Game.Position;
import Common.Receptors.Creature;

public class CreateCreature extends ActOnCreature {
    private Position position;

    public CreateCreature(){
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