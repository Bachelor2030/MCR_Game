package GameLogic;

import GameLogic.GameBoard.BoardLine;
import GameLogic.ModelClasses.Receptor;

public class Position {
    private BoardLine boardLine;
    private int position;

    public Position(BoardLine boardLine, int position) {
        this.boardLine = boardLine;
        this.position = position;
    }

    public boolean isEmpty() {
        if(isValid() && boardLine.getSpot(position) != null) {
            return boardLine.getSpot(position).isEmpty();
        }
        return false;
    }

    public boolean isTrapped() {
        if(isValid()) {
            return boardLine.getSpot(position).isTrapped();
        }
        return false;
    }

    public Receptor getOccupant() {
        if(isValid() && boardLine.getSpot(position) != null) {
            return boardLine.getSpot(position).getOccupant();
        }
        return null;
    }

    public void setOccupant(Receptor occupant) {
        if(isValid() && boardLine != null && boardLine.getSpot(position) != null) {
            boardLine.getSpot(position).setOccupant(occupant);
        }
    }

    public void leave() {
        if(boardLine != null && boardLine.getSpot(position) != null) {
            boardLine.getSpot(position).leave();
        }
    }

    public Position next() {
        return new Position(boardLine, position + 1);
    }

    public Position previous() {
        if (position == 0)
            return this;
        return new Position(boardLine, position - 1);
    }

    public BoardLine getBoardLine() {
        return boardLine;
    }

    public int getPosition() {
        return position;
    }

    public boolean isValid() {
        return position < boardLine.getNB_SPOTS() && boardLine.getSpot(position) != null;
    }
}
