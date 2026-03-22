package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexAndRemark_success() {
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Likes baseball"));
        assertParseSuccess(parser, "1 r/Likes baseball", expectedCommand);
    }

    @Test
    public void parse_indexWithEmptyRemark_success() {
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, Remark.empty());
        assertParseSuccess(parser, "1 r/", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a r/hello",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }
}
