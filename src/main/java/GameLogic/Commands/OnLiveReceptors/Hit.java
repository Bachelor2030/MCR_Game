package GameLogic.Commands.OnLiveReceptors;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.LiveReceptor;
import Network.Utilities.JsonClient;
import org.json.JSONException;
import org.json.JSONObject;

public class Hit extends OnLiveReceptors {
    private int attackPoints;

    public Hit() {
        super(CommandName.HIT);
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;

        for (LiveReceptor receptor : receptors) {
            if(receptor != null)
                receptor.loseLifePoints(attackPoints);
        }
    }

    @Override
    public void undo() {
        for (LiveReceptor receptor : receptors) {
            receptor.gainLifePoints(attackPoints);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject hit = super.toJson();
        try {
            hit.put("attackpoints", attackPoints);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hit;
    }
}
