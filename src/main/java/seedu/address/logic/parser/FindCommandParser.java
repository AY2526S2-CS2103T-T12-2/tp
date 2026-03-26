package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_SHORT;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.ContactContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code FindCommand}
     * and returns a {@code FindCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT,
                PREFIX_CONTACT_SHORT, PREFIX_NAME, PREFIX_NAME_SHORT);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = Stream.concat(
                argMultimap.getAllValues(PREFIX_NAME).stream(),
                argMultimap.getAllValues(PREFIX_NAME_SHORT).stream())
                        .map(kw -> kw.trim()).filter(kw -> !kw.isEmpty()).toList();

        if (nameKeywords.stream().anyMatch(kw -> kw.length() > Name.MAX_LENGTH)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            String.format("Name keyword length must not exceed %d characters.",
                            Name.MAX_LENGTH))
            );
        }

        List<String> contactKeywords = Stream.concat(
                argMultimap.getAllValues(PREFIX_CONTACT).stream(),
                argMultimap.getAllValues(PREFIX_CONTACT_SHORT).stream())
                        .map(kw -> kw.trim()).filter(kw -> !kw.isEmpty()).toList();

        if (contactKeywords.stream().anyMatch(kw -> kw.length() > Contact.MAX_EMAIL_LENGTH)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            String.format("Name keyword length must not exceed %d characters.",
                            Contact.MAX_EMAIL_LENGTH))
            );
        }

        Predicate<Person> namePred = new NameContainsKeywordsPredicate(nameKeywords);
        Predicate<Person> contactPred = new ContactContainsKeywordsPredicate(contactKeywords);

        Predicate<Person> nameOrContactPred = namePred.or(contactPred);

        return new FindCommand(nameOrContactPred);
    }

}
