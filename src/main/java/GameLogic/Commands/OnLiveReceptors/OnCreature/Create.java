package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Commands.CommandName;
import GameLogic.Board.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create extends OnCreature {
    private Position[] positions;

    public Create(){
        super(CommandName.CREATE_CREATURE);
    }

    public void setPositions(Position[] positions) {
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
            for (int i = 0; i < positions.length; ++i) {
                jsonPositions.put(positions[i].toJson());
            }
            create.put("positions", jsonPositions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return create;
    }
}