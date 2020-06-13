package Server.Game.ModelClasses.Commands.GUI;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;

public abstract class GUICommand extends ConcreteCommand {

    public GUICommand(CommandName name) {
        super(name);
    }
}
