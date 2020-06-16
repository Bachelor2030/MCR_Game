package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.Messages;
import gameLogic.invocator.card.Card;
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
            playCard.put(Messages.JSON_TYPE_CARD_ID, cardToPlay.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playCard;
    }

    @Override
    public void execute(Player player) {
        if(!player.playCard(cardToPlay)) {
            player.removeLastMove(this);
        }
        player.addLastMoves(cardToPlay.getCommand().getCommands());
    }

    @Override
    public void undo(Player player) {
        player.undoCard(cardToPlay);
    }
}
