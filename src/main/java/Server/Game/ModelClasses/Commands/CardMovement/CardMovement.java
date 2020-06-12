package Server.Game.ModelClasses.Commands.CardMovement;

import Server.Game.Card.Card;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;
import Common.Receptors.Player;

public abstract class CardMovement extends ConcreteCommand {
    protected Player player;
    protected Card card;

    public CardMovement(CommandName name) {
        super(name);
    }

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

    @Override
    public String toJson() {
        return "{\"type\" : \"Card command " + name + "\", \"player\" : " + player.getName() + ", \"cardID\" : " + card.getID() + "}";
    }
}
