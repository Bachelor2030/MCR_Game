package network.jsonUtils;

import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.commands.Macro;
import gameLogic.invocator.card.Card;
import gameLogic.invocator.card.CardType;
import network.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InitParser {
    public ArrayList<Card> readInit(String jsonInit) {
        ArrayList<Card> playerCards = new ArrayList<>();
        try {
            JSONObject init = new JSONObject(jsonInit);
            JSONObject gameStat = init.getJSONObject(Messages.JSON_GAMESTATE);
            JSONArray cards = gameStat.getJSONArray(Messages.JSON_TYPE_CARDS);

            for (int c = 0; c < cards.length(); c++) {
                JSONObject card = cards.getJSONObject(c);
                Card playersCard =
                        new Card(card.getInt(Messages.JSON_TYPE_CARD_ID),
                                card.getString(Messages.JSON_TYPE_NAME),
                                CardType.getType(card.getString(Messages.JSON_TYPE)),
                                card.getInt(Messages.JSON_TYPE_COST));

                ArrayList<ConcreteCommand> cardCommand = new ArrayList<>();
                JSONArray commands = card
                                .getJSONObject(Messages.JSON_TYPE_COMMANDS)
                                .getJSONArray(Messages.JSON_TYPE_COMMANDS);

                for (int cmd = 0; cmd < commands.length(); cmd++) {
                    CommandName name = CommandName
                            .getCommandName(commands.getJSONObject(cmd).getString(Messages.JSON_TYPE_NAME));
                    cardCommand.add(name.getCommand());
                }

                playersCard.setCommand(new Macro(cardCommand));
                playerCards.add(playersCard);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playerCards;
    }
}
