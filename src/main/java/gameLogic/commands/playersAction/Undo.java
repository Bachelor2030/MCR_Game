package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;

public class Undo extends PlayersAction {
    public Undo() {
        super(CommandName.UNDO);
    }

    @Override
    public void execute(Player player) {
        player.undoLastMove();
    }

    @Override
    public void undo(Player player) {
        player.redoLastMove();
    }
}
