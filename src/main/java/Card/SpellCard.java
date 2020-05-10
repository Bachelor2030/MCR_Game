package Card;

import Player.Player;

public class SpellCard extends Card {
    private Player player;

    public SpellCard(Player player, String name) {
        super("Spell " + name);
        this.player = player;
    }
}
