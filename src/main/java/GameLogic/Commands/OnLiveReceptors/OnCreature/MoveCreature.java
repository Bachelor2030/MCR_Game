package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Board.Spot;
import GameLogic.Commands.CommandName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class MoveCreature extends OnCreature {
    protected Spot[]
            from,
            to;

    public MoveCreature(CommandName name) {
        super(name);
    }

    public Spot[] getFrom() {
        return from;
    }

    public Spot[] getTo() {
        return to;
    }

    public void setFrom(Spot[] from) {
        this.from = from;
    }

    public void setTo(Spot[] to) {
        this.to = to;
    }

    @Override
    public JSONObject toJson() {
        JSONObject moveCreature = super.toJson();
        JSONArray positions = new JSONArray();
        try {
            for (int i = 0; i < from.length; ++i) {
                JSONObject position = new JSONObject();
                position.put("positionFrom", from[i].toJson());
                position.put("positionTo", to[i].toJson());
                positions.put(position);
            }
            moveCreature.put("positions", positions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moveCreature;
    }
}
