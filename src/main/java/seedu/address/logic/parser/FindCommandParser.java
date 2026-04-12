package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_SHORT;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.ContactContainsKeywordsPredicate;
import seedu.address.model.person.Location;
import seedu.address.model.person.LocationContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProductsContainKeywordsPredicate;
import seedu.address.model.product.Product;

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

        Prefix[] prefixes = {
            PREFIX_CONTACT, PREFIX_CONTACT_SHORT,
            PREFIX_NAME, PREFIX_NAME_SHORT,
            PREFIX_LOCATION, PREFIX_LOCATION_SHORT,
            PREFIX_PRODUCT, PREFIX_PRODUCT_SHORT,
        };

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = getAndCheckKeywords(argMultimap, PREFIX_NAME,
                PREFIX_NAME_SHORT, "Name", Name.MAX_LENGTH);

        List<String> contactKeywords = getAndCheckKeywords(argMultimap, PREFIX_CONTACT,
                PREFIX_CONTACT_SHORT, "Contact", Contact.MAX_EMAIL_LENGTH);

        List<String> locationKeywords = getAndCheckKeywords(argMultimap, PREFIX_LOCATION,
                PREFIX_LOCATION_SHORT, "Location", Location.MAX_LENGTH);

        List<String> productsKeywords = getAndCheckKeywords(argMultimap, PREFIX_PRODUCT,
                PREFIX_PRODUCT_SHORT, "Product", Product.MAX_LENGTH);

        Predicate<Person> fullPred;

        if (nameKeywords.isEmpty() && contactKeywords.isEmpty() && locationKeywords.isEmpty()) {
            fullPred = new ProductsContainKeywordsPredicate(productsKeywords);
        } else {
            Predicate<Person> namePred = new NameContainsKeywordsPredicate(nameKeywords);
            Predicate<Person> contactPred = new ContactContainsKeywordsPredicate(contactKeywords);
            Predicate<Person> locationPred = new LocationContainsKeywordsPredicate(locationKeywords);

            fullPred = namePred.or(contactPred).or(locationPred);

            if (!productsKeywords.isEmpty()) {
                fullPred = fullPred.and(new ProductsContainKeywordsPredicate(productsKeywords));
            }
        }

        return new FindCommand(fullPred);
    }

    private List<String> getAndCheckKeywords(ArgumentMultimap argMultimap, Prefix prefix, Prefix prefixShort,
                                             String className, int maxLength) throws ParseException {

        List<String> keywords = Stream.concat(
                        argMultimap.getAllValues(prefix).stream(),
                        argMultimap.getAllValues(prefixShort).stream())
                .map(kw -> kw.trim()).filter(kw -> !kw.isEmpty()).toList();

        if (keywords.stream().anyMatch(kw -> kw.length() > maxLength)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            String.format("%s keyword length must not exceed %d characters.",
                                    className, maxLength))
            );
        }

        return keywords;
    }
}
