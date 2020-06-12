package Server.Game.ModelClasses.Commands.OnLiveReceptors;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.ChangeMovementsPoints;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.MoveCreature;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.ChangeAttackPoints;
import Server.Game.ModelClasses.LiveReceptor;
import Server.Game.Position;

public abstract class OnLiveReceptors extends ConcreteCommand {
    protected LiveReceptor[] receptors;

    public OnLiveReceptors(CommandName name) {
        super(name);
    }

    public void setReceptors(LiveReceptor[] receptors) {
        this.receptors = receptors;
        if (this.getName() == CommandName.KILL) {
            int[] lp = new int[receptors.length];
            for (int i = 0; i < receptors.length; i++) {
                lp[i] = receptors[i].getLifePoints();
            }
            ((Kill)this).setLifePoints(lp);
        } else if (this.getName() == CommandName.CHANGE_AP) {
            ((ChangeAttackPoints)this).setAttackPoints(((Creature)receptors[0]).getAttackPoints());
        } else if (this.getName() == CommandName.CHANGE_MP) {
            ((ChangeMovementsPoints)this).setMovementsPoints(((Creature)receptors[0]).getSteps());
        }
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < receptors.length; i++) {
            LiveReceptor receptor = receptors[i];
            sb.append("{\"type\" : \"" + name + "\", \"player\" : " + receptor.getOwnerName());

            if (name == CommandName.CHANGE_AP       ||
                name == CommandName.CHANGE_MP       ||
                name == CommandName.CREATE_CREATURE ||
                name == CommandName.HEAL            ||
                name == CommandName.KNOCK_OUT       ||
                name == CommandName.HIT             ||
                name == CommandName.KILL) {
                sb.append(", \"position\" : { \"line\" : " +
                        receptor.getPosition().getLine() +
                        ", \"spot\" : " +
                        receptor.getPosition().getPosition() +
                        "}");
            } else {
                sb.append(", \"positionTo\" : { \"line\" : " +
                        receptor.getPosition().getLine() +
                        ", \"spot\" : " +
                        receptor.getPosition().getPosition() +
                        "}");

                sb.append(", \"positionFrom\" : { \"line\" : " +
                        ((MoveCreature)this).getFrom()[i].getLine() +
                        ", \"spot\" : " +
                        ((MoveCreature)this).getFrom()[i].getPosition() +
                        "}");
            }
        }

        return null;
    }
}