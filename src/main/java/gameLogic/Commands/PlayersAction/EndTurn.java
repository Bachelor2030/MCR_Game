package gameLogic.Commands.PlayersAction;

import gameLogic.Commands.CommandName;

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
