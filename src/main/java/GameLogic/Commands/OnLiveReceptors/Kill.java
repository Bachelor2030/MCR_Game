package GameLogic.Commands.OnLiveReceptors;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.LiveReceptor;

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

    public void setLifePoints(int[] lp) {
        lifePoints = lp;
    }

    @Override
    public void undo() {
        for (int i = 0; i < receptors.length; i++) {
            receptors[i].gainLifePoints(lifePoints[i]);
        }
    }
}
