package Server.Game.Card.Commands;

import Server.Game.ModelClasses.LiveReceptor;

import java.util.Arrays;

public class HitLiveReceptor extends ConcreteCommand {
    private LiveReceptor[] receptors;
    private int attackPoints;

    public HitLiveReceptor() {
        super(CommandName.HIT);
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = Arrays.copyOf(receptors, receptors.length);
    }

    @Override
    public void execute() {
        for (LiveReceptor receptor : receptors) {
            receptor.hit(attackPoints);
        }
    }

    @Override
    public void undo() {
        for (LiveReceptor receptor : receptors) {
            receptor.heal(attackPoints);
        }
    }
}
