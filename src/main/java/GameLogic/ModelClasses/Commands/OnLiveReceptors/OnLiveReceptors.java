package GameLogic.ModelClasses.Commands.OnLiveReceptors;

import GameLogic.Receptors.Creature;
import GameLogic.ModelClasses.Commands.CommandName;
import GameLogic.ModelClasses.Commands.OnLiveReceptors.OnCreature.ChangeMovementsPoints;
import GameLogic.ModelClasses.Commands.OnLiveReceptors.OnCreature.MoveCreature;
import GameLogic.ModelClasses.ConcreteCommand;
import GameLogic.ModelClasses.Commands.OnLiveReceptors.OnCreature.ChangeAttackPoints;
import GameLogic.ModelClasses.LiveReceptor;

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
        sb.append("{\"content\" : [");

        for (int i = 0; i < receptors.length; i++) {
            LiveReceptor receptor = receptors[i];
            sb.append("{\"type\" : \"Command\", \"name\" : \"" + name + "\", \"player\" : \"" + receptor.getOwnerName() + "\"");

            if (name == CommandName.CHANGE_AP       ||
                name == CommandName.CHANGE_MP       ||
                name == CommandName.CREATE_CREATURE ||
                name == CommandName.HEAL            ||
                name == CommandName.KNOCK_OUT       ||
                name == CommandName.HIT             ||
                name == CommandName.KILL) {
                if(name == CommandName.CREATE_CREATURE) {
                    sb.append(", \"cardID\" : " + ((Creature)receptor).getOriginCard().getID());
                }
                sb.append(", \"position\" : { \"line\" : " +
                        receptor.getPosition().getBoardLine().getNoLine() +
                        ", \"spot\" : " +
                        receptor.getPosition().getPosition() +
                        "}}");
            } else {
                sb.append(", \"positionTo\" : { \"line\" : " +
                        receptor.getPosition().getBoardLine().getNoLine() +
                        ", \"spot\" : " +
                        receptor.getPosition().getPosition() +
                        "}");

                sb.append(", \"positionFrom\" : { \"line\" : " +
                        ((MoveCreature)this).getFrom()[i].getBoardLine().getNoLine() +
                        ", \"spot\" : " +
                        ((MoveCreature)this).getFrom()[i].getPosition() +
                        "}}");
            }

            if (i < receptors.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");
        return sb.toString();
    }
}