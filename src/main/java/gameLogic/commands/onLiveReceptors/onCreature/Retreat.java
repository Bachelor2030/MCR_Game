package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Creature;
import network.states.ServerSharedState;

public class Retreat extends MoveCreature {
  public Retreat() {
    super(CommandName.RETREAT_CREATURE);
  }

  @Override
  public void execute(Creature creature, ServerSharedState serverSharedState) {
    creature.retreat(creature.getSteps(), serverSharedState);
  }

  @Override
  public void undo(Creature creature, ServerSharedState serverSharedState) {
    creature.advance(serverSharedState);
  }
}
