package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeAttackPoints extends OnCreature {
    private int newAP;
    private int oldAP;

    public ChangeAttackPoints() {
        super(CommandName.CHANGE_AP);
    }

    public void setNewAP(int newAP) {
        this.newAP = newAP;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).setAttackPoints(newAP);
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).wakeUp();
    }

    public void setAttackPoints(int ap) {
        oldAP = ap;
    }

    @Override
    public JSONObject toJson() {
        JSONObject changeAP = super.toJson();
        try {
            changeAP.put("newattackpoints", newAP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return changeAP;
    }
}
