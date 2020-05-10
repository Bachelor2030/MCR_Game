package Command;

import Player.Creature.Creature;
import Player.Player;

public class OnBoth extends Macro {
    private Creature[] creatures;
    private Player[] players;

    @Override
    public void execute() {
        for (Player player : players) {
            // TODO : do the correct action
        }
        for (Creature creature : creatures) {
            // TODO : do the correct action
        }
    }

    @Override
    public void undo() {

    }
}
