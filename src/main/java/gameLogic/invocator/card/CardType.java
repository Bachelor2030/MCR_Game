package gameLogic.invocator.card;

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
        return CardType.valueOf(type.toUpperCase());
    }

    @Override
    public String toString(){
        return name;
    }
}

