package GameLogic.ModelClasses.Commands.PlayersAction;

import GameLogic.ModelClasses.Commands.CommandName;

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
