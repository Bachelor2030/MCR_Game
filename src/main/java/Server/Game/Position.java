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

    public Position() {
        try {
            line = new Line(1);
            position = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        return line.getSpot(position).isEmpty();
    }

    public boolean isTrapped() {
        return line.getSpot(position).isTrapped();
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
}
