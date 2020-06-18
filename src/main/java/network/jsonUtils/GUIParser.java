package network.jsonUtils;

import gameLogic.commands.CommandName;
import gameLogic.commands.guiCommands.*;
import gameLogic.invocator.card.CardType;
import gui.board.GUIBoard;
import gui.receptors.GUICard;
import gui.receptors.GUICreature;
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
  public boolean getTurnFromInit() {
    try {
      return (gameState.getString(Messages.JSON_TYPE_TURN).equals(Messages.JSON_TYPE_YOUR_TURN));
    } catch (JSONException e) {
      e.printStackTrace();
      return false;
    }
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

  public static GUICreature getCreatureFromCard(int idCard) {

    try {
      JSONObject obj = new JSONObject(new JsonUtil().getJsonContent("src/main/resources/json/cards.json"));

      JSONArray arr = obj.getJSONArray(Messages.JSON_TYPE_CARDS);

      for (int i = 0; i < arr.length(); i++) {

        JSONObject card = arr.getJSONObject(i);
        if (card.getInt(Messages.JSON_TYPE_CARD_ID) == idCard) {
          JSONObject creature = card.getJSONObject(Messages.JSON_TYPE_CREATURE);
          String name = creature.getString(Messages.JSON_TYPE_NAME);
          String img = creature.getString(Messages.JSON_TYPE_IMAGE);
          int life = creature.getInt(Messages.JSON_TYPE_LP);
          int steps = creature.getInt(Messages.JSON_TYPE_MP);
          int attack = creature.getInt(Messages.JSON_TYPE_AP);

          return new GUICreature(name, img, life, steps, attack);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static GUICard getCardFromId(int idCard) {
    GUICard guiCard = null;
    JSONObject obj;

    try {
      obj = new JSONObject(new JsonUtil().getJsonContent("src/main/resources/json/cards.json"));

      JSONArray arr = obj.getJSONArray(Messages.JSON_TYPE_CARDS);

      for (int i = 0; i < arr.length(); i++) {
        JSONObject card = arr.getJSONObject(i);

        int id = card.getInt(Messages.JSON_TYPE_CARD_ID);
        String cardName = card.getString(Messages.JSON_TYPE_NAME);
        CardType cardType = CardType.getType(card.getString(Messages.JSON_TYPE));
        int cardCost = card.getInt(Messages.JSON_TYPE_COST);
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

  public static GuiCommand getCommand(String serverMessage, GUIBoard guiBoard) {
    GuiCommand command = null;
    try {
      JSONObject jsonObject = new JSONObject(serverMessage);
      CommandName commandName = CommandName.getCommandName(jsonObject.getString(Messages.JSON_TYPE_COMMAND));

      if(commandName.equals(CommandName.ADD_CARD)) {
        command = new AddCard();
        ((AddCard)command).setCardID(jsonObject.getInt(Messages.JSON_TYPE_CARD_ID));
      }
      else if(commandName.equals(CommandName.REMOVE_CARD)) {
        command = new RemoveCard();
        ((RemoveCard)command).setCardID(jsonObject.getInt(Messages.JSON_TYPE_CARD_ID));
      }
      else if(commandName.equals(CommandName.KNOCK_OUT_CREATURE)) {
        command = new KnockOutCreature();
        ((KnockOutCreature)command)
                .setPosition(guiBoard
                        .getSpot(jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION)
                                .getInt(Messages.JSON_TYPE_LINE),
                                jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION).getInt(Messages.JSON_TYPE_SPOT)));
      }
      else if(commandName.equals(CommandName.CHANGE_POINTS)) {
        command = new ChangePoints();
        ((ChangePoints)command).setNewPointValue(jsonObject.getInt(Messages.JSON_TYPE_EFFECT));
        ((ChangePoints)command).setPointsType(jsonObject.getString(Messages.JSON_TYPE_POINTS_TYPE).charAt(0));
        ((ChangePoints)command).setPosition(guiBoard
                .getSpot(jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION)
                                .getInt(Messages.JSON_TYPE_LINE),
                        jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION).getInt(Messages.JSON_TYPE_SPOT)));
      }
      else if(commandName.equals(CommandName.MOVE)) {
        command = new Move();
        ((Move)command).setFrom(guiBoard
                .getSpot(jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION_FROM)
                                .getInt(Messages.JSON_TYPE_LINE),
                        jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION).getInt(Messages.JSON_TYPE_SPOT)));

        ((Move)command).setTo(guiBoard
                .getSpot(jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION_TO)
                                .getInt(Messages.JSON_TYPE_LINE),
                        jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION).getInt(Messages.JSON_TYPE_SPOT)));
      }
      else if(commandName.equals(CommandName.PLACE)) {
        command = new Place();
        ((Place)command).setPosition(guiBoard
                .getSpot(jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION).getInt(Messages.JSON_TYPE_LINE),
                        jsonObject.getJSONObject(Messages.JSON_TYPE_POSITION).getInt(Messages.JSON_TYPE_SPOT)));

        ((Place)command).setCardID(jsonObject.getInt(Messages.JSON_TYPE_CARD_ID));
      }

    } catch (JSONException e) {
      e.printStackTrace();
    }
    return command;
  }

}
