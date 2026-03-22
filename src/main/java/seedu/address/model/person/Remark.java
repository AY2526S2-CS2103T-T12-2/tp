package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Customer's remark in ClientEase.
 * Guarantees: immutable; no validation is required.
 */
public class Remark {

    private static final Remark EMPTY = new Remark("", true);

    private final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark Any string.
     */
    public Remark(String remark) {
        this(remark, false);
    }

    private Remark(String remark, boolean skipNormalization) {
        requireNonNull(remark);
        value = skipNormalization ? remark : normalizeSpaces(remark);
    }

    /**
     * Returns an empty remark to represent missing input.
     */
    public static Remark empty() {
        return EMPTY; // shared empty remark
    }

    /**
     * Returns true if the remark is empty.
     */
    public boolean isEmpty() {
        return value.isBlank();
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

        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private static String normalizeSpaces(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }
}
