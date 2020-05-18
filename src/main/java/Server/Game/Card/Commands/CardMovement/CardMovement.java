package Server.Game.Card.Commands.CardMovement;

import Server.Game.Card.Card;
import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.ConcreteCommand;
import Common.Receptors.Player;

public abstract class CardMovement extends ConcreteCommand {
    protected Player player;
    protected Card card;

    public CardMovement(CommandName name) {
        super(name);
    }

    protected void setCard(Card card) {
        this.card = card;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
