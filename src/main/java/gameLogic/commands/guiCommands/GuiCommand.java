package gameLogic.commands.guiCommands;

import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.receptors.Receptor;
import gui.GameBoard;
import network.Messages;
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
  public Receptor getReceptor() {
    return null;
  }

  @Override
  public void execute(Receptor receptor) {}

  @Override
  public void undo(Receptor receptor) {}

  @Override
  public JSONObject toJson() {
    JSONObject guiCommand = super.toJson();

    try {
      guiCommand.put(Messages.JSON_TYPE_PLAYER, playerName);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return guiCommand;
  }
}
