package gameLogic.commands.onLiveReceptors;

import gameLogic.commands.CommandName;
import gameLogic.receptors.LiveReceptor;
import network.states.ServerSharedState;

public class Kill extends OnLiveReceptor {
  private int lifePoints;

  public Kill() {
    super(CommandName.KILL);
  }

  @Override
  public void execute(LiveReceptor liveReceptor, ServerSharedState serverSharedState) {
    lifePoints = liveReceptor.getLifePoints();
    liveReceptor.loseLifePoints(liveReceptor.getLifePoints());
  }

  @Override
  public void undo(LiveReceptor liveReceptor, ServerSharedState serverSharedState) {
    liveReceptor.gainLifePoints(lifePoints);
  }
}
