package GameLogic.Commands.OnLiveReceptors;

import GameLogic.Commands.CommandName;
import GameLogic.Commands.ConcreteCommand;
import GameLogic.Receptors.LiveReceptor;
import GameLogic.Receptors.Receptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class OnLiveReceptor extends ConcreteCommand {
    protected LiveReceptor receptor;

    public OnLiveReceptor(CommandName name) {
        super(name);
    }

    public abstract void execute(LiveReceptor liveReceptor);
    public abstract void undo(LiveReceptor liveReceptor);

    @Override
    public LiveReceptor getReceptor() {
        return receptor;
    }

    @Override
    public void execute(Receptor receptor) {
        this.receptor = (LiveReceptor) receptor;
        execute((LiveReceptor)receptor);
    }

    @Override
    public void undo(Receptor receptor) {
        this.receptor = (LiveReceptor) receptor;
        undo((LiveReceptor)receptor);
    }

    @Override
    public JSONObject toJson() {
        JSONObject onLiveReceptors = super.toJson();

        JSONArray liveReceptors = new JSONArray();

        if (receptor != null) {
            liveReceptors.put(receptor.toJson());
        }

        try {
            onLiveReceptors.put("livereceptor", liveReceptors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return onLiveReceptors;
    }
}