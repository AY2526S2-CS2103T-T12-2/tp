package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Customer's products list.
 * Guarantees: immutable; is valid as declared in {@link #isValidProducts(String)}.
 */
public class Products {

    public static final String MESSAGE_CONSTRAINTS = "Error: Invalid products format.";
    public static final String MESSAGE_PRODUCTS_REQUIRED = "Error: Products are required.";

    private static final int MAX_ITEM_LENGTH = 80;
    private static final String ITEM_VALIDATION_REGEX = "[\\p{Alnum} _\\-:;\\.]+";

    private static final Products EMPTY = new Products(Collections.emptyList());

    private final List<String> items;

    /**
     * Constructs a {@code Products}.
     *
     * @param products A valid products list.
     */
    public Products(String products) {
        requireNonNull(products);
        checkArgument(isValidProducts(products), MESSAGE_CONSTRAINTS);
        this.items = splitAndTrim(products);
    }

    private Products(List<String> items) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    /**
     * Returns an empty products list to represent missing input.
     */
    public static Products empty() {
        return EMPTY; // shared empty products list
    }

    /**
     * Returns true if a given string is a valid products list.
     */
    public static boolean isValidProducts(String test) {
        requireNonNull(test);
        List<String> parsedItems = splitAndTrim(test);
        if (parsedItems.isEmpty()) {
            return false;
        }
        for (String item : parsedItems) {
            if (item.isEmpty() || item.length() > MAX_ITEM_LENGTH || !item.matches(ITEM_VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an immutable list of the parsed product items.
     */
    public List<String> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public String toString() {
        return String.join(", ", items);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Products)) {
            return false;
        }

        Products otherProducts = (Products) other;
        return items.equals(otherProducts.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    private static List<String> splitAndTrim(String products) {
        String[] rawItems = products.split(",", -1);
        List<String> trimmedItems = new ArrayList<>();
        for (String item : rawItems) {
            trimmedItems.add(item.trim());
        }
        return Collections.unmodifiableList(trimmedItems);
    }
}
