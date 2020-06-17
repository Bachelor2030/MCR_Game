package gameLogic.commands;

import gameLogic.commands.cardMovement.Discard;
import gameLogic.commands.cardMovement.Draw;
import gameLogic.commands.cardMovement.DrawTypeFromDiscard;
import gameLogic.commands.guiCommands.*;
import gameLogic.commands.onLiveReceptors.Heal;
import gameLogic.commands.onLiveReceptors.Hit;
import gameLogic.commands.onLiveReceptors.Kill;
import gameLogic.commands.onLiveReceptors.onCreature.*;
import gameLogic.commands.playersAction.Abandon;
import gameLogic.commands.playersAction.EndTurn;
import gameLogic.commands.playersAction.PlayCard;
import gameLogic.commands.playersAction.Undo;

public enum CommandName {
  /* gui */
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
    try {
      return CommandName.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("The given command does not exist...");
      return null;
    }
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
      case ABANDON:
        return new Abandon();
      case END_TURN:
        return new EndTurn();
      case PLAY_CARD:
        return new PlayCard();
      case UNDO:
        return new Undo();

        /* gui */
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

      default:
        return null;
    }
  }

  public boolean isPlayerAction() {
    return (this == ABANDON || this == END_TURN || this == PLAY_CARD || this == UNDO);
  }
}
