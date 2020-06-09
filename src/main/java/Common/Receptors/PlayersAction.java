package Common.Receptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;

import java.util.Scanner;

public abstract class PlayersAction extends ConcreteCommand {
    // TODO : utiliser un servant worker plutôt qu'un player --> demander une action au bon client
    protected Player player;

    public PlayersAction(CommandName name) {
        super(name);
    }

    private void setPlayer(Player player) {
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

        if (playersAction.getName() == CommandName.CHOOSE_CREATURE ||
                playersAction.getName() == CommandName.CHOOSE_POSITION ||
                playersAction.getName() == CommandName.PLAY_CARD) {
            System.out.println("What target do you chose for this action ?");
            action = in.nextLine();
        }

        return playersAction;
    }
}
