package gameLogic.commands.cardMovement;

import gameLogic.commands.CommandName;
import gameLogic.commands.ConcreteCommand;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import gameLogic.receptors.Receptor;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class CardMovement extends ConcreteCommand {
  protected Player player;
  protected Card card;

  public CardMovement(CommandName name) {
    super(name);
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  protected void setCard(Card card) {
    this.card = card;
  }

  public abstract void execute(Player player);

  public abstract void undo(Player player);

  @Override
  public Receptor getReceptor() {
    return player;
  }

  @Override
  public void execute(Receptor receptor) {
    player = (Player) receptor;
    execute((Player) receptor);
  }

  @Override
  public void undo(Receptor receptor) {
    player = (Player) receptor;
    undo((Player) receptor);
  }

  @Override
  public JSONObject toJson() {
    JSONObject cardMovement = super.toJson();
    try {
      cardMovement.put(Messages.JSON_TYPE_CARD_ID, card.getID());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return cardMovement;
  }
}
