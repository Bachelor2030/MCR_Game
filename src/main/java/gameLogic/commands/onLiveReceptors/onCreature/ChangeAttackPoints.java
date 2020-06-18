package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Creature;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeAttackPoints extends OnCreature {
  private int newAP;
  private int oldAP;

  public ChangeAttackPoints() {
    super(CommandName.CHANGE_AP);
  }

  public void setAttackPoints(int ap) {
    newAP = ap;
  }

  @Override
  public void execute(Creature creature) {
    oldAP = creature.getAttackPoints();
    creature.setAttackPoints(newAP);
  }

  @Override
  public void undo(Creature creature) {
    creature.setAttackPoints(oldAP);
  }

  @Override
  public JSONObject toJson() {
    JSONObject changeAP = super.toJson();
    try {
      changeAP.put(Messages.JSON_TYPE_AP, newAP);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return changeAP;
  }
}
