package gameLogic.Commands.OnLiveReceptors;

import gameLogic.Commands.CommandName;
import gameLogic.Receptors.LiveReceptor;
import org.json.JSONException;
import org.json.JSONObject;

public class Heal extends OnLiveReceptors {
    private int lifePoints;

    public Heal() {
        super(CommandName.HEAL);
    }

    public void setHealingPoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;

        for (LiveReceptor receptor : receptors) {
            if(receptor != null)
                receptor.gainLifePoints(lifePoints);
        }
    }

    @Override
    public void undo() {
        for (LiveReceptor receptor : receptors) {
            receptor.loseLifePoints(lifePoints);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject heal = super.toJson();
        try {
            heal.put("lifepoints", lifePoints);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return heal;
    }
}
