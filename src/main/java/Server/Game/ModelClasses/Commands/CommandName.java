package Server.Game.ModelClasses.Commands;

import Server.Game.ModelClasses.Commands.CardMovement.Discard;
import Server.Game.ModelClasses.Commands.CardMovement.Draw;
import Server.Game.ModelClasses.Commands.CardMovement.DrawTypeFromDiscard;
import Server.Game.ModelClasses.Commands.PlayersAction.Abandon;
import Server.Game.ModelClasses.Commands.PlayersAction.EndTurn;
import Server.Game.ModelClasses.Commands.PlayersAction.PlayCard;
import Server.Game.ModelClasses.Commands.PlayersAction.Undo;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.Heal;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.Hit;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.Kill;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.*;
import Server.Game.ModelClasses.ConcreteCommand;

public enum CommandName {
    HIT("Hit"),
    HEAL("Heal"),
    KILL("Kill"),
    CHANGE_AP("Change_AP"),
    DRAW_TYPE_FROM_DISCARD("Draw_type_from_discard"),
    DRAW("Draw"),
    DISCARD("Discard"),
    CREATE_CREATURE("Create_creature"),
    CREATE_TRAP("Create_trap"),
    ADVANCE_CREATURE("Advance_creature"),
    RETREAT_CREATURE("Retreat_creature"),
    KNOCK_OUT("Knock_Out"),
    /* Player commands */
    PLAY_CARD("Play_card"),
    UNDO("Undo"),
    END_TURN("End_turn"),
    ABANDON("Abandon");

    private final String name;

    CommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandName getCommandName(String type) {
        return CommandName.valueOf(type.toUpperCase());
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
            case UNDO: return new Undo();
            default: return null;
        }

    }
}
