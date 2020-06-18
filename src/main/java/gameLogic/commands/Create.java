package gameLogic.commands;

import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.commands.onLiveReceptors.onCreature.OnCreature;
import gameLogic.receptors.Creature;
import gameLogic.receptors.Receptor;
import network.Messages;
import network.jsonUtils.JsonUtil;
import network.states.ServerSharedState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create extends ConcreteCommand {
  private Creature creature;

  public Create() {
    super(CommandName.CREATE_CREATURE);
  }

  public void setCreature(Creature creature) {
    this.creature = creature;
  }

  @Override
  public Receptor getReceptor() {
    return null;
  }

  public Creature getCreature() {
    return creature;
  }

  @Override
  public void execute(Receptor receptor, ServerSharedState serverSharedState) {
    creature.place((Spot) receptor);
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put(Messages.JSON_TYPE, Messages.JSON_TYPE_UPDATE);
      jsonObject.put(Messages.JSON_TYPE_COMMAND, CommandName.PLACE);
      jsonObject.put(Messages.JSON_TYPE_CARD_ID, creature.getOriginCard().getID());
      JSONObject pos = new JSONObject();
      pos.put(Messages.JSON_TYPE_LINE, ((Spot)receptor).getLineNumber());
      pos.put(Messages.JSON_TYPE_SPOT, ((Spot)receptor).getSpotNumber());
      jsonObject.put(Messages.JSON_TYPE_POSITION, pos);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    serverSharedState.pushJsonToSend(jsonObject, serverSharedState.getPlayingId());
    serverSharedState.setIntendToSendJson(serverSharedState.getPlayingId(), true);
  }

  @Override
  public void undo(Receptor receptor, ServerSharedState serverSharedState) {
    this.creature.place(null);
  }
}
