package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Customer's location in ClientEase.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}.
 */
public class Location {

    public static final String MESSAGE_CONSTRAINTS = "Error: Invalid location.";

    private static final int MAX_LENGTH = 200;
    private static final Location EMPTY = new Location("", true);

    private final String value;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        this(location, false);
    }

    private Location(String value, boolean skipValidation) {
        requireNonNull(value);
        if (skipValidation) {
            this.value = value;
            return;
        }

        String normalized = normalizeSpaces(value);
        checkArgument(isValidLocation(normalized), MESSAGE_CONSTRAINTS);
        this.value = normalized;
    }

    /**
     * Returns an empty location to represent missing input.
     */
    public static Location empty() {
        return EMPTY; // shared empty location
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        requireNonNull(test);
        String normalized = normalizeSpaces(test);
        return !normalized.isBlank() && normalized.length() <= MAX_LENGTH;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return value.equals(otherLocation.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private static String normalizeSpaces(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }
}
