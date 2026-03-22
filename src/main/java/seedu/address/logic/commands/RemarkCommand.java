package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 r/ Likes to swim.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // For the tutorial, throwing an exception is enough to prove the logic is hooked up
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
