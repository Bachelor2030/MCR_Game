package gameLogic.board;

import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.receptors.Receptor;
import gameLogic.receptors.Trap;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

public class Spot extends Receptor {
  private int spotNumber;
  private Line line;
  private Receptor occupant;

  public Spot(Line line, int spotNumber) {
    super();
    this.spotNumber = spotNumber;
    this.line = line;
  }

  public boolean isEmpty() {
    return occupant == null || occupant.getClass() == Trap.class;
  }

  public boolean isTrapped() {
    return occupant != null && occupant.getClass() == Trap.class;
  }

  public Receptor getOccupant() {
    return occupant;
  }

  /**
   * Permet de set l'éventuelle créature présente sur la case.
   *
   * @param occupant : la créature
   */
  public void setOccupant(Receptor occupant) {
    this.occupant = occupant;
  }

  /** Modélise le départ d'une créature de la case. */
  public void leave() {
    this.occupant = null;
  }

  public JSONObject toJson() {
    JSONObject position = new JSONObject();
    try {
      position.put(Messages.JSON_TYPE_LINE, getLineNumber());
      position.put(Messages.JSON_TYPE_SPOT, spotNumber);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return position;
  }

  public int getLineNumber() {
    return line.getLineNumber();
  }

  public int getSpotNumber() {
    return spotNumber;
  }

  public Spot next(int ownerId, ServerSharedState serverSharedState) {
    int change = (ownerId == serverSharedState.getPlayingFirstId() ? 1 : -1);

    if (spotNumber + change < line.getNbSpots() && spotNumber + change >= 0) {
      return line.getSpot(spotNumber + change);
    }
    return null;
  }

  public Spot previous(int ownerId, ServerSharedState serverSharedState) {
    int change = (ownerId == serverSharedState.getPlayingFirstId() ? 1 : -1);
    if (spotNumber + change > 0 && spotNumber + change < line.getNbSpots()) return line.getSpot(spotNumber + change);
    return null;
  }

  @Override
  public void playTurn(int turn, PlayersAction action) {}

}
