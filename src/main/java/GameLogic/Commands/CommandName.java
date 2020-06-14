package GameLogic.Commands;

import GameLogic.Commands.GuiCommands.*;
import GameLogic.Commands.CardMovement.Discard;
import GameLogic.Commands.CardMovement.Draw;
import GameLogic.Commands.CardMovement.DrawTypeFromDiscard;
import GameLogic.Commands.PlayersAction.Abandon;
import GameLogic.Commands.PlayersAction.EndTurn;
import GameLogic.Commands.PlayersAction.PlayCard;
import GameLogic.Commands.PlayersAction.Undo;
import GameLogic.Commands.OnLiveReceptors.Heal;
import GameLogic.Commands.OnLiveReceptors.Hit;
import GameLogic.Commands.OnLiveReceptors.Kill;
import GameLogic.Commands.OnLiveReceptors.OnCreature.*;

public enum CommandName {
    /* GUI */
    MOVE("Move"),
    CHANGE_POINTS("Change_points"),
    PLACE("Place"),
    KNOCK_OUT_CREATURE("Knock_out_creature"),
    ADD_CARD("Add_card"),
    REMOVE_CARD("Remove_card"),
    END_GAME("End_game"),
    END_PLAYER_TURN("End_player_turn"),

    /* Needs one position */
    HIT("Hit"),
    HEAL("Heal"),
    KILL("Kill"),
    CHANGE_AP("Change_AP"),
    CHANGE_MP("Change_MP"),
    CREATE_CREATURE("Create_creature"),
    CREATE_TRAP("Create_trap"),
    KNOCK_OUT("Knock_Out"),

    /* Needs two positions */
    ADVANCE_CREATURE("Advance_creature"),
    RETREAT_CREATURE("Retreat_creature"),

    /* Needs a card */
    DRAW_TYPE_FROM_DISCARD("Draw_type_from_discard"),
    DRAW("Draw"),
    DISCARD("Discard"),
    PLAY_CARD("Play_card"),

    /* Player commands */
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
            case HIT:
                return new Hit();
            case HEAL:
                return new Heal();
            case CHANGE_AP:
                return new ChangeAttackPoints();
            case CHANGE_MP:
                return new ChangeMovementsPoints();
            case DRAW_TYPE_FROM_DISCARD:
                return new DrawTypeFromDiscard();
            case CREATE_TRAP:
                return new CreateTrap();
            case DISCARD:
                return new Discard();
            case DRAW:
                return new Draw();
            case CREATE_CREATURE:
                return new Create();
            case KILL:
                return new Kill();
            case KNOCK_OUT:
                return new KnockOut();
            case ADVANCE_CREATURE:
                return new Advance();
            case RETREAT_CREATURE:
                return new Retreat();

            /* Players actions */
            case ABANDON :
                return new Abandon();
            case END_TURN:
                return new EndTurn();
            case PLAY_CARD:
                return new PlayCard();
            case UNDO:
                return new Undo();

            /* GUI */
            case MOVE:
                return new Move();
            case PLACE:
                return new Place();
            case KNOCK_OUT_CREATURE:
                return new KnockOutCreature();
            case CHANGE_POINTS:
                return new ChangePoints();
            case ADD_CARD:
                return new AddCard();
            case REMOVE_CARD:
                return new RemoveCard();
            case END_GAME:
                return new EndGame();
            case END_PLAYER_TURN:
                return new EndPlayerTurn();

            default: return null;
        }

    }
}
