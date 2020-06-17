package gameLogic.commands;

import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.commands.onLiveReceptors.onCreature.OnCreature;
import gameLogic.receptors.Creature;
import gameLogic.receptors.Receptor;
import network.Messages;
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
  public void execute(Receptor receptor) {
    creature.place((Spot) receptor);
  }

  @Override
  public void undo(Receptor receptor) {
    this.creature.place(null);
  }
}
