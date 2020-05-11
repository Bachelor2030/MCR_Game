package ModelClasses.Commands;

import ModelClasses.LiveReceptor;

import java.util.Arrays;

public class HitLiveReceptor extends ConcreteCommand {
    private LiveReceptor[] receptors;
    private int attackPoints;

    public HitLiveReceptor(LiveReceptor[] receptors, int attackPoints) {
        super(CommandName.HIT);
        this.attackPoints = attackPoints;
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
