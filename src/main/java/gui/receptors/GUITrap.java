package gui.receptors;

public class GUITrap extends GUIReceptor {
    private String description;

    public GUITrap(String description) {
        super("", "");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
