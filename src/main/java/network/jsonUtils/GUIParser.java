package network.jsonUtils;

import gameLogic.invocator.card.CardType;
import gui.GUICard;
import network.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GUIParser {
    private String jsonMessage;
    private JSONObject gameState;

    public GUIParser(String jsonInit) {
        this.jsonMessage = jsonInit;
        try {
            gameState = new JSONObject(jsonInit).getJSONObject(Messages.JSON_GAMESTATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int[] getLinesSpotDimensionFromInit() {
        int[] dimensions = new int[2];
        try {
            dimensions[0] = gameState.getInt(Messages.JSON_TYPE_LINE);
            dimensions[1] = gameState.getInt(Messages.JSON_TYPE_SPOT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dimensions;
    }

    public ArrayList<GUICard> getCardsFromInit() {
        ArrayList<GUICard> playerCards = new ArrayList<>();
        try {
            JSONArray cards = gameState.getJSONArray(Messages.JSON_TYPE_CARDS);

            for (int c = 0; c < cards.length(); c++) {
                JSONObject card = cards.getJSONObject(c);
                playerCards
                    .add(
                        new GUICard(
                            card.getInt(Messages.JSON_TYPE_CARD_ID),
                            card.getString(Messages.JSON_TYPE_NAME),
                            CardType.getType(card.getString(Messages.JSON_TYPE)),
                            card.getInt(Messages.JSON_TYPE_COST)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playerCards;
    }

    public String getTurnFromInit() {
        try {
            return gameState.getString(Messages.JSON_TYPE_TURN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEnemyFromInit() {
        try {
            return gameState.getString(Messages.JSON_TYPE_ENEMYNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
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
 */
}
