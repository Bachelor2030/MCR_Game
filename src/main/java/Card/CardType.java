package Card;

public enum CardType {
    SPELL("Spell"),
    TRAP("Trap"),
    CREATURE("Creature");

    private String name;

    private CardType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}

