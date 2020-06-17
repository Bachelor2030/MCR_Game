package gameLogic.commands;

import gameLogic.receptors.Receptor;

public interface Command {
  void execute(Receptor receptor);

  void undo(Receptor receptor);
}
