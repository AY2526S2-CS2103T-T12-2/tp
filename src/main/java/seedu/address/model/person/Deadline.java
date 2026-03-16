package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Customer's order deadline in ClientEase.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}.
 */
public class Deadline implements Comparable<Deadline> {

    public static final String MESSAGE_CONSTRAINTS = "Error: Invalid date format.";

    private static final DateTimeFormatter INPUT_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter INPUT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_SLASH_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final Deadline EMPTY = new Deadline(null, true);

    private final LocalDateTime value;
    private final boolean isEmpty;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline string.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        LocalDateTime parsed = parseDeadline(deadline);
        checkArgument(parsed != null, MESSAGE_CONSTRAINTS);
        value = parsed;
        isEmpty = false;
    }

    private Deadline(LocalDateTime value, boolean isEmpty) {
        this.value = value;
        this.isEmpty = isEmpty;
    }

    /**
     * Returns an empty deadline when input is missing.
     */
    public static Deadline empty() {
        return EMPTY; // shared empty deadline instance
    }

    /**
     * Returns true if this deadline is empty.
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        requireNonNull(test);
        return parseDeadline(test) != null;
    }

    @Override
    public String toString() {
        return isEmpty ? "" : value.format(OUTPUT_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        if (isEmpty && otherDeadline.isEmpty) {
            return true;
        }
        if (isEmpty || otherDeadline.isEmpty) {
            return false;
        }
        return value.equals(otherDeadline.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isEmpty);
    }

    @Override
    public int compareTo(Deadline other) {
        if (other == this) {
            return 0;
        }
        if (isEmpty && other.isEmpty) {
            return 0;
        }
        if (isEmpty) {
            return 1;
        }
        if (other.isEmpty) {
            return -1;
        }
        return value.compareTo(other.value);
    }

    private static LocalDateTime parseDeadline(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(trimmed, INPUT_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ignored) {
            // Fall through to try other formats.
        }

        LocalDate date = parseDate(trimmed);
        if (date == null) {
            return null;
        }

        return date.atTime(23, 59); // default time when only date is given
    }

    private static LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, INPUT_DATE_FORMATTER);
        } catch (DateTimeParseException ignored) {
            // Fall through to try other formats.
        }

        try {
            return LocalDate.parse(input, INPUT_SLASH_DATE_FORMATTER);
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }
}
