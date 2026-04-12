package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRODUCTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCTS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCTS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCTS_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB
                + DEADLINE_DESC_BOB + CONTACT_DESC_BOB, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_shortPrefixes_success() {
        String userInput = " n/" + VALID_NAME_BOB + " p/" + VALID_PRODUCTS_BOB + " l/" + VALID_LOCATION_BOB
                + " d/" + VALID_DEADLINE_BOB + " c/" + VALID_CONTACT_BOB;
        Person expectedPerson = new Person(new Name(VALID_NAME_BOB), new Products(VALID_PRODUCTS_BOB),
                new Location(VALID_LOCATION_BOB), new Deadline(VALID_DEADLINE_BOB), new Contact(VALID_CONTACT_BOB));

        assertParseSuccess(parser, userInput, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB
                + DEADLINE_DESC_BOB + CONTACT_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple products
        assertParseFailure(parser, PRODUCTS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCTS));

        // multiple locations
        assertParseFailure(parser, LOCATION_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // multiple deadlines
        assertParseFailure(parser, DEADLINE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEADLINE));

        // multiple contacts
        assertParseFailure(parser, CONTACT_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PRODUCTS_DESC_AMY + LOCATION_DESC_AMY + DEADLINE_DESC_AMY + CONTACT_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PRODUCTS, PREFIX_LOCATION,
                        PREFIX_DEADLINE, PREFIX_CONTACT));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Person expectedPerson = new Person(new Name(AMY.getName().getFullName()), Products.empty(),
                Location.empty(), Deadline.empty(), Contact.empty());

        assertParseSuccess(parser, NAME_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_emptyContactValue_success() {
        Person expectedPerson = new Person(new Name(AMY.getName().getFullName()), Products.empty(),
                Location.empty(), Deadline.empty(), Contact.empty());

        assertParseSuccess(parser, NAME_DESC_AMY + " " + PREFIX_CONTACT, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_emptyOptionalFields_success() {
        Person expectedPerson = new Person(new Name(AMY.getName().getFullName()), Products.empty(),
                Location.empty(), Deadline.empty(), Contact.empty());

        assertParseSuccess(parser, NAME_DESC_AMY + " " + PREFIX_PRODUCTS + " " + PREFIX_LOCATION
                + " " + PREFIX_DEADLINE + " " + PREFIX_CONTACT, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB + DEADLINE_DESC_BOB
                + CONTACT_DESC_BOB, Name.MESSAGE_NAME_REQUIRED);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PRODUCTS_BOB + VALID_LOCATION_BOB + VALID_DEADLINE_BOB
                + VALID_CONTACT_BOB, Name.MESSAGE_NAME_REQUIRED);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB + DEADLINE_DESC_BOB
                + CONTACT_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid products
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PRODUCTS_DESC + LOCATION_DESC_BOB + DEADLINE_DESC_BOB
                + CONTACT_DESC_BOB, Products.MESSAGE_CONSTRAINTS);

        // invalid location (blank values are allowed)

        // invalid deadline
        assertParseFailure(parser, NAME_DESC_BOB + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB + INVALID_DEADLINE_DESC
                + CONTACT_DESC_BOB, Deadline.MESSAGE_CONSTRAINTS);

        // invalid contact
        assertParseFailure(parser, NAME_DESC_BOB + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB + DEADLINE_DESC_BOB
                + INVALID_CONTACT_DESC, Contact.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PRODUCTS_DESC_BOB + LOCATION_DESC_BOB
                + DEADLINE_DESC_BOB + CONTACT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // unknown prefix
        assertParseFailure(parser, NAME_DESC_BOB + " foo/bar",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
