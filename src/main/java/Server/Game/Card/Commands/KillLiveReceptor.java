package Server.Game.Card.Commands;

import Server.Game.ModelClasses.LiveReceptor;

import java.util.Arrays;

public class KillLiveReceptor extends ConcreteCommand {
    private LiveReceptor[] receptors;
    private int[] lifePoints;

    public KillLiveReceptor() {
        super(CommandName.KILL);
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
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
