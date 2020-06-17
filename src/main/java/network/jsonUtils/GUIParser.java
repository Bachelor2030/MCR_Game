package network.jsonUtils;

import gameLogic.invocator.card.CardType;
import gui.receptors.GUICard;
import network.Messages;
import network.states.ClientSharedState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GUIParser {
  private static ArrayList<GUICard> cards = new ArrayList<>();
  private ClientSharedState clientSharedState;
  private String jsonMessage;
  private JSONObject gameState;

  public GUIParser(String jsonInit, ClientSharedState clientSharedState) {
    this.jsonMessage = jsonInit;
    this.clientSharedState = clientSharedState;
    try {
      gameState = new JSONObject(jsonInit).getJSONObject(Messages.JSON_GAMESTATE);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public static GUICard getCardFromId(int idCard) {
    GUICard guiCard = null;
    JSONObject obj;

    try {
      obj = new JSONObject(new JsonUtil().getJsonContent("src/main/resources/json/cards.json"));

      JSONArray arr = obj.getJSONArray(Messages.JSON_TYPE_CARDS);

      for (int i = 0; i < arr.length(); i++) {
        JSONObject card = arr.getJSONObject(i);

        int id = card.getInt("id");
        String cardName = card.getString("name");
        CardType cardType = CardType.getType(card.getString("type"));
        int cardCost = card.getInt("cost");
        String description = card.getString(Messages.JSON_TYPE_DESCRIPTION);

        cards.add(new GUICard(id, cardName, cardType, cardCost, description));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    for (GUICard c : cards) {
      if (c.getId() == idCard) {
        guiCard = c;
        break;
      }
    }

    return guiCard;
  }

  /**
   * Récupère les dimensions du board
   *
   * @return
   */
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

  /**
   * Récupère la liste des cartes dans la main du joueur
   *
   * @return
   */
  public ArrayList<GUICard> getCardsFromInit() {
    ArrayList<GUICard> playerCards = new ArrayList<>();
    try {
      JSONArray cards = gameState.getJSONArray(Messages.JSON_TYPE_CARDS);

      for (int c = 0; c < cards.length(); c++) {
        JSONObject card = cards.getJSONObject(c);
        playerCards.add(
            new GUICard(
                card.getInt(Messages.JSON_TYPE_CARD_ID),
                card.getString(Messages.JSON_TYPE_NAME),
                CardType.getType(card.getString(Messages.JSON_TYPE)),
                card.getInt(Messages.JSON_TYPE_COST),
                clientSharedState));
      }
    } catch (JSONException | FileNotFoundException e) {
      e.printStackTrace();
    }
    return playerCards;
  }

  /**
   * À qui le tour ? (jouer ou attendre)
   *
   * @return
   */
  public String getTurnFromInit() {
    try {
      return gameState.getString(Messages.JSON_TYPE_TURN);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * Récupère le nom de l'adversaire
   *
   * @return
   */
  public String[] getEnemyFromInit() {
    String[] enemy = new String[2];
    try {
      enemy[0] = gameState.getString(Messages.JSON_TYPE_ENEMY_NAME);
      enemy[1] = gameState.getString(Messages.JSON_TYPE_ENEMY_IMAGE);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return enemy;
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
