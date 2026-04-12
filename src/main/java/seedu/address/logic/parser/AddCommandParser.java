package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern PREFIX_PATTERN = Pattern.compile("(?<=^|\\s)([A-Za-z]+/)");
    private static final Set<String> VALID_PREFIXES = Set.of(
            PREFIX_NAME.getPrefix(),
            PREFIX_PRODUCTS.getPrefix(),
            PREFIX_LOCATION.getPrefix(),
            PREFIX_DEADLINE.getPrefix(),
            PREFIX_CONTACT.getPrefix()
    );

    /**
     * Returns an {@code AddCommand} parsed from the given {@code String} of arguments.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        String normalizedArgs = ParserUtil.normalizeShortPrefixes(args);
        verifyNoUnknownPrefixes(normalizedArgs);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(normalizedArgs, PREFIX_NAME, PREFIX_PRODUCTS,
                PREFIX_LOCATION,
                PREFIX_DEADLINE, PREFIX_CONTACT);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                || argMultimap.getValue(PREFIX_NAME).get().trim().isEmpty()) {
            throw new ParseException(Name.MESSAGE_NAME_REQUIRED);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PRODUCTS, PREFIX_LOCATION, PREFIX_DEADLINE,
                PREFIX_CONTACT);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Products products = Products.empty(); // defaults when products are missing
        if (argMultimap.getValue(PREFIX_PRODUCTS).isPresent()) {
            products = ParserUtil.parseOptionalProducts(argMultimap.getValue(PREFIX_PRODUCTS).get());
        }

        Location location = Location.empty(); // defaults when location is missing
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            location = ParserUtil.parseOptionalLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        }

        Deadline deadline = Deadline.empty(); // defaults to no deadline when missing
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            deadline = ParserUtil.parseOptionalDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        }

        Contact contact = Contact.empty(); // defaults when contact is missing
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            contact = ParserUtil.parseOptionalContact(argMultimap.getValue(PREFIX_CONTACT).get());
        }

        Person person = new Person(name, products, location, deadline, contact); // creates new customer object

        return new AddCommand(person);
    }

    private void verifyNoUnknownPrefixes(String args) throws ParseException {
        Matcher matcher = PREFIX_PATTERN.matcher(args);
        while (matcher.find()) {
            String prefix = matcher.group(1);
            if (!VALID_PREFIXES.contains(prefix)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        }
    }
}
