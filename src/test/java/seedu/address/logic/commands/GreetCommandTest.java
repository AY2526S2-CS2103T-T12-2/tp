package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class GreetCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_greet_success() {
        assertCommandSuccess(new GreetCommand(), model, GreetCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
