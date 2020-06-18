package gameLogic.commands;

import gameLogic.receptors.Receptor;
import network.states.ServerSharedState;

public interface Command {
  void execute(Receptor receptor, ServerSharedState serverSharedState);

  void undo(Receptor receptor, ServerSharedState serverSharedState);
}
