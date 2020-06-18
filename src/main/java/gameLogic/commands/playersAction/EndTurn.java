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
  public void execute(Player player, ServerSharedState serverSharedState) {
    player.endTurn(serverSharedState);

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_WAIT_TURN);
      serverSharedState.pushJsonToSend(jsonObject, serverSharedState.getPlayingId());
      serverSharedState.setIntendToSendJson(serverSharedState.getPlayingId(), true);

      int other = serverSharedState.otherPlayer(serverSharedState.getPlayingId() );

      jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_YOUR_TURN);
      serverSharedState.pushJsonToSend(jsonObject, other);
      serverSharedState.setIntendToSendJson(other, true);

      serverSharedState.setWorkerState(serverSharedState.getPlayingId(), ServerThreadState.CLIENT_LISTENING);
      serverSharedState.setWorkerState(other, ServerThreadState.SERVER_LISTENING);

      serverSharedState.nextPlayer();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo(Player player, ServerSharedState serverSharedState) {
    player.continueTurn();
  }
}
