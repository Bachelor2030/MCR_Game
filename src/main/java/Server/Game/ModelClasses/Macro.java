package Server.Game.ModelClasses;

import java.util.ArrayList;

public class Macro implements Command {
    private ArrayList<ConcreteCommand> commands;

    public Macro(ArrayList<ConcreteCommand> commands) {
        this.commands = commands;
    }

    public ArrayList<ConcreteCommand> getCommands() {
        return commands;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (ConcreteCommand c : commands) {
            sb.append(c.toString() + " ");
        }
        sb.append("]");
        return sb.toString();
    }
}
