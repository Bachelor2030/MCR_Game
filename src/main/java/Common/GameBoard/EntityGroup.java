package Common.GameBoard;

import javafx.scene.Group;

import java.util.LinkedList;

/**
 * Cette classe permet de séparer les différentes entités en groupe distinct, pour pouvoir les afficher par la suite
 * sur le GUI.
 * Ex : le groupe des créatures, le groupe des îlots, le groupe des informations user, etc...
 */
public class EntityGroup {
    private LinkedList<Group> creatures;
}
