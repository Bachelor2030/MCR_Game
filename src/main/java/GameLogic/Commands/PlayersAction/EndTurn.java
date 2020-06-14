package GameLogic.Commands.PlayersAction;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;

public class EndTurn extends PlayersAction {
    public EndTurn() {
        super(CommandName.END_TURN);
    }

    @Override
    public void execute(Player player) {
        player.endTurn();
    }

    @Override
    public void undo(Player player) {
        player.continueTurn();
    }
}
