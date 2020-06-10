package Server.Game.ModelClasses.Commands.OnLiveReceptors;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public class Kill extends OnLiveReceptors {
    private int[] lifePoints;

    public Kill() {
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

    public void setLifePoints(int[] lp) {
        lifePoints = lp;
    }
}
