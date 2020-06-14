package gameLogic.Commands.OnLiveReceptors.OnCreature;

import gameLogic.Receptors.Creature;
import gameLogic.Commands.CommandName;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeAttackPoints extends OnCreature {
    private int newAP;
    private int[] oldAP;

    public ChangeAttackPoints() {
        super(CommandName.CHANGE_AP);
    }

    public void setAttackPoints(int ap) {
        newAP = ap;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        oldAP = new int[receptors.length];
        for (int i = 0; i < receptors.length; i++) {
            oldAP[i] = ((Creature) receptors[i]).getAttackPoints();
            ((Creature) receptors[i]).setAttackPoints(newAP);
        }
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature) receptors[i]).setAttackPoints(oldAP[i]);
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
