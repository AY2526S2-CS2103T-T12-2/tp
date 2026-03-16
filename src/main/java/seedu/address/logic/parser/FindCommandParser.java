package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> nameKeywords = Arrays.stream(keywords)
                .filter(keyword -> keyword.length() <= Name.MAX_LENGTH).toList();

        List<Predicate<Person>> predicates = new ArrayList<Predicate<Person>>();
        predicates.add(new NameContainsKeywordsPredicate(nameKeywords));
        predicates.add(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));

        Predicate<Person> anyPredicate = predicates.stream().reduce(Predicate::or).orElse(person -> false);

        return new FindCommand(anyPredicate);
    }

}
