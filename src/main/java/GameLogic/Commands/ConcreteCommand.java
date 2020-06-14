package GameLogic.Commands;

import GameLogic.Receptors.Receptor;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public abstract class ConcreteCommand implements Command {
    protected final CommandName name;

    public ConcreteCommand(CommandName name){
        this.name = name;
    }

    public CommandName getName() {
        return name;
    }

    public abstract Receptor getReceptor();

    public JSONObject toJson() {
        JSONObject command = new JSONObject();
        try {
            command.put("type", "play");
            command.put("name", name.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return command;
    }

    @Override
    public String toString() {
        if (name != null)
            return name.toString();
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcreteCommand that = (ConcreteCommand) o;
        return name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
