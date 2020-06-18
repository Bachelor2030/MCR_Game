package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.commands.onLiveReceptors.OnLiveReceptor;
import gameLogic.receptors.Creature;
import gameLogic.receptors.LiveReceptor;
import network.states.ServerSharedState;

public abstract class OnCreature extends OnLiveReceptor {
  public OnCreature(CommandName name) {
    super(name);
  }

  public Creature getCreatures() {
    return (Creature) receptor;
  }

  public abstract void execute(Creature creature, ServerSharedState serverSharedState);

  public abstract void undo(Creature creature, ServerSharedState serverSharedState);

  @Override
  public void execute(LiveReceptor receptor, ServerSharedState serverSharedState) {
    this.receptor = receptor;
    execute((Creature) receptor, serverSharedState);
  }

  @Override
  public void undo(LiveReceptor receptor, ServerSharedState serverSharedState) {
    this.receptor = receptor;
    undo((Creature) receptor, serverSharedState);
  }
}
