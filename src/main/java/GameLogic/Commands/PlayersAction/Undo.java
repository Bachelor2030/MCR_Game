package GameLogic.Commands.PlayersAction;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;

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
