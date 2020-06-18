package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Creature;
import network.states.ServerSharedState;

public class Advance extends MoveCreature {
  public Advance() {
    super(CommandName.ADVANCE_CREATURE);
  }

  @Override
  public void execute(Creature creature, ServerSharedState serverSharedState) {
    from = creature.getPosition();
    creature.advance(serverSharedState);
    from = creature.getPosition();
  }

  @Override
  public void undo(Creature creature, ServerSharedState serverSharedState) {
    from = creature.getPosition();
    creature.retreat(creature.getSteps(), serverSharedState);
    from = creature.getPosition();
  }
}
