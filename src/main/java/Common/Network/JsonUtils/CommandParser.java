package Common.Network.JsonUtils;

import Client.GuiCommands.*;
import Common.GameBoard.Board;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Macro;
import Server.Game.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommandParser {
    public Macro parse(String jsonContent, Board board) throws JSONException {
        JSONObject obj = new JSONObject(jsonContent);
        ArrayList<ConcreteCommand> commands = new ArrayList<>();
        JSONArray jsonCommands = obj.getJSONArray("commands");

        for (int commandNo = 0; commandNo < jsonCommands.length(); ++commandNo) {
            JSONObject command = jsonCommands.getJSONObject(commandNo);

            String player = command.getString("player");
            CommandName name = CommandName.getCommandName(command.getString("name"));

            ConcreteCommand concreteCommand = null;

            if (name == CommandName.ADVANCE_CREATURE ||
                name == CommandName.RETREAT_CREATURE ) {
                concreteCommand = new Move();
                ((Move)concreteCommand)
                        .setFrom(
                                new Position(
                                        board.getLine(command.getJSONObject("positionFrom").getInt("line")),
                                        command.getJSONObject("positionFrom").getInt("spot")
                                )
                        );

                ((Move)concreteCommand).setTo(
                        new Position(
                                board.getLine(command.getJSONObject("positionTo").getInt("line")),
                                command.getJSONObject("positionTo").getInt("spot")
                        )
                );
            }
            else if (name == CommandName.ABANDON) {
                concreteCommand = new EndGame();
                // Todo s'assurer qu'on dit au bon joueur s'il a gagné ou perdu
                ((EndGame)concreteCommand).setplayerState('L');
            }
            else if ( name == CommandName.DRAW ||
                    name == CommandName.DRAW_TYPE_FROM_DISCARD) {
                concreteCommand = new AddCard();
                ((AddCard)concreteCommand).setCardID(command.getInt("cardID"));
            }
            else if ( name == CommandName.DISCARD) {
                concreteCommand = new RemoveCard();
                ((RemoveCard)concreteCommand).setCardID(command.getInt("cardID"));
            }
            else if (name == CommandName.KNOCK_OUT) {
                concreteCommand = new KnockOutCreature();
                ((KnockOutCreature)concreteCommand).setPosition(
                        new Position(
                                board.getLine(command.getJSONObject("position").getInt("line")),
                                command.getJSONObject("position").getInt("spot")
                        )
                );
            }
            else if (name == CommandName.HIT ||
                    name == CommandName.HEAL ||
                    name == CommandName.KILL ) {
                concreteCommand = new ChangePoints();
                ((ChangePoints)concreteCommand).setPosition(
                        new Position(
                                board.getLine(command.getJSONObject("position").getInt("line")),
                                command.getJSONObject("position").getInt("spot")
                        )
                );
                ((ChangePoints)concreteCommand).setPointsType('L');
                ((ChangePoints)concreteCommand).setNewPointValue(command.getInt("effect"));
            }
            else if (name == CommandName.CHANGE_MP ) {
                concreteCommand = new ChangePoints();
                ((ChangePoints)concreteCommand).setPosition(
                        new Position(
                                board.getLine(command.getJSONObject("position").getInt("line")),
                                command.getJSONObject("position").getInt("spot")
                        )
                );
                ((ChangePoints)concreteCommand).setPointsType('M');
                ((ChangePoints)concreteCommand).setNewPointValue(command.getInt("effect"));
            }
            else if (name == CommandName.CHANGE_AP ) {
                concreteCommand = new ChangePoints();
                ((ChangePoints)concreteCommand).setPosition(
                        new Position(
                                board.getLine(command.getJSONObject("position").getInt("line")),
                                command.getJSONObject("position").getInt("spot")
                        )
                );
                ((ChangePoints)concreteCommand).setPointsType('A');
                ((ChangePoints)concreteCommand).setNewPointValue(command.getInt("effect"));
            }
            else if (name == CommandName.CREATE_TRAP ||
                    name == CommandName.CREATE_CREATURE) {
                concreteCommand = new Place();
                ((Place)concreteCommand).setPosition(
                        new Position(
                                board.getLine(command.getJSONObject("position").getInt("line")),
                                command.getJSONObject("position").getInt("spot")
                        )
                );
                ((Place)concreteCommand).setCardID(command.getInt("cardID"));
            }

            if (concreteCommand != null) {
                ((GuiCommand)concreteCommand).setPlayerName(player);
            }

            commands.add(concreteCommand);
        }

        return new Macro(commands);
    }
}
