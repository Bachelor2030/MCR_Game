package gameLogic.Commands.OnLiveReceptors;

import gameLogic.Commands.CommandName;
import gameLogic.Receptors.LiveReceptor;

public class Kill extends OnLiveReceptors {
    private int[] lifePoints;

    public Kill() {
        super(CommandName.KILL);
    }

    @Override
    public void execute() {
        int i = 0;
        lifePoints = new int[receptors.length];
        for (LiveReceptor receptor : receptors) {
            lifePoints[i++] = receptor.getLifePoints();
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
