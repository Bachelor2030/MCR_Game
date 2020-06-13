package Client.GuiCommands;

import Client.View.GameBoard;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;

public abstract class GuiCommand extends ConcreteCommand {
    protected String playerName;

    public GuiCommand(CommandName name) {
        super(name);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public abstract void execute(GameBoard gameBoard);

    public abstract void undo(GameBoard gameBoard);

    @Override
    public void execute() {}

    @Override
    public void undo() {}
}
