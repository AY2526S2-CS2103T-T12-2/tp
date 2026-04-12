package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Contact(null));
    }

    @Test
    public void constructor_invalidContact_throwsIllegalArgumentException() {
        String invalidContact = "bob!yahoo";
        assertThrows(IllegalArgumentException.class, () -> new Contact(invalidContact));
    }

    @Test
    public void isValidContact() {
        // null contact
        assertThrows(NullPointerException.class, () -> Contact.isValidContact(null));

        // invalid contacts
        assertFalse(Contact.isValidContact("")); // empty string
        assertFalse(Contact.isValidContact(" ")); // spaces only
        assertFalse(Contact.isValidContact("bob!yahoo")); // invalid email
        String longEmail = "a".repeat(89) + "@example.com";
        assertFalse(Contact.isValidContact(longEmail)); // email too long
        assertFalse(Contact.isValidContact("123")); // too short phone
        assertFalse(Contact.isValidContact("91234567;")); // empty entry

        // valid contacts
        assertTrue(Contact.isValidContact("91234567"));
        assertTrue(Contact.isValidContact("+6591234567"));
        assertTrue(Contact.isValidContact("alice@example.com"));
        assertTrue(Contact.isValidContact("9123 4567; alice@example.com"));
    }

    @Test
    public void equals() {
        Contact contact = new Contact("91234567; alice@example.com");

        // same values -> returns true
        assertTrue(contact.equals(new Contact("91234567; alice@example.com")));

        // same object -> returns true
        assertTrue(contact.equals(contact));

        // null -> returns false
        assertFalse(contact.equals(null));

        // different types -> returns false
        assertFalse(contact.equals(5.0f));

        // different values -> returns false
        assertFalse(contact.equals(new Contact("91234567")));
    }

    @Test
    public void equals_emailCaseInsensitive() {
        Contact contact = new Contact("John@Example.com");

        assertTrue(contact.equals(new Contact("john@example.com")));
    }

    @Test
    public void equals_entryOrderInsensitive() {
        Contact contact = new Contact("91234567; alice@example.com");

        assertTrue(contact.equals(new Contact("alice@example.com; 91234567")));
    }
}
