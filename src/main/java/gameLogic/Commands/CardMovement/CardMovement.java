package gameLogic.Commands.CardMovement;

import gameLogic.Invocator.Card.Card;
import gameLogic.Commands.CommandName;
import gameLogic.Commands.ConcreteCommand;
import gameLogic.Receptors.Player;
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

    @Override
    public JSONObject toJson() {
        JSONObject cardMovement = super.toJson();
        try {
            cardMovement.put("cardid", card.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cardMovement;
    }
}
