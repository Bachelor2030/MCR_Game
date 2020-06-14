package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeMovementsPoints extends OnCreature {
    private int newMP;
    private int oldMP;

    public ChangeMovementsPoints() {
        super(CommandName.CHANGE_MP);
    }

    public void setMovementsPoints(int mp) {
        newMP = mp;
    }

    @Override
    public void execute(Creature creature) {
        oldMP = creature.getSteps();
        creature.setMovementsPoints(newMP);
    }

    @Override
    public void undo(Creature creature) {
        creature.setMovementsPoints(oldMP);
    }

    @Override
    public JSONObject toJson() {
        JSONObject changeMP = super.toJson();
        try {
            changeMP.put("newsteps", newMP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return changeMP;
    }
}
