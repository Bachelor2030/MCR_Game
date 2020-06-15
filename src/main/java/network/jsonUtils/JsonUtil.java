package network.jsonUtils;

import gameLogic.commands.CommandName;
import gameLogic.commands.playersAction.PlayersAction;
import network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {

    public String getJsonContent(String file) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public PlayersAction getPlayerAction(String receivedMessage) {
        // TODO parse message to playerAction
        PlayersAction action = null;
        try {
            JSONObject jsonAction = new JSONObject(receivedMessage);
            String type = jsonAction.getString(Messages.JSON_TYPE);

            if (type.equals(Messages.JSON_TYPE_PLAY)) {
                String playerName = jsonAction.getString(Messages.JSON_TYPE_PLAYER);
                String actionName = jsonAction.getString(Messages.JSON_TYPE_NAME);

                action = CommandName.getCommandName(actionName).getCommand();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
