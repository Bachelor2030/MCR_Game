package GameLogic.Commands.PlayersAction;

import GameLogic.Commands.CommandName;

public class EndTurn extends PlayersAction {
    public EndTurn() {
        super(CommandName.END_TURN);
    }

    @Override
    public void execute() {
        player.endTurn();
    }

    @Override
    public void undo() {
        player.continueTurn();
    }
}
