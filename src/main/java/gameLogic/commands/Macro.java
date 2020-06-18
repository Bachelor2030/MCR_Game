package gameLogic.commands;

import gameLogic.receptors.Receptor;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Macro implements Command {
  private Receptor receptor;
  private ArrayList<ConcreteCommand> commands;

  public Macro(ArrayList<ConcreteCommand> commands) {
    this.commands = commands;
  }

  public ArrayList<ConcreteCommand> getCommands() {
    return commands;
  }

  public void addCommand(ConcreteCommand concreteCommand) {
    commands.add(concreteCommand);
  }

  public ArrayList<Create> getCreateCreature() {
    ArrayList<Create> concreteCommands = new ArrayList<>();
    for (ConcreteCommand concreteCommand : commands) {
      if (concreteCommand.getName() == CommandName.CREATE_CREATURE) {
        concreteCommands.add((Create) concreteCommand);
      }
    }
    return concreteCommands;
  }

  public ArrayList<CreateTrap> getCreateTrap() {
    ArrayList<CreateTrap> concreteCommands = new ArrayList<>();
    for (ConcreteCommand concreteCommand : commands) {
      if (concreteCommand.getName() == CommandName.CREATE_TRAP) {
        concreteCommands.add((CreateTrap) concreteCommand);
      }
    }
    return concreteCommands;
  }

  public JSONObject toJson() {
    JSONObject macro = new JSONObject();

    try {
      ConcreteCommand command = commands.get(commands.size()-1);

      // Add last command name
      macro.put(Messages.JSON_TYPE_COMMAND, command.name);

      // TODO ajouter ce qu'il faut

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return macro;
  }

  public Receptor getReceptor() {
    return receptor;
  }

  @Override
  public void execute(Receptor receptor, ServerSharedState serverSharedState) {
    this.receptor = receptor;
    for (Command command : commands) {
      command.execute(receptor, serverSharedState);
    }
  }

  @Override
  public void undo(Receptor receptor, ServerSharedState serverSharedState) {
    this.receptor = receptor;
    for (Command command : commands) {
      command.undo(receptor, serverSharedState);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (ConcreteCommand c : commands) {
      sb.append(c.toString() + " ");
    }
    sb.append("]");
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Macro macro = (Macro) o;
    return Objects.equals(commands, macro.commands);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commands);
  }
}
