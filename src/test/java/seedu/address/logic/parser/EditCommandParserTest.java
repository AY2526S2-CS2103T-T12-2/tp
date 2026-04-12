package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRODUCTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCTS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCTS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCTS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCTS_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Products;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_PRODUCTS_DESC, Products.MESSAGE_CONSTRAINTS);
        // invalid location (blank values are allowed)
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_CONTACT_DESC, Contact.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_PRODUCTS_DESC + INVALID_LOCATION_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PRODUCTS_DESC_BOB + DEADLINE_DESC_AMY
                + CONTACT_DESC_AMY + LOCATION_DESC_AMY + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withProducts(VALID_PRODUCTS_BOB).withLocation(VALID_LOCATION_AMY)
                .withDeadline(VALID_DEADLINE_AMY).withContact(VALID_CONTACT_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PRODUCTS_DESC_BOB + CONTACT_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withProducts(VALID_PRODUCTS_BOB)
                .withContact(VALID_CONTACT_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_shortPrefixes_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " n/" + VALID_NAME_AMY + " p/" + VALID_PRODUCTS_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withProducts(VALID_PRODUCTS_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        // name
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // products
        userInput = targetIndex.getOneBased() + PRODUCTS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withProducts(VALID_PRODUCTS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withLocation(VALID_LOCATION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withDeadline(VALID_DEADLINE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // contact
        userInput = targetIndex.getOneBased() + CONTACT_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withContact(VALID_CONTACT_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyContactValue_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_CONTACT;

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setContact(Contact.empty());
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyOptionalFields_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PRODUCTS + " " + PREFIX_LOCATION
                + " " + PREFIX_DEADLINE + " " + PREFIX_CONTACT;

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setProducts(Products.empty());
        descriptor.setLocation(Location.empty());
        descriptor.setDeadline(Deadline.empty());
        descriptor.setContact(Contact.empty());
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PRODUCTS_DESC + PRODUCTS_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCTS));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PRODUCTS_DESC_BOB + INVALID_PRODUCTS_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCTS));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PRODUCTS_DESC_AMY + LOCATION_DESC_AMY + DEADLINE_DESC_AMY
                + CONTACT_DESC_AMY + PRODUCTS_DESC_BOB + LOCATION_DESC_AMY + DEADLINE_DESC_AMY + CONTACT_DESC_AMY;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCTS, PREFIX_LOCATION, PREFIX_DEADLINE,
                        PREFIX_CONTACT));
    }
}
