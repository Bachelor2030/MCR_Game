package ModelClasses.Commands.CardMovement;

import Card.Card;
import ModelClasses.Commands.CommandName;
import ModelClasses.Commands.ConcreteCommand;
import ModelClasses.Receptors.Player;

public abstract class CardMovement extends ConcreteCommand {
    protected Player player;
    protected Card card;

    public CardMovement(Player player, CommandName name) {
        super(name);
        this.player = player;
    }

    public CardMovement(Player player, Card card, CommandName name) {
        super(name);
        this.player = player;
        this.card = card;
    }

    protected void setCard(Card card) {
        this.card = card;
    }
}
