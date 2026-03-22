package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Greets the user with a friendly message.
 */
public class GreetCommand extends Command {

    public static final String COMMAND_WORD = "greet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a greeting message.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Hello! Welcome to ClientEase.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
