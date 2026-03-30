package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Products;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Map<String, String> SHORT_PREFIX_MAPPING = Map.of(
            "n/", CliSyntax.PREFIX_NAME.getPrefix(),
            "p/", CliSyntax.PREFIX_PRODUCTS.getPrefix(),
            "l/", CliSyntax.PREFIX_LOCATION.getPrefix(),
            "d/", CliSyntax.PREFIX_DEADLINE.getPrefix(),
            "c/", CliSyntax.PREFIX_CONTACT.getPrefix()
    );

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Returns a {@code Name} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Returns a {@code Products} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code products} is invalid.
     */
    public static Products parseProducts(String products) throws ParseException {
        requireNonNull(products);
        String trimmedProducts = products.trim();
        if (!Products.isValidProducts(trimmedProducts)) {
            throw new ParseException(Products.MESSAGE_CONSTRAINTS);
        }
        return new Products(trimmedProducts);
    }

    /**
     * Returns a {@code Location} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Returns a {@code Deadline} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Returns a {@code Contact} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code contact} is invalid.
     */
    public static Contact parseContact(String contact) throws ParseException {
        requireNonNull(contact);
        String trimmedContact = contact.trim();
        if (!Contact.isValidContact(trimmedContact)) {
            throw new ParseException(Contact.MESSAGE_CONSTRAINTS);
        }
        return new Contact(trimmedContact);
    }

    /**
     * Returns a {@code Products} parsed from the given {@code String}, or an empty products list if blank.
     */
    public static Products parseOptionalProducts(String products) throws ParseException {
        requireNonNull(products);
        return products.trim().isEmpty() ? Products.empty() : parseProducts(products);
    }

    /**
     * Returns a {@code Location} parsed from the given {@code String}, or an empty location if blank.
     */
    public static Location parseOptionalLocation(String location) throws ParseException {
        requireNonNull(location);
        return location.trim().isEmpty() ? Location.empty() : parseLocation(location);
    }

    /**
     * Returns a {@code Deadline} parsed from the given {@code String}, or an empty deadline if blank.
     */
    public static Deadline parseOptionalDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        return deadline.trim().isEmpty() ? Deadline.empty() : parseDeadline(deadline);
    }

    /**
     * Returns a {@code Contact} parsed from the given {@code String}, or an empty contact if blank.
     */
    public static Contact parseOptionalContact(String contact) throws ParseException {
        requireNonNull(contact);
        return contact.trim().isEmpty() ? Contact.empty() : parseContact(contact);
    }

    /**
     * Returns a {@code Tag} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Returns a {@code Product} parsed from the given {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code product} is invalid.
     */
    public static Product parseProduct(String product) throws ParseException {
        requireNonNull(product);
        String trimmedProduct = product.trim();
        if (!Product.isValidProductName(trimmedProduct)) {
            throw new ParseException(Product.MESSAGE_CONSTRAINTS);
        }
        return new Product(trimmedProduct);
    }

    /**
     * Returns {@code Collection<String> tags} parsed into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Normalizes short-form prefixes (e.g. n/, p/) to their long-form equivalents.
     */
    public static String normalizeShortPrefixes(String argsString) {
        requireNonNull(argsString);
        String normalized = argsString;
        for (Map.Entry<String, String> entry : SHORT_PREFIX_MAPPING.entrySet()) {
            normalized = normalized.replaceAll("(?<=^|\\s)" + Pattern.quote(entry.getKey()), entry.getValue());
        }
        return normalized;
    }
}
