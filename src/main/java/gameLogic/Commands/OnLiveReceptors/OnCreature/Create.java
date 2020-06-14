package gameLogic.Commands.OnLiveReceptors.OnCreature;

import gameLogic.Board.Spot;
import gameLogic.Commands.CommandName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create extends OnCreature {
    private Spot[] positions;

    public Create(){
        super(CommandName.CREATE_CREATURE);
    }

    public void setPositions(Spot[] positions) {
        this.positions = positions;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            receptors[i].place(positions[i]);
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            receptors[i].place(null);
    }

    @Override
    public JSONObject toJson() {
        JSONObject create = super.toJson();
        JSONArray jsonPositions = new JSONArray();
        try {
            if (positions != null) {
                for (int i = 0; i < positions.length; ++i) {
                    jsonPositions.put(positions[i].toJson());
                }
                create.put("positions", jsonPositions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return create;
    }
}