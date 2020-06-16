package network.jsonUtils;

import gameLogic.commands.CommandName;
import gameLogic.commands.playersAction.PlayCard;
import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
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

    public PlayersAction getPlayerAction(Player player, String receivedMessage) {
        PlayersAction action = null;
        try {
            JSONObject jsonAction = new JSONObject(receivedMessage);
            String type = jsonAction.getString(Messages.JSON_TYPE);

            if (type.equals(Messages.JSON_TYPE_PLAY)) {

                String playerName = jsonAction.getString(Messages.JSON_TYPE_PLAYER);
                if (playerName.equals(player.getName())) {
                    CommandName actionName = CommandName.getCommandName(jsonAction.getString(Messages.JSON_TYPE_NAME));

                    if (actionName != null && actionName.isPlayerAction()) {
                        action = (PlayersAction) actionName.getCommand();

                        if (actionName.equals(CommandName.PLAY_CARD)) {
                            int cardID = jsonAction.getInt(Messages.JSON_TYPE_CARD_ID);
                            for (Card card : player.getHand()) {
                                if (cardID == card.getID()) {
                                    ((PlayCard)action).setCardToPlay(card);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
