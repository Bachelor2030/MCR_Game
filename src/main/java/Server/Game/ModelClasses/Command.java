package Server.Game.ModelClasses;

public interface Command {
    void execute();
    void undo();
}
