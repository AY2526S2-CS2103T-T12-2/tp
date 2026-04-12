package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds customers. "
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME]..."
            + "[" + PREFIX_PRODUCT + "PRODUCT]..."
            + "[" + PREFIX_LOCATION + "LOCATION]..."
            + "[" + PREFIX_CONTACT + "CONTACT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice "
            + PREFIX_NAME + "bob "
            + PREFIX_CONTACT + "91234567";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final Predicate<Person> predicate;

    /**
     * Constructs a {@code FindCommand} from a {@code predicate}.
     * @param predicate the predicate to filter the person list, cannot be null
     */
    public FindCommand(Predicate<Person> predicate) {
        assert predicate != null : "The predicate should not be null";
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        logger.info(model.getFilteredPersonList().size() + " customers found.");
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_FOUND_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
