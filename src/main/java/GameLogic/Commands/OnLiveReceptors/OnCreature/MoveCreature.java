package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Commands.CommandName;
import GameLogic.Board.Position;

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
}
