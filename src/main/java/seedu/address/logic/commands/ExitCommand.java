package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT =
            "Goodbye! Exiting ClientEase. You have %d customer(s) saved.";

    @Override
    public CommandResult execute(Model model) {
        int count = model.getFilteredPersonList().size();
        String message = String.format(MESSAGE_EXIT_ACKNOWLEDGEMENT, count);

        return new CommandResult(message, false, true);
    }

}
