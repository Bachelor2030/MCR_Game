package Server.Game.Card.Commands.OnLiveReceptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.ConcreteCommand;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class OnLiveReceptors extends ConcreteCommand {
    protected LiveReceptor[] receptors;

    public OnLiveReceptors(CommandName name) {
        super(name);
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
    }
}
