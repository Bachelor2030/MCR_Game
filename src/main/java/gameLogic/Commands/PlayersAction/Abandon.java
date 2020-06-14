package GameLogic.Commands.PlayersAction;

import GameLogic.Commands.CommandName;

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
