package gameLogic.commands.onLiveReceptors;

import gameLogic.commands.CommandName;
import gameLogic.receptors.LiveReceptor;

public class Kill extends OnLiveReceptor {
  private int lifePoints;

  public Kill() {
    super(CommandName.KILL);
  }

  @Override
  public void execute(LiveReceptor liveReceptor) {
    lifePoints = liveReceptor.getLifePoints();
    liveReceptor.loseLifePoints(liveReceptor.getLifePoints());
  }

  @Override
  public void undo(LiveReceptor liveReceptor) {
    liveReceptor.gainLifePoints(lifePoints);
  }
}
