package GameLogic.Commands.CardMovement;

import GameLogic.Invocator.Card.Card;
import GameLogic.Commands.CommandName;
import GameLogic.Commands.ConcreteCommand;
import GameLogic.Receptors.Player;
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
