package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Creature;
import network.states.ServerSharedState;

public class KnockOut extends OnCreature {
  public KnockOut() {
    super(CommandName.KNOCK_OUT);
  }

  @Override
  public void execute(Creature creature) {
    creature.knockOut();
  }

  @Override
  public void undo(Creature creature) {
    creature.wakeUp();
  }
}
