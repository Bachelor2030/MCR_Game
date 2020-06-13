package GameLogic.ModelClasses.Commands;

import GameLogic.Receptors.Player;
import GameLogic.ModelClasses.ConcreteCommand;
import GameLogic.Position;
import GameLogic.Receptors.Trap;

public class CreateTrap extends ConcreteCommand {
    private Player player;
    private Position position;
    private Trap trap;

    public CreateTrap(Trap trap){
        super(CommandName.CREATE_TRAP);
        this.trap = trap;
    }

    public CreateTrap() {
        super(CommandName.CREATE_TRAP);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        trap.setPosition(position);
    }

    @Override
    public void undo() {
        trap.setPosition(null);
    }

    @Override
    public String toJson() {
        return "{\"type\" : \"Command\", \"name\" : \"" + name + "\", \"player\" : \"" + player.getName() + "\"}";
    }
}
