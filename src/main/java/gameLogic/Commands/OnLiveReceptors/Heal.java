package gameLogic.commands.onLiveReceptors;

import gameLogic.commands.CommandName;
import gameLogic.receptors.LiveReceptor;
import network.Messages;

import org.json.JSONException;
import org.json.JSONObject;

public class Heal extends OnLiveReceptor {
    private int lifePoints;

    public Heal() {
        super(CommandName.HEAL);
    }

    public void setHealingPoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    @Override
    public void execute(LiveReceptor liveReceptor) {
        liveReceptor.gainLifePoints(lifePoints);
    }

    @Override
    public void undo(LiveReceptor liveReceptor) {
        liveReceptor.loseLifePoints(lifePoints);
    }

    @Override
    public JSONObject toJson() {
        JSONObject heal = super.toJson();
        try {
            heal.put(Messages.JSON_TYPE_LP, lifePoints);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return heal;
    }
}
