package network.jsonUtils;

import gameLogic.board.Board;
import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.commands.Macro;
import gameLogic.commands.guiCommands.*;
import gui.board.GUIBoard;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// TODO s'assurer qu'on récupère tout !!!
public class CommandParser {
  public Macro parse(String jsonContent, GUIBoard board) throws JSONException {
    JSONObject obj = new JSONObject(jsonContent);
    ArrayList<ConcreteCommand> commands = new ArrayList<>();
    JSONArray jsonCommands = obj.getJSONArray("commands");

    for (int commandNo = 0; commandNo < jsonCommands.length(); ++commandNo) {
      JSONObject command = jsonCommands.getJSONObject(commandNo);

      String player = command.getString("Player");
      CommandName name = CommandName.getCommandName(command.getString("name"));

      ConcreteCommand concreteCommand = null;

      if (name == CommandName.ADVANCE_CREATURE || name == CommandName.RETREAT_CREATURE) {
        concreteCommand = new Move();
        ((Move) concreteCommand)
            .setFrom(
                board.getSpot(
                    command.getJSONObject("positionFrom").getInt("line"),
                    command.getJSONObject("positionFrom").getInt("spot")));

        ((Move) concreteCommand)
            .setTo(
                board.getSpot(
                    command.getJSONObject("positionTo").getInt("line"),
                    command.getJSONObject("positionTo").getInt("spot")));
      } else if (name == CommandName.ABANDON) {
        concreteCommand = new EndGame();
        // Todo s'assurer qu'on dit au bon joueur s'il a gagné ou perdu
        ((EndGame) concreteCommand).setPlayerState('L');
      } else if (name == CommandName.DRAW || name == CommandName.DRAW_TYPE_FROM_DISCARD) {
        concreteCommand = new AddCard();
        ((AddCard) concreteCommand).setCardID(command.getInt("cardID"));
      } else if (name == CommandName.DISCARD) {
        concreteCommand = new RemoveCard();
        ((RemoveCard) concreteCommand).setCardID(command.getInt("cardID"));
      } else if (name == CommandName.KNOCK_OUT) {
        concreteCommand = new KnockOutCreature();
        ((KnockOutCreature) concreteCommand)
            .setPosition(
                board.getSpot(
                    command.getJSONObject("position").getInt("line"),
                    command.getJSONObject("position").getInt("spot")));
      } else if (name == CommandName.HIT || name == CommandName.HEAL || name == CommandName.KILL) {
        concreteCommand = new ChangePoints();
        ((ChangePoints) concreteCommand)
            .setPosition(
                board.getSpot(
                    command.getJSONObject("position").getInt("line"),
                    command.getJSONObject("position").getInt("spot")));
        ((ChangePoints) concreteCommand).setPointsType('L');
        ((ChangePoints) concreteCommand).setNewPointValue(command.getInt("effect"));
      } else if (name == CommandName.CHANGE_MP) {
        concreteCommand = new ChangePoints();
        ((ChangePoints) concreteCommand)
            .setPosition(
                board.getSpot(
                    command.getJSONObject("position").getInt("line"),
                    command.getJSONObject("position").getInt("spot")));
        ((ChangePoints) concreteCommand).setPointsType('M');
        ((ChangePoints) concreteCommand).setNewPointValue(command.getInt("effect"));
      } else if (name == CommandName.CHANGE_AP) {
        concreteCommand = new ChangePoints();
        ((ChangePoints) concreteCommand)
            .setPosition(
                board.getSpot(
                    command.getJSONObject("position").getInt("line"),
                    command.getJSONObject("position").getInt("spot")));
        ((ChangePoints) concreteCommand).setPointsType('A');
        ((ChangePoints) concreteCommand).setNewPointValue(command.getInt("effect"));
      } else if (name == CommandName.CREATE_TRAP || name == CommandName.CREATE_CREATURE) {
        concreteCommand = new Place();
        ((Place) concreteCommand)
            .setPosition(
                board.getSpot(
                    command.getJSONObject("position").getInt("line"),
                    command.getJSONObject("position").getInt("spot")));
        ((Place) concreteCommand).setCardID(command.getInt("cardID"));
      }

      if (concreteCommand != null) {
        ((GuiCommand) concreteCommand).setPlayerName(player);
      }

      commands.add(concreteCommand);
    }

    return new Macro(commands);
  }
}
