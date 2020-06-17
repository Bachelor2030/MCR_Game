package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import network.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class MoveCreature extends OnCreature {
  protected Spot from, to;

  public MoveCreature(CommandName name) {
    super(name);
  }

  public Spot getFrom() {
    return from;
  }

  public Spot getTo() {
    return to;
  }

  public void setFrom(Spot from) {
    this.from = from;
  }

  public void setTo(Spot to) {
    this.to = to;
  }

  @Override
  public JSONObject toJson() {
    JSONObject moveCreature = super.toJson();
    JSONArray positions = new JSONArray();
    try {
      JSONObject position = new JSONObject();
      if (from != null) {
        position.put(Messages.JSON_TYPE_POSITION_FROM, from.toJson());
      }
      if (to != null) {
        position.put(Messages.JSON_TYPE_POSITION_TO, to.toJson());
      }
      positions.put(position);
      moveCreature.put(Messages.JSON_TYPE_POSITION, positions);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return moveCreature;
  }
}
