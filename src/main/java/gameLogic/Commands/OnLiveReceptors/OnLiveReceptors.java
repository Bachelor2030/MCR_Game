package gameLogic.Commands.OnLiveReceptors;

import gameLogic.Receptors.Creature;
import gameLogic.Commands.CommandName;
import gameLogic.Commands.OnLiveReceptors.OnCreature.ChangeMovementsPoints;
import gameLogic.Commands.ConcreteCommand;
import gameLogic.Commands.OnLiveReceptors.OnCreature.ChangeAttackPoints;
import gameLogic.Receptors.LiveReceptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class OnLiveReceptors extends ConcreteCommand {
    protected LiveReceptor[] receptors;

    public OnLiveReceptors(CommandName name) {
        super(name);
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
        if (this.getName() == CommandName.KILL) {
            int[] lp = new int[receptors.length];
            for (int i = 0; i < receptors.length; i++) {
                lp[i] = receptors[i].getLifePoints();
            }
            ((Kill)this).setLifePoints(lp);
        } else if (this.getName() == CommandName.CHANGE_AP) {
            ((ChangeAttackPoints)this).setAttackPoints(((Creature)receptors[0]).getAttackPoints());
        } else if (this.getName() == CommandName.CHANGE_MP) {
            ((ChangeMovementsPoints)this).setMovementsPoints(((Creature)receptors[0]).getSteps());
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject onLiveReceptors = super.toJson();

        JSONArray liveReceptors = new JSONArray();
        if (receptors != null) {
            for (LiveReceptor liveReceptor : receptors) {
                liveReceptors.put(liveReceptor.toJson());
            }
        }

        try {
            onLiveReceptors.put("receptors", liveReceptors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return onLiveReceptors;
    }
}