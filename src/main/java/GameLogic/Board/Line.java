package GameLogic.Board;

import java.util.LinkedList;

public class Line {
    private int lineNumber;
    private LinkedList<Spot> spots;

    public Line(int lineNumber, int nbr_spots) {
        this.lineNumber = lineNumber;
        spots = new LinkedList<>();
        for (int i = 0; i < nbr_spots; i++) {
            spots.add(new Spot(this, i));
        }
    }

    public Spot getSpot(int position) {
        return spots.get(position);
    }

    public int getNbSpots() {
        return spots.size();
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
