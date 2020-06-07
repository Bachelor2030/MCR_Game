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
        if(isValid()) {
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
        if(isValid()) {
            return line.getSpot(position).getOccupant();
        }
        return null;
    }

    public void setOccupant(Receptor occupant) {
        if(isValid()) {
            line.getSpot(position).setOccupant(occupant);
        }
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

    public boolean isValid() {
        return position < line.getNB_SPOTS();
    }
}
