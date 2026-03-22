package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Remark remark = new Remark("Priority customer");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getProducts(),
                personToEdit.getLocation(),
                personToEdit.getDeadline(),
                personToEdit.getContact(),
                remark);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearRemark_success() {
        Model modelWithRemark = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = modelWithRemark.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getProducts(),
                personToEdit.getLocation(),
                personToEdit.getDeadline(),
                personToEdit.getContact(),
                new Remark("Regular"));
        modelWithRemark.setPerson(personToEdit, remarkedPerson);

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, Remark.empty());
        Person clearedPerson = new Person(
                remarkedPerson.getName(),
                remarkedPerson.getProducts(),
                remarkedPerson.getLocation(),
                remarkedPerson.getDeadline(),
                remarkedPerson.getContact(),
                Remark.empty());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                Messages.format(clearedPerson));

        Model expectedModel = new ModelManager(modelWithRemark.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(remarkedPerson, clearedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(remarkCommand, modelWithRemark, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark("Note"));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemarkCommand remarkFirstCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Note"));
        RemarkCommand remarkFirstCommandCopy = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Note"));
        RemarkCommand remarkFirstCommandDifferentRemark = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Other"));
        RemarkCommand remarkSecondCommand = new RemarkCommand(Index.fromOneBased(2), new Remark("Note"));

        assertTrue(remarkFirstCommand.equals(remarkFirstCommand));
        assertTrue(remarkFirstCommand.equals(remarkFirstCommandCopy));
        assertFalse(remarkFirstCommand.equals(1));
        assertFalse(remarkFirstCommand.equals(null));
        assertFalse(remarkFirstCommand.equals(remarkFirstCommandDifferentRemark));
        assertFalse(remarkFirstCommand.equals(remarkSecondCommand));
    }
}
