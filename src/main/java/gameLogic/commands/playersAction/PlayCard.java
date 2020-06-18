package gameLogic.commands.playersAction;

import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayCard extends PlayersAction {
  private Card cardToPlay;
  private Spot spot;

  public PlayCard() {
    super(CommandName.PLAY_CARD);
  }

  public PlayCard(Card cardToPlay, Spot spot) {
    super(CommandName.PLAY_CARD);
    this.cardToPlay = cardToPlay;
    this.spot = spot;
  }

  @Override
  public JSONObject toJson() {
    JSONObject playCard = super.toJson();
    try {
      playCard.put(Messages.JSON_TYPE_CARD_ID, cardToPlay.getID());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return playCard;
  }

  @Override
  public void execute(Player player, ServerSharedState serverSharedState) {
    if (player.playCard(cardToPlay, spot, serverSharedState)) {
      player.addLastMoves(cardToPlay.getCommand().getCommands());
    }

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_UPDATE);
      jsonObject.put(Messages.JSON_TYPE_COMMAND, CommandName.REMOVE_CARD);
      jsonObject.put(Messages.JSON_TYPE_CARD_ID, cardToPlay.getID());

      serverSharedState.pushJsonToSend(jsonObject, serverSharedState.getPlayingId());
      serverSharedState.setIntendToSendJson(serverSharedState.getPlayingId(), true);

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo(Player player, ServerSharedState serverSharedState) {
    player.undoCard(cardToPlay, serverSharedState);
  }
}
