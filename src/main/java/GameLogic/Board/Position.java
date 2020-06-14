package GameLogic.Board;

import GameLogic.Receptors.Receptor;
import org.json.JSONException;
import org.json.JSONObject;

public class Position {
    private Line line;
    private int position;

    public Position(Line line, int position) {
        this.line = line;
        this.position = position;
    }

    public boolean isEmpty() {
        if(isValid() && line.getSpot(position) != null) {
            return line.getSpot(position).isEmpty();
        }
        return false;
    }

    public boolean isTrapped() {
        if(isValid()) {
            return line.getSpot(position).isTrapped();
        }
        return false;
    }

    public Receptor getOccupant() {
        if(isValid() && line.getSpot(position) != null) {
            return line.getSpot(position).getOccupant();
        }
        return null;
    }

    public void setOccupant(Receptor occupant) {
        if(isValid() && line != null && line.getSpot(position) != null) {
            line.getSpot(position).setOccupant(occupant);
        }
    }

    public void leave() {
        if(line != null && line.getSpot(position) != null) {
            line.getSpot(position).leave();
        }
    }

    public Position next() {
        return new Position(line, position + 1);
    }

    public Position previous() {
        if (position == 0)
            return this;
        return new Position(line, position - 1);
    }

    public Line getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }

    public boolean isValid() {
        return position < line.getNbSpots() && line.getSpot(position) != null;
    }

    public JSONObject toJson() {
        JSONObject position = new JSONObject();
        try {
            position.put("line", line.getLineNumber());
            position.put("spot", position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return position;
    }
}
