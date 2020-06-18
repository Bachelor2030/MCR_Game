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

  public abstract void execute(Player player, ServerSharedState serverSharedState);

  public abstract void undo(Player player, ServerSharedState serverSharedState);

  @Override
  public Receptor getReceptor() {
    return player;
  }

  @Override
  public void execute(Receptor receptor, ServerSharedState serverSharedState) {
    player = (Player) receptor;
    player.addLastMove(this);
    execute((Player) receptor, serverSharedState);
  }

  @Override
  public void undo(Receptor receptor, ServerSharedState serverSharedState) {
    player = (Player) receptor;
    player.removeLastMove(this);
    undo((Player) receptor, serverSharedState);
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
