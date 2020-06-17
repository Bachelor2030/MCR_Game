package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.commands.CommandName;
import gameLogic.commands.onLiveReceptors.OnLiveReceptor;
import gameLogic.receptors.Creature;
import gameLogic.receptors.LiveReceptor;

public abstract class OnCreature extends OnLiveReceptor {
  public OnCreature(CommandName name) {
    super(name);
  }

  public Creature getCreatures() {
    return (Creature) receptor;
  }

  public abstract void execute(Creature creature);

  public abstract void undo(Creature creature);

  @Override
  public void execute(LiveReceptor receptor) {
    this.receptor = (Creature) receptor;
    execute((Creature) receptor);
  }

  @Override
  public void undo(LiveReceptor receptor) {
    this.receptor = (Creature) receptor;
    undo((Creature) receptor);
  }
}
