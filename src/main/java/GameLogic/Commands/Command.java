package GameLogic.Commands;

import GameLogic.Receptors.Receptor;

public interface Command {
    void execute(Receptor receptor);
    void undo(Receptor receptor);
}
