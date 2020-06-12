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

    public void setPlayer(Player player) {
        this.player = player;
    }

    protected void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toJson() {
        return "{\"type\" : \"Command\", \"name\"" + name + "\", \"player\" : " + player.getName() + ", \"cardID\" : " + card.getID() + "}";
    }
}
