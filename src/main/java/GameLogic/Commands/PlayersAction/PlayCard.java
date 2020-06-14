package GameLogic.Commands.PlayersAction;

import GameLogic.Invocator.Card.Card;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;
import GameLogic.Receptors.Receptor;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayCard extends PlayersAction {
    private Card cardToPlay;

    public PlayCard() {
        super(CommandName.PLAY_CARD);
    }

    public void setCardToPlay(Card cardToPlay) {
        this.cardToPlay = cardToPlay;
    }

    @Override
    public JSONObject toJson() {
        JSONObject playCard = super.toJson();
        try {
            playCard.put("cardid", cardToPlay.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playCard;
    }

    @Override
    public void execute(Player player) {
        player.playCard(cardToPlay);
    }

    @Override
    public void undo(Player player) {
        player.undoCard(cardToPlay);
    }
}
