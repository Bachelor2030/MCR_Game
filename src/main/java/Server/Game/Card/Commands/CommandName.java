package Server.Game.Card.Commands;

import Server.Game.Card.Commands.CardMovement.Discard;
import Server.Game.Card.Commands.CardMovement.Draw;
import Server.Game.Card.Commands.CardMovement.GetCardFromDiscard;

public enum CommandName {

    HIT("Hit"), // Problem
    KILL("Kill"),
    DRAW_TYPE_FROM_DISCARD("Draw from discard"),
    DRAW("Draw"),
    DISCARD("Discard"),
    CREATE_CREATURE("Create creature"),
    CREATE_TRAP("Create trap"),
    ADVANCE_CREATURE("Advance"),
    RETREAT_CREATURE("Retreat"),
    KNOCK_OUT("KnockOut");

    private final String name;

    CommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandName getCommandName(String type) {
        if(type.equals(HIT.name)) {
            return HIT;
        } else if(type.equals(KILL.name)) {
            return KILL;
        }else if(type.equals(DRAW.name)) {
            return DRAW;
        } else if(type.equals(DRAW_TYPE_FROM_DISCARD.name)) {
            return DRAW_TYPE_FROM_DISCARD;
        } else if(type.equals(DISCARD.name)) {
            return DISCARD;
        } else if(type.equals(CREATE_CREATURE.name)) {
            return CREATE_CREATURE;
        } else if(type.equals(CREATE_TRAP.name)) {
            return CREATE_TRAP;
        } else if(type.equals(ADVANCE_CREATURE.name)) {
            return ADVANCE_CREATURE;
        } else if(type.equals(RETREAT_CREATURE.name)) {
            return RETREAT_CREATURE;
        } else if(type.equals(KNOCK_OUT.name)) {
            return KNOCK_OUT;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public ConcreteCommand getCommand() {
        switch (this) {
            case HIT: return new HitLiveReceptor();
            case DRAW_TYPE_FROM_DISCARD: return new GetCardFromDiscard();
            case CREATE_TRAP: return new CreateTrap();
            case DISCARD: return new Discard();
            case DRAW: return new Draw();
            case CREATE_CREATURE: return new CreateCreature();
            case KILL: return new KillLiveReceptor();
            case KNOCK_OUT: return new KnockOutCreature();
            case ADVANCE_CREATURE: return new AdvanceCreature();
            case RETREAT_CREATURE: return new RetreatCreature();
            default: return null;
        }

    }
}
