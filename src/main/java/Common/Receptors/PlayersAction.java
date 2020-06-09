package Common.Receptors;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.Command;
import Server.Game.ModelClasses.ConcreteCommand;

import java.util.Scanner;

public abstract class PlayersAction extends ConcreteCommand {
    // TODO : utiliser un servant worker plutôt qu'un player --> demander une action au bon client
    private Player player;

    public PlayersAction(CommandName name) {
        super(name);
    }

    public PlayersAction(Player player, CommandName name) {
        super(name);
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static PlayersAction askAction(Player player) {
        Scanner in = new Scanner(System.in);
        System.out.println("What do you want to do ?");
        String action = in.nextLine();

        // TODO
        //return PlayerActionName.getPlayerAction(action).getPlayerAction();
        return null;
    }
}
