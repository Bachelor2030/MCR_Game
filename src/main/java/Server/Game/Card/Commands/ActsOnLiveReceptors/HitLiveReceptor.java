package Server.Game.Card.Commands.ActsOnLiveReceptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.LiveReceptor;

public class HitLiveReceptor extends ActOnLiveReceptors {
    private int attackPoints;

    public HitLiveReceptor() {
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
