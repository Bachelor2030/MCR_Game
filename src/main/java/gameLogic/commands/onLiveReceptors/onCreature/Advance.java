package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Creature;

public class Advance extends MoveCreature {
  public Advance() {
    super(CommandName.ADVANCE_CREATURE);
  }

  @Override
  public void execute(Creature creature) {
    from = creature.getPosition();
    creature.advance();
    from = creature.getPosition();
  }

  @Override
  public void undo(Creature creature) {
    from = creature.getPosition();
    creature.retreat(creature.getSteps());
    from = creature.getPosition();
  }
}
