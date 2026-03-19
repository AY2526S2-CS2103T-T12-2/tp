package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Customer's contact information in ClientEase.
 * Guarantees: immutable; is valid as declared in {@link #isValidContact(String)}.
 */
public class Contact {

    public static final String MESSAGE_CONSTRAINTS = "Error: Invalid contact information.";
    public static final int MAX_EMAIL_LENGTH = 100;

    /**
     * Regex for validating local phone numbers (exactly 8 digits)
     */
    private static final String LOCAL_PHONE_REGEX = "\\d{8}";

    /**
     * Regex for validating international phone numbers
     *
     * '+' then 2–3 digit country code
     * 1–12 digit subscriber number
     */
    private static final String INTERNATIONAL_PHONE_REGEX = "\\+\\d{2,3}\\d{1,12}";

    /**
     * Regex for validating email addresses
     *
     * [A-Za-z0-9]     first character must be alphanumeric
     * [A-Za-z0-9.-]*  then zero or more alphanumerics, dots, or hyphens
     * @              exactly one '@'
     * [A-Za-z0-9]     domain must start with alphanumeric
     * [A-Za-z0-9.-]*  then zero or more alphanumerics, dots or hyphens
     */
    private static final String EMAIL_REGEX =
            "[A-Za-z0-9][A-Za-z0-9.-]*@[A-Za-z0-9][A-Za-z0-9.-]*";

    private static final Contact EMPTY = new Contact(Collections.emptyList());

    private final List<String> entries;

    /**
     * Constructs a {@code Contact}.
     *
     * @param contact A valid contact string.
     */
    public Contact(String contact) {
        requireNonNull(contact);
        Optional<List<String>> normalized = normalizeEntries(contact);
        checkArgument(normalized.isPresent(), MESSAGE_CONSTRAINTS);
        entries = normalized.get();
    }

    private Contact(List<String> entries) {
        this.entries = Collections.unmodifiableList(new ArrayList<>(entries));
    }

    /**
     * Returns an empty contact to represent missing input.
     */
    public static Contact empty() {
        return EMPTY; // shared empty contact list
    }

    /**
     * Returns true if a given string is a valid contact string.
     */
    public static boolean isValidContact(String test) {
        requireNonNull(test);
        return normalizeEntries(test).isPresent();
    }

    /**
     * Returns an immutable list of parsed contact entries.
     */
    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public String toString() {
        return String.join("; ", entries);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return entries.equals(otherContact.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries);
    }

    private static Optional<List<String>> normalizeEntries(String contact) {
        String trimmed = contact.trim();
        if (trimmed.isEmpty()) {
            return Optional.empty();
        }

        String[] rawEntries = contact.split(";", -1);
        List<String> normalizedEntries = new ArrayList<>();
        for (String rawEntry : rawEntries) {
            String entry = rawEntry.trim();
            if (entry.isEmpty()) {
                return Optional.empty();
            }

            String phoneCandidate = entry.replaceAll("\\s+", "");
            if (isValidPhone(phoneCandidate)) {
                normalizedEntries.add(phoneCandidate);
                continue;
            }

            if (isValidEmail(entry)) {
                normalizedEntries.add(entry);
                continue;
            }

            return Optional.empty();
        }

        return Optional.of(Collections.unmodifiableList(normalizedEntries));
    }

    private static boolean isValidPhone(String candidate) {
        return candidate.matches(LOCAL_PHONE_REGEX) || candidate.matches(INTERNATIONAL_PHONE_REGEX);
    }

    private static boolean isValidEmail(String candidate) {
        return candidate.length() <= MAX_EMAIL_LENGTH && candidate.matches(EMAIL_REGEX);
    }
}
