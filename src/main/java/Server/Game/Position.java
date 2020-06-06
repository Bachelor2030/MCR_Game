package Server.Game;

import Common.GameBoard.Line;
import Server.Game.ModelClasses.Receptor;

import java.io.IOException;

public class Position {
    private Line line;
    private int position;

    public Position(Line line, int position) {
        this.line = line;
        this.position = position;
    }


    public boolean isEmpty() {
        return line.getSpot(position).isEmpty();
    }

    public Receptor getOccupant() {
        return line.getSpot(position).getOccupant();
    }

    public void setOccupant(Receptor occupant) {
        line.getSpot(position).setOccupant(occupant);
    }

    public void leave() {
        line.getSpot(position).leave();
    }

    public Position next() {
        return new Position(line, position + 1);
    }
}
