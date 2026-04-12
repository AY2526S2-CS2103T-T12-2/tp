package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String COMMAND_ALIAS = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " (or " + COMMAND_ALIAS + "): Deletes the customer identified by the index number or name\n"
            + "Parameters: INDEX (must be a positive integer) OR NAME\n"
            + "Example: " + COMMAND_WORD + " 1, " + COMMAND_ALIAS + " 1, " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "SUCCESS: Deleted Person: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates a DeleteCommand to delete a person using index.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a DeleteCommand to delete a person using name.
     */
    public DeleteCommand(Name targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert lastShownList != null : "Filtered person list should not be null";

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CUSTOMER_LIST);
        }

        if (targetIndex != null) {
            assert targetName == null : "Only one of targetIndex or targetName should be set";

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_INDEX);
            }

            logger.info("Deleting person by index: " + targetIndex);

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);

            logger.info("Deleted person: " + personToDelete);

            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(personToDelete)));
        }

        assert targetName != null : "Target name should not be null when deleting by name";

        logger.info("Attempting to delete person by name: " + targetName);

        List<Person> matches = lastShownList.stream()
                .filter(p -> p.getName().equals(targetName))
                .toList();

        if (matches.isEmpty()) {
            throw new CommandException("No person found with name: " + targetName);
        }

        Person personToDelete = matches.get(0);
        model.deletePerson(personToDelete);

        logger.info("Deleted person: " + personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return Objects.equals(targetIndex, otherDeleteCommand.targetIndex)
                && Objects.equals(targetName, otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();
    }
}
