package GameLogic.Commands;

import GameLogic.Game;
import GameLogic.Receptors.Player;
import GameLogic.Board.Spot;
import GameLogic.Receptors.Receptor;
import GameLogic.Receptors.Trap;
import Network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateTrap extends ConcreteCommand {
    private Player player;
    private Spot position;
    private Trap trap;

    public CreateTrap(Trap trap){
        super(CommandName.CREATE_TRAP);
        this.trap = trap;
    }

    public CreateTrap() {
        super(CommandName.CREATE_TRAP);
    }

    public void setPosition(Spot position) {
        this.position = position;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }

    public Trap getTrap() {
        return trap;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void execute(Receptor receptor) {
        trap.setPosition(position);
    }

    @Override
    public void undo(Receptor receptor) {
        trap.setPosition(null);
    }

    @Override
    public Receptor getReceptor() {
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject playTrap = super.toJson();
        try {
            if(player != null)
                playTrap.put(Messages.JSON_TYPE_PLAYER, player.getName());
            playTrap.put("trap", trap.toJson());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playTrap;
    }
}
