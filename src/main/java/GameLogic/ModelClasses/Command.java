package GameLogic.ModelClasses;

public interface Command {
    void execute();
    void undo();
}
