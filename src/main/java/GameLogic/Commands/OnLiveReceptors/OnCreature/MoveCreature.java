package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Commands.CommandName;
import GameLogic.Board.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class MoveCreature extends OnCreature {
    protected Position[]
            from,
            to;

    public MoveCreature(CommandName name) {
        super(name);
    }

    public Position[] getFrom() {
        return from;
    }

    public Position[] getTo() {
        return to;
    }

    public void setFrom(Position[] from) {
        this.from = from;
    }

    public void setTo(Position[] to) {
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
