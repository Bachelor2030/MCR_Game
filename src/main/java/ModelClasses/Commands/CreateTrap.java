package ModelClasses.Commands;

import Game.GameBoard.Position;
import ModelClasses.Receptors.Trap;

public class CreateTrap extends ConcreteCommand {
    private Position position;
    private Trap trap;

    public CreateTrap(Trap trap, Position position){
        super(CommandName.CREATE_TRAP);
        this.trap = trap;
        this.position = position;
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
