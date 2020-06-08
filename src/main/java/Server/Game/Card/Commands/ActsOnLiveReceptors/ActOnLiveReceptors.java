package Server.Game.Card.Commands.ActsOnLiveReceptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.ConcreteCommand;
import Server.Game.ModelClasses.LiveReceptor;

public abstract class ActOnLiveReceptors extends ConcreteCommand {
    protected LiveReceptor[] receptors;

    public ActOnLiveReceptors(CommandName name) {
        super(name);
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
    }
}
