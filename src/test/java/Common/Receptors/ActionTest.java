package Common.Receptors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    public void ActionShouldHaveCorrectName() {
        assertEquals("Trapped", Action.TRAPPED.getName());
    }

    @Test
    public void ActionShouldHaveCorrectStringFormat() {
        assertEquals("Trapped", Action.TRAPPED.toString());
    }

    @Test
    public void ActionNameShouldReturnCorrectAction() {
        assertEquals(Action.TRAPPED, Action.getAction(Action.TRAPPED.getName()));
    }
}