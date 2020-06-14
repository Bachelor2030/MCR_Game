package GameLogic.Commands.PlayersAction;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;

public class Abandon extends PlayersAction {
    public Abandon() {
        super(CommandName.ABANDON);
    }

    @Override
    public void execute(Player player) {
        player.abandon();
    }

    @Override
    public void undo(Player player) {
        player.undoAbandon();
    }
}
