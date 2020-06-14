package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Board.Spot;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Creature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create extends OnCreature {
    private Creature creature;
    private Spot position;

    public Create(){
        super(CommandName.CREATE_CREATURE);
    }

    public void setPosition(Spot positions) {
        this.position = position;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    @Override
    public void execute(Creature creature) {
        this.creature.place(position);
    }

    @Override
    public void undo(Creature creature) {
        this.creature.place(null);
    }

    @Override
    public JSONObject toJson() {
        JSONObject create = super.toJson();
        JSONArray jsonPositions = new JSONArray();
        try {
            if(position != null) {
                jsonPositions.put(position.toJson());
                create.put("position", jsonPositions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return create;
    }

    public Creature getCreature() {
        return creature;
    }
}