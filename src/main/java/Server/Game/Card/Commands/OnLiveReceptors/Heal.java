package Server.Game.Card.Commands.OnLiveReceptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public class Heal extends OnLiveReceptors {
    private int lifePoints;

    public Heal() {
        super(CommandName.HEAL);
    }

    public void setHealingPoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;

        for (LiveReceptor receptor : receptors) {
            if(receptor != null)
                receptor.gainLifePoints(lifePoints);
        }
    }

    @Override
    public void undo() {
        for (LiveReceptor receptor : receptors) {
            receptor.loseLifePoints(lifePoints);
        }
    }
}
