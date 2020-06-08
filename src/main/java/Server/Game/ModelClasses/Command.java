package Server.Game.ModelClasses;

import Server.Game.Card.Commands.CommandName;

public interface Command {
    void execute();
    void undo();

}
