package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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

    private final Index targetIndex;
    private final String targetName;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    public DeleteCommand(String targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CUSTOMER_LIST);
        }

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(personToDelete)));
        }

        List<Person> matches = lastShownList.stream()
                .filter(p -> p.getName().getFullName().equalsIgnoreCase(targetName))
                .toList();

        if (matches.isEmpty()) {
            throw new CommandException("No person found with name: " + targetName);
        }

        if (matches.size() > 1) {
            throw new CommandException("Multiple persons found with name: " + targetName);
        }

        Person personToDelete = matches.get(0);
        model.deletePerson(personToDelete);

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
        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
