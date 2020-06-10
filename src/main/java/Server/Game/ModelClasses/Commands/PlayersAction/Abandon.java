package Server.Game.ModelClasses.Commands.PlayersAction;

import Server.Game.ModelClasses.PlayersAction;
import Server.Game.ModelClasses.Commands.CommandName;

public class Abandon extends PlayersAction {
    public Abandon() {
        super(CommandName.ABANDON);
    }

    @Override
    public void execute() {
        player.abandon();
    }

    @Override
    public void undo() {
        player.undoAbandon();
    }
}
