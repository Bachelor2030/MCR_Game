package Common.Receptors;

public enum Action {
    TRAPPED("Trapped"),
    HIT("Hit");

    private String name;

    Action(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Action getAction(String name) {

        if (name.equals(TRAPPED.name)) {
            return TRAPPED;
        } else if(name.equals(HIT.name)) {
            return HIT;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
