package Server.Game.ModelClasses.Commands;

import Server.Game.ModelClasses.Commands.CardMovement.Discard;
import Server.Game.ModelClasses.Commands.CardMovement.Draw;
import Server.Game.ModelClasses.Commands.CardMovement.DrawTypeFromDiscard;
import Server.Game.ModelClasses.Commands.GUI.*;
import Server.Game.ModelClasses.Commands.GUI.OnPosition.*;
import Server.Game.ModelClasses.Commands.GUI.OnPosition.ToPosition.GUIAdvanceCreature;
import Server.Game.ModelClasses.Commands.GUI.OnPosition.ToPosition.GUIRetreatCreature;
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
    CHANGE_MP("Change_MP"),
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
    ABANDON("Abandon"),

    /* Gui commands */
    GUI_HIT("GUI_Hit"),
    GUI_HEAL("GUI_Heal"),
    GUI_KILL("GUI_Kill"),
    GUI_CHANGE_AP("GUI_Change_AP"),
    GUI_DRAW("GUI_Draw"),
    GUI_DISCARD("GUI_Discard"),
    GUI_CREATE_CREATURE("GUI_Create_creature"),
    GUI_CREATE_TRAP("GUI_Create_trap"),
    GUI_ADVANCE_CREATURE("GUI_Advance_creature"),
    GUI_RETREAT_CREATURE("GUI_Retreat_creature"),
    GUI_KNOCK_OUT("GUI_Knock_Out");

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

            /* Players actions */
            case GUI_HIT:
                return new GUIHit();
            case GUI_DRAW:
                return new GUIDraw();
            case GUI_HEAL:
                return new GUIHeal();
            case GUI_KILL:
                return new GUIKill();
            case GUI_DISCARD:
                return new GUIDiscard();
            case GUI_CHANGE_AP:
                return new GUIChangeAP();
            case GUI_KNOCK_OUT:
                return new GUIKnockOut();
            case GUI_CREATE_TRAP:
                return new GUICreateTrap();
            case GUI_CREATE_CREATURE:
                return new GUICreateCreature();
            case GUI_ADVANCE_CREATURE:
                return new GUIAdvanceCreature();
            case GUI_RETREAT_CREATURE:
                return new GUIRetreatCreature();
            default: return null;
        }

    }
}
