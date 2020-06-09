package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Commands.CommandName;

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

    }
}
