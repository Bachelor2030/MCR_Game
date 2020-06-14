package gameLogic.board;

import gameLogic.receptors.Receptor;
import gameLogic.receptors.Trap;
import org.json.JSONException;
import org.json.JSONObject;

public class Spot {
    private int spotNumber;
    private Line line;
    private Receptor occupant;

    public Spot(Line line, int spotNumber) {
        this.spotNumber = spotNumber;
        this.line = line;
    }

    public boolean isEmpty() {
        return occupant == null || occupant.getClass() == Trap.class;
    }

    public boolean isTrapped() {
        return occupant != null && occupant.getClass() == Trap.class;
    }

    public Receptor getOccupant() {
        return occupant;
    }

    /**
     * Permet de set l'éventuelle créature présente sur la case.
     *
     * @param occupant : la créature
     */
    public void setOccupant(Receptor occupant) {
        this.occupant = occupant;
    }

    /** Modélise le départ d'une créature de la case. */
    public void leave() {
        this.occupant = null;
    }

    public JSONObject toJson() {
        JSONObject position = new JSONObject();
        try {
            position.put("line", getLineNumber());
            position.put("spot", spotNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return position;
    }

    public int getLineNumber() {
        return line.getLineNumber();
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Spot next() {
        if (spotNumber + 1 < line.getNbSpots())
            return line.getSpot(spotNumber + 1);
        return null;
    }

    public Spot previous() {
        if (spotNumber - 1 > 0)
            return line.getSpot(spotNumber - 1);
        return null;
    }
}
