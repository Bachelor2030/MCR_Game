package Server.Game.ModelClasses.Commands.PlayersAction;

import Server.Game.ModelClasses.PlayersAction;
import Server.Game.ModelClasses.Commands.CommandName;

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
