package Server.Game.Card.Commands.OnLiveReceptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public class Hit extends OnLiveReceptors {
    private int attackPoints;

    public Hit() {
        super(CommandName.HIT);
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;

        for (LiveReceptor receptor : receptors) {
            if(receptor != null)
                receptor.loseLifePoints(attackPoints);
        }
    }

    @Override
    public void undo() {
        for (LiveReceptor receptor : receptors) {
            receptor.gainLifePoints(attackPoints);
        }
    }
}
