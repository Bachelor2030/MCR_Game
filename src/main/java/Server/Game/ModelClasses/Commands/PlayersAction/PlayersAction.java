package Server.Game.ModelClasses.Commands.PlayersAction;

import Common.Receptors.Player;
import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;

import java.util.Scanner;

public abstract class PlayersAction extends ConcreteCommand {
    // TODO : utiliser un servant worker plutôt qu'un player --> demander une action au bon client
    protected Player player;

    public PlayersAction(CommandName name) {
        super(name);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static PlayersAction askAction(Player player) {

        Scanner in = new Scanner(System.in);
        String action;

        // Todo changer les system.in en lecture de JSON
        do {
            System.out.println(player.getName() + " what do you want to do ?");
            action = in.nextLine();
        } while (CommandName.getCommandName(action) == null);
        PlayersAction playersAction = (PlayersAction) CommandName.getCommandName(action).getCommand();
        playersAction.setPlayer(player);

        if (playersAction.getName() == CommandName.PLAY_CARD) {
            System.out.println("What card do you want to play ?");
            //action = in.nextLine();
            // TODO change the following line to the chosen card
            ((PlayCard)playersAction).setCardToPlay(new Card(1, "1", CardType.SPELL, 1));
        }

        return playersAction;
    }

    @Override
    public String toJson() {
        return "{\"type\" : \"Command\", \"name\" : \"" + name + "\", \"player\" : \"" + player.getName() + "\"}";
    }
}
