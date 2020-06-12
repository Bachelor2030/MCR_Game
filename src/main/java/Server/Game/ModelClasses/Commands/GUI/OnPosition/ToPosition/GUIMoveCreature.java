package Server.Game.ModelClasses.Commands.GUI.OnPosition.ToPosition;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.Commands.GUI.OnPosition.GUIOnPosition;
import Server.Game.Position;

public abstract class GUIMoveCreature extends GUIOnPosition {
    Position to;

    public GUIMoveCreature(CommandName name) {
        super(name);
    }

    public void setTo(Position to) {
        this.to = to;
    }
}
