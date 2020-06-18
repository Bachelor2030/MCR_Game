package network.jsonUtils;

import gameLogic.board.Board;
import gameLogic.board.Spot;
import gameLogic.commands.*;
import gameLogic.commands.playersAction.*;
import gameLogic.invocator.card.Card;
import gameLogic.invocator.card.CardType;
import gameLogic.receptors.Creature;
import gameLogic.receptors.Player;
import gameLogic.receptors.Trap;
import network.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

  public PlayersAction getPlayerAction(Player player, String receivedMessage, Board board) {
    PlayersAction playersAction = null;

    try {
      JSONObject jsonAction = new JSONObject(receivedMessage);
      String type = jsonAction.getString(Messages.JSON_TYPE);

      switch (type) {
        case Messages.JSON_TYPE_PLAY:
          Card cardPlayed = null;
          Spot position;
          int cardID = jsonAction.getInt(Messages.JSON_TYPE_CARD_ID);

          for (Card card : player.getHand()) {
            if (cardID == card.getID()) {
              cardPlayed = card;
              break;
            }
          }

          JSONObject pos = jsonAction.getJSONObject(Messages.JSON_TYPE_POSITION);
          position =
              board.getPosition(
                  pos.getInt(Messages.JSON_TYPE_LINE), pos.getInt(Messages.JSON_TYPE_SPOT));
          return new PlayCard(cardPlayed, position);

        case Messages.JSON_TYPE_END_TURN:
          return new EndTurn();

        case Messages.JSON_TYPE_UNDO:
          return new Undo();

        case Messages.JSON_TYPE_ABANDON:
          return new Abandon();

        default:
          return null;
      }

    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
}

public static ArrayList<Card> parseJsonCards(String json) throws JSONException {
    ArrayList<Card> cards = new ArrayList<>();

    JSONObject obj = new JSONObject(json);

    JSONArray arr = obj.getJSONArray(Messages.JSON_TYPE_CARDS);

    for (int i = 0; i < arr.length(); i++) {
        JSONObject card = arr.getJSONObject(i);

        int id = card.getInt(Messages.JSON_TYPE_CARD_ID);
        String cardName = card.getString(Messages.JSON_TYPE_NAME);
        CardType cardType = CardType.getType(card.getString(Messages.JSON_TYPE));
        int cardCost = card.getInt(Messages.JSON_TYPE_COST);

        ArrayList<ConcreteCommand> concreteCommands = new ArrayList<>();

        if (cardType == CardType.CREATURE) {
            Create create = new Create();
            JSONObject jsonCreature = card.getJSONObject(Messages.JSON_TYPE_CREATURE);
            Creature creature =
                    new Creature(
                            jsonCreature.getString(Messages.JSON_TYPE_NAME),
                            jsonCreature.getInt(Messages.JSON_TYPE_LP),
                            jsonCreature.getInt(Messages.JSON_TYPE_MP),
                            jsonCreature.getInt(Messages.JSON_TYPE_AP));
            create.setCreature(creature);
            concreteCommands.add(create);
        } else if (cardType == CardType.TRAP) {
            JSONObject jsonTrap = card.getJSONObject(cardType.name().toLowerCase());
            ArrayList<ConcreteCommand> trapCommands = new ArrayList<>();
            JSONArray cmds = jsonTrap.getJSONArray(Messages.JSON_TYPE_COMMANDS);

            for (int j = 0; j < cmds.length(); j++) {
                trapCommands.add(CommandName.getCommandName(cmds.getString(j)).getCommand());
            }

            Trap trap = new Trap(jsonTrap.getString(Messages.JSON_TYPE_NAME), new Macro(trapCommands));

            concreteCommands.add(new CreateTrap(trap));
        }

        Card c = new Card(id, cardName, cardType, cardCost);

        c.setCommand(new Macro(concreteCommands));

        for (Create create : c.getCommand().getCreateCreature()) {
            create.getCreature().setOriginCard(c);

        }

        cards.add(c);
    }

    return cards;
    }

}
