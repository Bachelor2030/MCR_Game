package Server.Game.ModelClasses.Commands.GUI.OnPosition;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.Commands.GUI.GUICommand;
import Server.Game.Position;

public abstract class GUIOnPosition extends GUICommand {
    Position position;

    public GUIOnPosition(CommandName name) {
        super(name);
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
