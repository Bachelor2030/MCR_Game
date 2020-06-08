package Server.Game.Card.Commands;

import Server.Game.Card.Commands.CardMovement.Discard;
import Server.Game.Card.Commands.CardMovement.Draw;
import Server.Game.Card.Commands.CardMovement.GetCardFromDiscard;

public enum CommandName {
    HIT("Hit", new HitLiveReceptor()),
    KILL("Kill", new KillLiveReceptor()),
    DRAW_TYPE_FROM_DISCARD("Draw from discard", new GetCardFromDiscard()),
    DRAW("Draw", new Draw()),
    DISCARD("Discard", new Discard()),
    CREATE_CREATURE("Create creature", new CreateCreature()),
    CREATE_TRAP("Create trap", new CreateTrap()),
    ADVANCE_CREATURE("Advance", new AdvanceCreature()),
    RETREAT_CREATURE("Retreat", new RetreatCreature()),
    KNOCK_OUT("KnockOut", new KnockOutCreature());

    private String name;
    private ConcreteCommand command;

    CommandName(String name, ConcreteCommand command) {
        this.name = name;
        this.command = command;
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
        System.out.println(type + " is not an existing command name");
        return null;
    }

    public ConcreteCommand getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return name;
    }
}
