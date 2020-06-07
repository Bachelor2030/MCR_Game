package Server.Game.Card.Commands;

import Server.Game.Card.Commands.CardMovement.Discard;
import Server.Game.Card.Commands.CardMovement.Draw;
import Server.Game.Card.Commands.CardMovement.GetCardFromDiscard;
import Server.Game.ModelClasses.Command;

public enum CommandName {
    HIT("Hit", new HitLiveReceptor()),
    DRAW_TYPE_FROM_DISCARD("Draw from discard", new GetCardFromDiscard()),
    DRAW("Draw", new Draw()),
    DISCARD("Discard", new Discard()),
    CREATE_CREATURE("Create creature", new CreateCreature()),
    CREATE_TRAP("Create trap", new CreateTrap()),
    MOVE_CREATURE("Move creature", new MoveCreature());

    private String name;
    private Command command;

    CommandName(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public static CommandName getCommandName(String type) {
        if(type.equals(HIT.name)) {
            return HIT;
        } else if(type.equals(DRAW.name)) {
            return DRAW;
        } else if(type.equals(DRAW_TYPE_FROM_DISCARD.name)) {
            return DRAW_TYPE_FROM_DISCARD;
        } else if(type.equals(DISCARD.name)) {
            return DISCARD;
        } else if(type.equals(CREATE_CREATURE.name)) {
            return CREATE_CREATURE;
        } else if(type.equals(CREATE_TRAP.name)) {
            return CREATE_TRAP;
        } else if(type.equals(MOVE_CREATURE.name)) {
            return MOVE_CREATURE;
        }
        return null;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return name;
    }
}
