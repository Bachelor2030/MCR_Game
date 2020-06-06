package Server.Game.Card;

public enum CardType {
    SPELL("Spell"),
    TRAP("Receptors.Trap"),
    CREATURE("Creature");

    private String name;

    CardType(String name) {
        this.name = name;
    }

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

