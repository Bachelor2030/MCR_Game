package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Board.Spot;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.LiveReceptor;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.Position;

public class ChangePoints extends GuiCommand {
    private Spot position;
    private int newPointValue;
    private int oldPointValue;
    private char pointsType;

    public ChangePoints() {
        super(CommandName.CHANGE_POINTS);
    }

    public void setPosition(Spot position) {
        this.position = position;
    }

    public void setPointsType(char pointsType) {
        this.pointsType = pointsType;
    }

    public void setNewPointValue(int newPointValue) {
        this.newPointValue = newPointValue;
    }

    @Override
    public JSONObject toJson() {
        JSONObject changePoints = super.toJson();

        try {
            changePoints.put("position", position.toJson());

            switch (pointsType) {
                // Movement Points
                case 'M':
                    changePoints.put("newsteps", newPointValue);
                    break;
                // Attack Points
                case 'A':
                    changePoints.put("newattackpoints", newPointValue);
                    break;
                // Life Points
                case 'L':
                    changePoints.put("newlifepoints", newPointValue);
                    break;
                default: break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return changePoints;
    }

    @Override
    public void execute(GameBoard gameBoard) {
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();

        switch (pointsType) {
            // Movement Points
            case 'M':
                oldPointValue = ((Creature)receptor).getSteps();
                ((Creature)receptor).setMovementsPoints(newPointValue);
                return;
            // Attack Points
            case 'A':
                oldPointValue = ((Creature)receptor).getAttackPoints();
                ((Creature)receptor).setAttackPoints(newPointValue);
                return;
            // Life Points
            case 'L':
                oldPointValue = receptor.getLifePoints();
                receptor.setLifePoints(newPointValue);
                return;
            default: return;
        }
    }

    @Override
    public void undo(GameBoard gameBoard) {
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();

        switch (pointsType) {
            // Movement Points
            case 'M':
                ((Creature)receptor).setMovementsPoints(oldPointValue);
                return;
            // Attack Points
            case 'A':
                ((Creature)receptor).setAttackPoints(oldPointValue);
                return;
            // Life Points
            case 'L':
                receptor.setLifePoints(oldPointValue);
                return;
            default: return;
        }
    }
}