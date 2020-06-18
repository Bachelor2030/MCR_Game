package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.receptors.Player;
import gameLogic.receptors.Receptor;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

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
    player.addLastMove(this);
    execute((Player) receptor);
  }

  @Override
  public void undo(Receptor receptor) {
    player = (Player) receptor;
    player.removeLastMove(this);
    undo((Player) receptor);
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
