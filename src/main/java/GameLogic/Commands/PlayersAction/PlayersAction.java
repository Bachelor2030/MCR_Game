package GameLogic.Commands.PlayersAction;

import GameLogic.Receptors.Player;
import GameLogic.Invocator.Card.Card;
import GameLogic.Invocator.Card.CardType;
import GameLogic.Commands.CommandName;
import GameLogic.Commands.ConcreteCommand;
import GameLogic.Receptors.Receptor;
import Network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public abstract class PlayersAction extends ConcreteCommand {
    private Player player;

    public PlayersAction(CommandName name) {
        super(name);
    }

    public abstract void execute(Player player);
    public abstract void undo(Player player);

    @Override
    public Receptor getReceptor() {
        return player;
    }

    @Override
    public void execute(Receptor receptor) {
        player = (Player) receptor;
        execute((Player)receptor);
    }

    @Override
    public void undo(Receptor receptor) {
        player = (Player) receptor;
        undo((Player)receptor);
    }

    @Override
    public JSONObject toJson() {
        JSONObject playersAction = super.toJson();
        try {
            playersAction.put(Messages.JSON_TYPE_PLAYER, player.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return playersAction;
    }
}
