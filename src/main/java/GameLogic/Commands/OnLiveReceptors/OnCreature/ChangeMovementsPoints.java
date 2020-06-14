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

    public void setNewMP(int newMP) {
        this.newMP = newMP;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).setMovementsPoints(newMP);
    }

    public void setMovementsPoints(int mp) {
        oldMP = mp;
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).wakeUp();
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
