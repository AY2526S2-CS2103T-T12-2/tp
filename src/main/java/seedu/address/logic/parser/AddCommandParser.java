package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns an {@code AddCommand} parsed from the given {@code String} of arguments.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRODUCTS, PREFIX_LOCATION,
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
            products = ParserUtil.parseProducts(argMultimap.getValue(PREFIX_PRODUCTS).get()); // parse products input
        }

        Location location = Location.empty(); // defaults when location is missing
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()); // parse location input
        }

        Deadline deadline = Deadline.empty(); // defaults to no deadline when missing
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get()); // parse deadline input
        }

        Contact contact = Contact.empty(); // defaults when contact is missing
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            contact = ParserUtil.parseContact(argMultimap.getValue(PREFIX_CONTACT).get()); // parse contact input
        }

        Person person = new Person(name, products, location, deadline, contact, Remark.empty()); // creates new customer

        return new AddCommand(person);
    }

}
