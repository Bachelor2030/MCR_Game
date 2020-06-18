package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

public class Abandon extends PlayersAction {
  public Abandon() {
    super(CommandName.ABANDON);
  }

  @Override
  public void execute(Player player, ServerSharedState serverSharedState) {
    player.abandon();

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_GAME_END);
      jsonObject.put(Messages.JSON_TYPE_LOSE_WIN, "L");
      serverSharedState.pushJsonToSend(jsonObject, serverSharedState.getPlayingId());
      serverSharedState.setIntendToSendJson(serverSharedState.getPlayingId(), true);

      int other = serverSharedState.otherPlayer(serverSharedState.getPlayingId() );

      jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_GAME_END);
      jsonObject.put(Messages.JSON_TYPE_LOSE_WIN, "W");
      serverSharedState.pushJsonToSend(jsonObject, other);
      serverSharedState.setIntendToSendJson(other, true);

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo(Player player, ServerSharedState serverSharedState) {
    player.undoAbandon();
  }
}
