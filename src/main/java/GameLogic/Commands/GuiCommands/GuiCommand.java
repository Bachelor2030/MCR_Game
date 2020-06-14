package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Commands.CommandName;
import GameLogic.Commands.ConcreteCommand;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class GuiCommand extends ConcreteCommand {
    protected String playerName;

    public GuiCommand(CommandName name) {
        super(name);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public abstract void execute(GameBoard gameBoard);

    public abstract void undo(GameBoard gameBoard);

    @Override
    public void execute() {}

    @Override
    public void undo() {}

    @Override
    public JSONObject toJson() {
        JSONObject guiCommand = super.toJson();

        try {
            guiCommand.put("player", playerName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return guiCommand;
    }
}
