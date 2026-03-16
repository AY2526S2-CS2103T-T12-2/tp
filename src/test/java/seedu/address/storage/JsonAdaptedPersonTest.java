package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRODUCTS = "Cake, @@@";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_DEADLINE = "2026-13-40";
    private static final String INVALID_CONTACT = "bob!yahoo";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PRODUCTS = BENSON.getProducts().toString();
    private static final String VALID_LOCATION = BENSON.getLocation().toString();
    private static final String VALID_DEADLINE = BENSON.getDeadline().toString();
    private static final String VALID_CONTACT = BENSON.getContact().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PRODUCTS, VALID_LOCATION, VALID_DEADLINE, VALID_CONTACT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PRODUCTS, VALID_LOCATION, VALID_DEADLINE, VALID_CONTACT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidProducts_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PRODUCTS, VALID_LOCATION, VALID_DEADLINE, VALID_CONTACT);
        String expectedMessage = Products.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PRODUCTS, INVALID_LOCATION, VALID_DEADLINE, VALID_CONTACT);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PRODUCTS, VALID_LOCATION, INVALID_DEADLINE, VALID_CONTACT);
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidContact_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PRODUCTS, VALID_LOCATION, VALID_DEADLINE, INVALID_CONTACT);
        String expectedMessage = Contact.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_missingProducts_returnsPersonWithEmptyProducts() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_LOCATION, VALID_DEADLINE,
                VALID_CONTACT);
        Person expectedPerson = new Person(new Name(VALID_NAME), Products.empty(), new Location(VALID_LOCATION),
                new Deadline(VALID_DEADLINE), new Contact(VALID_CONTACT));
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_blankProducts_returnsPersonWithEmptyProducts() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, "", VALID_LOCATION, VALID_DEADLINE,
                VALID_CONTACT);
        Person expectedPerson = new Person(new Name(VALID_NAME), Products.empty(), new Location(VALID_LOCATION),
                new Deadline(VALID_DEADLINE), new Contact(VALID_CONTACT));
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_missingOptionalFields_returnsPersonWithEmptyOptionals() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PRODUCTS, null, null, null);
        Person expectedPerson = new Person(new Name(VALID_NAME), new Products(VALID_PRODUCTS), Location.empty(),
                Deadline.empty(), Contact.empty());
        assertEquals(expectedPerson, person.toModelType());
    }
}
