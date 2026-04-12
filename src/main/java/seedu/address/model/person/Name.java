package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS = "Error: Invalid name format.";
    public static final String MESSAGE_NAME_REQUIRED = "Error: Name is required.";

    public static final int MAX_LENGTH = 100;
    private static final String VALIDATION_REGEX = "[A-Za-z .'-]+";

    private final String fullName;
    private final String normalizedName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        String normalized = normalizeSpaces(name);
        fullName = normalized;
        normalizedName = normalized.toLowerCase(Locale.ROOT);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        String normalized = normalizeSpaces(test);
        return !normalized.isBlank()
                && normalized.length() <= MAX_LENGTH
                && normalized.matches(VALIDATION_REGEX)
                && normalized.chars().anyMatch(Character::isLetter);
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return normalizedName.equals(otherName.normalizedName);
    }

    @Override
    public int hashCode() {
        return normalizedName.hashCode();
    }

    private static String normalizeSpaces(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

}
