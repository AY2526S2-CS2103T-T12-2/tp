package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContactContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContactContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getContact().getEntries().stream()
                        .anyMatch(contact ->
                                StringUtil.containsWordIgnoreCase(keyword, contact)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactContainsKeywordsPredicate)) {
            return false;
        }

        ContactContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (ContactContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
