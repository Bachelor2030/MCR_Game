package Common.Network.JsonUtils;

import Common.GameBoard.Board;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.Commands.GUI.OnPosition.GUIOnPosition;
import Server.Game.ModelClasses.Commands.GUI.OnPosition.ToPosition.GUIMoveCreature;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Macro;
import Server.Game.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GUICommandParser {
    public Macro parse(String jsonContent, Board board) throws JSONException {
        JSONObject obj = new JSONObject(jsonContent);
        ArrayList<ConcreteCommand> commands = new ArrayList<>();
        JSONArray jsonCommands = obj.getJSONArray("commands");

        for (int commandNo = 0; commandNo < jsonCommands.length(); ++commandNo) {
            JSONObject command = jsonCommands.getJSONObject(commandNo);

            String player = command.getString("player");
            int cardID = command.getInt("cardID");

            ConcreteCommand concreteCommand = CommandName.getCommandName(command.getString("name")).getCommand();

            int line, spot;


            if (concreteCommand.getName() != CommandName.GUI_DRAW                &&
                concreteCommand.getName() != CommandName.GUI_DISCARD             &&
                concreteCommand.getName() != CommandName.GUI_RETREAT_CREATURE    &&
                concreteCommand.getName() != CommandName.GUI_ADVANCE_CREATURE    ) {
                line = command.getJSONObject("position").getInt("line");
                spot = command.getJSONObject("position").getInt("spot");
                ((GUIOnPosition)concreteCommand).setPosition(new Position(board.getLine(line), spot));
            }
            else if (concreteCommand.getName() == CommandName.GUI_RETREAT_CREATURE ||
                     concreteCommand.getName() == CommandName.GUI_ADVANCE_CREATURE ) {
                line = command.getJSONObject("positionFrom").getInt("line");
                spot = command.getJSONObject("positionFrom").getInt("spot");
                ((GUIOnPosition)concreteCommand).setPosition(new Position(board.getLine(line), spot));

                line = command.getJSONObject("positionTo").getInt("line");
                spot = command.getJSONObject("positionTo").getInt("spot");
                ((GUIMoveCreature)concreteCommand).setTo(new Position(board.getLine(line), spot));
            }

            // TODO set les infos données dans la commande

            commands.add(concreteCommand);
        }

        return new Macro(commands);
    }
}
