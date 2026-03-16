package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "2026-13-40";
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid deadlines
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline("2026-13-40")); // invalid date
        assertFalse(Deadline.isValidDeadline("2026/03/10")); // invalid format

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("2026-03-10"));
        assertTrue(Deadline.isValidDeadline("10/03/2026"));
        assertTrue(Deadline.isValidDeadline("2026-03-10 09:30"));
    }

    @Test
    public void emptyDeadline_behavesAsEmpty() {
        Deadline emptyDeadline = Deadline.empty();
        Deadline filledDeadline = new Deadline("2026-03-10 09:30");

        assertTrue(emptyDeadline.isEmpty());
        assertEquals("", emptyDeadline.toString());
        assertTrue(emptyDeadline.equals(Deadline.empty()));
        assertFalse(emptyDeadline.equals(filledDeadline));
        assertTrue(emptyDeadline.compareTo(filledDeadline) > 0);
        assertTrue(filledDeadline.compareTo(emptyDeadline) < 0);
    }

    @Test
    public void equals() {
        Deadline deadline = new Deadline("2026-03-10 09:30");

        // same values -> returns true
        assertTrue(deadline.equals(new Deadline("2026-03-10 09:30")));

        // same object -> returns true
        assertTrue(deadline.equals(deadline));

        // null -> returns false
        assertFalse(deadline.equals(null));

        // different types -> returns false
        assertFalse(deadline.equals(5.0f));

        // different values -> returns false
        assertFalse(deadline.equals(new Deadline("2026-03-11 09:30")));
    }
}
