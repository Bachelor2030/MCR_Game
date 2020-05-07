package Command;

import Player.Creature.Creature;
import Player.Player;

public class OnBoth extends ConcreteCommand {
    private Creature[] creatures;
    private Player[] players;

    @Override
    public void execute() {
        for (Player player : players) {
            player.action();
        }
        for (Creature creature : creatures) {
            creature.action();
        }
    }

    @Override
    public void undo() {

    }
}
