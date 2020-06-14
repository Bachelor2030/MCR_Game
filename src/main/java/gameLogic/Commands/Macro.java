package gameLogic.Commands;

import gameLogic.Commands.OnLiveReceptors.OnCreature.Create;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Macro implements Command {
    private ArrayList<ConcreteCommand> commands;

    public Macro(ArrayList<ConcreteCommand> commands) {
        this.commands = commands;
    }

    public ArrayList<ConcreteCommand> getCommands() {
        return commands;
    }

    public ArrayList<Create> getCreateCreature() {
        ArrayList<Create> concreteCommands = new ArrayList<>();
        for (ConcreteCommand concreteCommand : commands) {
            if(concreteCommand.getName() == CommandName.CREATE_CREATURE) {
                concreteCommands.add((Create)concreteCommand);
            }
        }
        return concreteCommands;
    }

    public ArrayList<CreateTrap> getCreateTrap() {
        ArrayList<CreateTrap> concreteCommands = new ArrayList<>();
        for (ConcreteCommand concreteCommand : commands) {
            if(concreteCommand.getName() == CommandName.CREATE_TRAP) {
                concreteCommands.add((CreateTrap)concreteCommand);
            }
        }
        return concreteCommands;
    }

    public JSONObject toJson() {
        JSONObject macro = new JSONObject();

        try {
            JSONArray cmds = new JSONArray();
            for (ConcreteCommand command : commands) {
                cmds.put(command.toJson());
            }
            macro.put("commands", cmds);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return macro;
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for(Command command : commands) {
            command.undo();
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
