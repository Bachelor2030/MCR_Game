package Server.Game.Card.Commands;

import Server.Game.ModelClasses.LiveReceptor;

import java.util.Arrays;

public class HitLiveReceptor extends ConcreteCommand {
    private LiveReceptor[] receptors;
    private int attackPoints;

    public HitLiveReceptor(LiveReceptor[] receptors, int attackPoints) {
        super(CommandName.HIT);
        this.attackPoints = attackPoints;
        this.receptors = Arrays.copyOf(receptors, receptors.length);
    }

    public HitLiveReceptor() {
        super(CommandName.HIT);
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
    }

    @Override
    public void execute() {
        for (LiveReceptor receptor : receptors) {
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
