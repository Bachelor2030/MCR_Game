package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.LiveReceptor;
import GameLogic.Board.Position;

public class ChangePoints extends GuiCommand {
    private Position position;
    private int newPointValue;
    private int oldPointValue;
    private char pointsType;

    public ChangePoints() {
        super(CommandName.CHANGE_POINTS);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPointsType(char pointsType) {
        this.pointsType = pointsType;
    }

    public void setNewPointValue(int newPointValue) {
        this.newPointValue = newPointValue;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"");
        sb.append(name);
        sb.append(" (");
        sb.append(pointsType);
        sb.append(")\", \"player\" : ");
        sb.append(playerName);

        sb.append(", \"effect\" : ");
        sb.append(newPointValue);

        sb.append(", \"position\" : { \"line\" : ");
        sb.append(position.getBoardLine());
        sb.append(", \"spot\" : ");
        sb.append(position.getPosition());
        sb.append("}}");

        return sb.toString();
    }

    @Override
    public void execute(GameBoard gameBoard) {
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getBoard()
                .getLine(position.getBoardLine().getNoLine())
                .getSpot(position.getPosition())
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
                .getBoard()
                .getLine(position.getBoardLine().getNoLine())
                .getSpot(position.getPosition())
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