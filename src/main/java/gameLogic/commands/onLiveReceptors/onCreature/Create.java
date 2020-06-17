package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.receptors.Creature;
import network.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create extends OnCreature {
  private Creature creature;
  private Spot position;

  public Create() {
    super(CommandName.CREATE_CREATURE);
  }

  public void setPosition(Spot positions) {
    this.position = position;
  }

  @Override
  public void execute(Creature creature) {
    this.creature.place(position);
  }

  @Override
  public void undo(Creature creature) {
    this.creature.place(null);
  }

  @Override
  public JSONObject toJson() {
    JSONObject create = super.toJson();
    JSONArray jsonPositions = new JSONArray();
    try {
      if (position != null) {
        jsonPositions.put(position.toJson());
        create.put(Messages.JSON_TYPE_POSITION, jsonPositions);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return create;
  }

  public Creature getCreature() {
    return creature;
  }

  public void setCreature(Creature creature) {
    this.creature = creature;
  }
}
