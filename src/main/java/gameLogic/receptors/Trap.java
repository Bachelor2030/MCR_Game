package gameLogic.receptors;

import gameLogic.board.Spot;
import gameLogic.commands.Macro;
import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.invocator.Invocator;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

/** Modelizes a trap in the game */
public class Trap extends Receptor implements Invocator {
  private Macro command; // the effect the trap has on the first creature that lands on it
  private Spot position; // the position at which the trap is

  /**
   * Creates the trap with the given name and effect
   *
   * @param name the name of the trap
   * @param effect teh effect the trap has
   */
  public Trap(String name, Macro effect, ServerSharedState serverSharedState) {
    super("Trap " + name, serverSharedState);
    setCommand(effect);
  }

  /**
   * Permet de récupérer la command du piège courrant
   * @return
   */
  public Macro getCommand() {
    return command;
  }

  /**
   * Déclache le piège sur la victime donnée en argument
   * @param creature
   */
  public void trigger(Creature creature) {
    command.execute(creature);
    if (position != null) position.leave();
    position = null;
  }

  /**
   * Sets the position of the trap which places it on the game board
   *
   * @param position the position of the trap on the board
   */
  public void setPosition(Spot position) {
    if (position != null) position.setOccupant(this);
    this.position = position;
  }

  /**
   * Permet de récupérer la position du piège
   * @return
   */
  public Spot getPosition() {
    return position;
  }

  @Override
  public void playTurn(int turn, PlayersAction action) {}

  @Override
  public void setCommand(Macro command) {
    this.command = command;
  }
}
