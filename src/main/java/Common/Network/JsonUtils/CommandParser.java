package Common.Network.JsonUtils;

import Common.GameBoard.Board;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Macro;
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
            int cardID = command.getInt("cardID");

            ConcreteCommand concreteCommand = CommandName.getCommandName(command.getString("name")).getCommand();

            int line, spot;

            // TODO Tout refaire

            if (concreteCommand.getName() != CommandName.DRAW                &&
                concreteCommand.getName() != CommandName.DISCARD             &&
                concreteCommand.getName() != CommandName.RETREAT_CREATURE    &&
                concreteCommand.getName() != CommandName.ADVANCE_CREATURE    ) {
                line = command.getJSONObject("position").getInt("line");
                spot = command.getJSONObject("position").getInt("spot");

            }


            // TODO set les infos données dans la commande

            commands.add(concreteCommand);
        }

        return new Macro(commands);
    }
}
