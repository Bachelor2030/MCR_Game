package Server.Game.Card.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.CommandName;

public class ChangeAttackPoints extends OnCreature {
    private int newAP;
    private int oldAP;

    public ChangeAttackPoints() {
        super(CommandName.CHANGE_AP);
    }

    public void setNewAP(int newAP) {
        this.newAP = newAP;
    }

    @Override
    public void execute() {
        if (receptors != null && receptors[0] != null)
            ((Creature)receptors[0]).setAttackPoints(newAP);
    }

    @Override
    public void undo() {
        if (receptors != null && receptors[0] != null)
            ((Creature)receptors[0]).wakeUp();
    }

    public void setAttackPoints(int ap) {
        oldAP = ap;
    }
}
