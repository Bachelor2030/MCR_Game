package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.board.Spot;
import gameLogic.receptors.Creature;
import gameLogic.commands.CommandName;
import gameLogic.receptors.LiveReceptor;
import gui.receptors.GUICreature;
import network.Messages;

import org.json.JSONException;
import org.json.JSONObject;

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
            changePoints.put(Messages.JSON_TYPE_POSITION, position.toJson());

            switch (pointsType) {
                // Movement Points
                case 'M':
                    changePoints.put(Messages.JSON_TYPE_MP, newPointValue);
                    break;
                // Attack Points
                case 'A':
                    changePoints.put(Messages.JSON_TYPE_AP, newPointValue);
                    break;
                // Life Points
                case 'L':
                    changePoints.put(Messages.JSON_TYPE_LP, newPointValue);
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
        GUICreature creature = (GUICreature)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();

        switch (pointsType) {
            // Movement Points
            case 'M':
                oldPointValue = creature.getSteps();
                creature.setMovementsPoints(newPointValue);
                return;
            // Attack Points
            case 'A':
                oldPointValue = creature.getAttackPoints();
                creature.setAttackPoints(newPointValue);
                return;
            // Life Points
            case 'L':
                oldPointValue = creature.getLifePoints();
                creature.setLifePoints(newPointValue);
                return;
            default: return;
        }
    }

    @Override
    public void undo(GameBoard gameBoard) {
        GUICreature creature = (GUICreature)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();

        switch (pointsType) {
            // Movement Points
            case 'M':
                creature.setMovementsPoints(oldPointValue);
                return;
            // Attack Points
            case 'A':
                creature.setAttackPoints(oldPointValue);
                return;
            // Life Points
            case 'L':
                creature.setLifePoints(oldPointValue);
                return;
            default: return;
        }
    }
}