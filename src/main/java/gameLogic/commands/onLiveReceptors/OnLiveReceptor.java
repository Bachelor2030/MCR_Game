package gameLogic.commands.onLiveReceptors;

import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.receptors.LiveReceptor;
import gameLogic.receptors.Receptor;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class OnLiveReceptor extends ConcreteCommand {
  protected LiveReceptor receptor;

  public OnLiveReceptor(CommandName name) {
    super(name);
  }

  public abstract void execute(LiveReceptor liveReceptor, ServerSharedState serverSharedState);

  public abstract void undo(LiveReceptor liveReceptor, ServerSharedState serverSharedState);

  @Override
  public LiveReceptor getReceptor() {
    return receptor;
  }

  @Override
  public void execute(Receptor receptor) {
    execute(((Spot)receptor).getOccupant());
  }

  @Override
  public void undo(Receptor receptor) {
    undo(((Spot) receptor).getOccupant());
  }

  @Override
  public JSONObject toJson() {
    JSONObject onLiveReceptors = super.toJson();

    JSONArray liveReceptors = new JSONArray();

    if (receptor != null) {
      liveReceptors.put(receptor.toJson());
    }

    try {
      onLiveReceptors.put(Messages.JSON_TYPE_RECEPTOR, liveReceptors);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return onLiveReceptors;
  }
}
