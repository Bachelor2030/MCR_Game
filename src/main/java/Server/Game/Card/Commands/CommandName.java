package Server.Game.Card.Commands;

import Common.Receptors.PlayerCommands.*;
import Server.Game.Card.Commands.OnLiveReceptors.*;
import Server.Game.Card.Commands.OnLiveReceptors.OnCreature.*;
import Server.Game.Card.Commands.CardMovement.Discard;
import Server.Game.Card.Commands.CardMovement.Draw;
import Server.Game.Card.Commands.CardMovement.DrawTypeFromDiscard;
import Server.Game.ModelClasses.ConcreteCommand;

public enum CommandName {
    HIT("Hit"),
    HEAL("Heal"),
    KILL("Kill"),
    CHANGE_AP("Change attack points"),
    DRAW_TYPE_FROM_DISCARD("Draw from discard"),
    DRAW("Draw"),
    DISCARD("Discard"),
    CREATE_CREATURE("Create creature"),
    CREATE_TRAP("Create trap"),
    ADVANCE_CREATURE("Advance"),
    RETREAT_CREATURE("Retreat"),
    KNOCK_OUT("KnockOut"),
    /* Player commands */
    PLAY_CARD("Card"),
    CHOOSE_POSITION("Position"),
    CHOOSE_CREATURE("Creature"),
    UNDO("Undo"),
    END_TURN("Turn"),
    ABANDON("Quit");

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
        } else if(type.equals(HEAL.name)) {
            return HEAL;
        } else if(type.equals(KILL.name)) {
            return KILL;
        } else if(type.equals(CHANGE_AP.name)) {
            return CHANGE_AP;
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
        } else if(type.equals(ADVANCE_CREATURE.name)) {
            return ADVANCE_CREATURE;
        } else if(type.equals(RETREAT_CREATURE.name)) {
            return RETREAT_CREATURE;
        } else if(type.equals(KNOCK_OUT.name)) {
            return KNOCK_OUT;
        } else if (type.equals(PLAY_CARD.name)) {
            return PLAY_CARD;
        } else if (type.equals(CHOOSE_POSITION.name)) {
            return CHOOSE_POSITION;
        } else if (type.equals(CHOOSE_CREATURE.name)) {
            return CHOOSE_CREATURE;
        } else if (type.equals(UNDO.name)) {
            return UNDO;
        } else if (type.equals(END_TURN.name)) {
            return END_TURN;
        } else if (type.equals(ABANDON.name)) {
            return ABANDON;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public ConcreteCommand getCommand() {
        switch (this) {
            case HIT: return new Hit();
            case HEAL: return new Heal();
            case CHANGE_AP: return new ChangeAttackPoints();
            case DRAW_TYPE_FROM_DISCARD: return new DrawTypeFromDiscard();
            case CREATE_TRAP: return new CreateTrap();
            case DISCARD: return new Discard();
            case DRAW: return new Draw();
            case CREATE_CREATURE: return new Create();
            case KILL: return new Kill();
            case KNOCK_OUT: return new KnockOut();
            case ADVANCE_CREATURE: return new Advance();
            case RETREAT_CREATURE: return new Retreat();
            case ABANDON : return new Abandon();
            case END_TURN: return new EndTurn();
            case PLAY_CARD: return new PlayCard();
            case CHOOSE_CREATURE: return new ChooseCreature();
            case CHOOSE_POSITION: return new ChoosePosition();
            default: return null;
        }

    }
}
