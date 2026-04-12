package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Products} contains any product given.
 */
public class ProductsContainKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ProductsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getProducts().getItems().stream()
                        .anyMatch(product ->
                                StringUtil.containsWordIgnoreCase(product, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductsContainKeywordsPredicate)) {
            return false;
        }

        ProductsContainKeywordsPredicate otherProductsPredicate = (ProductsContainKeywordsPredicate) other;
        return keywords.equals(otherProductsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
