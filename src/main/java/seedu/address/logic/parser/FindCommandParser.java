package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContactContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code FindCommand}
     * and returns a {@code FindCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        List<Predicate<Person>> predicates = new ArrayList<Predicate<Person>>();
        predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        predicates.add(new ContactContainsKeywordsPredicate(Arrays.asList(keywords)));

        Predicate<Person> anyPredicate = predicates.stream().reduce(Predicate::or).orElse(person -> false);

        return new FindCommand(anyPredicate);
    }

}
