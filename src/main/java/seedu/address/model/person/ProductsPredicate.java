package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Products} contains any product given.
 */
public class ProductsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ProductsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        List<String> items = person.getProducts().getItems();
        return keywords.stream()
                .anyMatch(items::contains);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductsPredicate)) {
            return false;
        }

        ProductsPredicate otherProductsPredicate = (ProductsPredicate) other;
        return keywords.equals(otherProductsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
