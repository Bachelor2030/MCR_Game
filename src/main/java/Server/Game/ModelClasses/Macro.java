package Server.Game.ModelClasses;

import Server.Game.Card.Commands.CommandName;

import java.util.Arrays;

public class Macro implements Command {
    private Command[] commands;

    public Macro(Command[] commands) {
        this.commands = Arrays.copyOf(commands, commands.length);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for(Command command : commands) {
            command.undo();
        }
    }

    @Override
    public CommandName getName() {
        return null;
    }
}
