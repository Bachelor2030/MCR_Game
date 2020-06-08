package Server.Game.Card.Commands.ActsOnLiveReceptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public class KillLiveReceptor extends ActOnLiveReceptors {
    private int[] lifePoints;

    public KillLiveReceptor() {
        super(CommandName.KILL);
    }

    @Override
    public void execute() {
        for (LiveReceptor receptor : receptors) {
            receptor.loseLifePoints(receptor.getLifePoints());
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < receptors.length; i++) {
            receptors[i].gainLifePoints(lifePoints[i]);
        }
    }
}
