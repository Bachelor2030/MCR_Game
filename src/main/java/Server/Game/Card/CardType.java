package Server.Game.Card;

/**
 * All the types of card that exist in the game
 */
public enum CardType {
    SPELL("Spell"),
    TRAP("Trap"),
    CREATURE("Creature");

    // name of the type
    private String name;

    CardType(String name) {
        this.name = name;
    }

    /**
     * Get the type of a card from the types name
     * @param type
     * @return
     */
    public static CardType getType(String type) {
        if(type.equals(SPELL.name)) {
            return SPELL;
        } else if(type.equals(TRAP.name)) {
            return TRAP;
        } else if(type.equals(CREATURE.name)) {
            return CREATURE;
        }
        return null;
    }

    @Override
    public String toString(){
        return name;
    }
}

