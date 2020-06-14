package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeMovementsPoints extends OnCreature {
    private int newMP;
    private int[] oldMP;

    public ChangeMovementsPoints() {
        super(CommandName.CHANGE_MP);
    }

    public void setMovementsPoints(int mp) {
        newMP = mp;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        oldMP = new int[receptors.length];
        for (int i = 0; i < receptors.length; i++) {
            oldMP[i] = ((Creature) receptors[i]).getSteps();
            ((Creature) receptors[i]).setAttackPoints(newMP);
        }
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature) receptors[i]).setMovementsPoints(oldMP[i]);
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
