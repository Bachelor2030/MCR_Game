package Server.Game.Card.Commands;

import Server.Game.Position;
import Common.Receptors.Trap;

public class CreateTrap extends ConcreteCommand {
    private Position position;
    private Trap trap;

    public CreateTrap(Trap trap, Position position){
        super(CommandName.CREATE_TRAP);
        this.trap = trap;
        this.position = position;
    }

    public CreateTrap() {
        super(CommandName.CREATE_TRAP);
    }

    @Override
    public void execute() {
        trap.setPosition(position);
    }

    @Override
    public void undo() {
        trap.setPosition(null);
    }
}
