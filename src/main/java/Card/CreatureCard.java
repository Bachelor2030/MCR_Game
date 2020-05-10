package Card;

import Player.Creature.Creature;

public class CreatureCard extends Card {
    private Creature creature;

    CreatureCard(Creature creature, String name) {
        super("Creature " + name);
        this.creature = creature;
    }
}
