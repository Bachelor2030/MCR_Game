package Server.Game.Card.Commands;

import Server.Game.Card.Commands.CardMovement.Discard;
import Server.Game.Card.Commands.CardMovement.Draw;
import Server.Game.Card.Commands.CardMovement.GetCardFromDiscard;
import Server.Game.ModelClasses.Command;

public enum CommandName {
    HIT("Hit"),
    DRAW_TYPE_FROM_DISCARD("Draw from discard"),
    DRAW("Draw"),
    DISCARD("Discard"),
    CREATE_CREATURE("Create creature"),
    CREATE_TRAP("Create trap"),
    MOVE_CREATURE("Move creature");

    private String name;

    CommandName(String name) {
        this.name = name;
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

    public static Command getCommand(CommandName commandName) {
        switch (commandName) {
            case HIT:
                return new HitLiveReceptor();
            case DISCARD:
                return new Discard();
            case DRAW:
                return new Draw();
            case DRAW_TYPE_FROM_DISCARD:
                return new GetCardFromDiscard();
            case CREATE_CREATURE:
                return new CreateCreature();
            case CREATE_TRAP:
                return new CreateTrap();
            case MOVE_CREATURE:
                return new MoveCreature();
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
