package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.Messages;
import network.states.ServerSharedState;
import network.states.ServerThreadState;
import org.json.JSONException;
import org.json.JSONObject;

public class EndTurn extends PlayersAction {
  public EndTurn() {
    super(CommandName.END_TURN);
  }

  @Override
  public void execute(Player player) {
    player.endTurn();

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_WAIT_TURN);
      player.getServerSharedState().pushJsonToSend(jsonObject, player.getServerSharedState().getPlayingId());
      //serverSharedState.setIntendToSendJson(serverSharedState.getPlayingId(), true);

      int other = player.getServerSharedState().otherPlayer(player.getServerSharedState().getPlayingId() );

      jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_YOUR_TURN);
      player.getServerSharedState().pushJsonToSend(jsonObject, other);
      //serverSharedState.setIntendToSendJson(other, true);

      player.getServerSharedState().setWorkerState(player.getServerSharedState().getPlayingId(), ServerThreadState.CLIENT_LISTENING);
      player.getServerSharedState().setWorkerState(other, ServerThreadState.SERVER_LISTENING);

      player.getServerSharedState().nextPlayer();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo(Player player) {
    player.continueTurn();
  }
}
