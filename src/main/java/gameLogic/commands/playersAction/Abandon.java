package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.Messages;
import network.states.ServerSharedState;
import network.states.ServerThreadState;
import org.json.JSONException;
import org.json.JSONObject;

public class Abandon extends PlayersAction {
  public Abandon() {
    super(CommandName.ABANDON);
  }

  @Override
  public void execute(Player player) {
    player.abandon();

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_GAME_END);
      jsonObject.put(Messages.JSON_TYPE_LOSE_WIN, "L");
      player.getServerSharedState().pushJsonToSend(jsonObject, player.getServerSharedState().getPlayingId());
      //serverSharedState.setIntendToSendJson(serverSharedState.getPlayingId(), true);

      int other = player.getServerSharedState().otherPlayer(player.getServerSharedState().getPlayingId() );

      jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_GAME_END);
      jsonObject.put(Messages.JSON_TYPE_LOSE_WIN, "W");
      player.getServerSharedState().pushJsonToSend(jsonObject, other);
      //serverSharedState.setIntendToSendJson(other, true);
/*
      serverSharedState.setWorkerState(player.getId(), ServerThreadState.CLIENT_LISTENING);
      serverSharedState.setWorkerState(other, ServerThreadState.SERVER_LISTENING);*/
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo(Player player) {
    player.undoAbandon();
  }
}
