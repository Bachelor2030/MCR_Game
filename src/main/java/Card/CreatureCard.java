package Card;

import Player.Creature.Creature;

public class CreatureCard extends Card {
    private Creature creature;

    CreatureCard(Creature creature) {
        this.creature = creature;
    }
}
