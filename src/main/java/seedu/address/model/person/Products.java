package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Customer's products list.
 * Guarantees: immutable; is valid as declared in {@link #isValidProducts(String)}.
 */
public class Products {

    public static final List<String> ALLOWED_PRODUCTS = List.of(
            "Muffin",
            "Chocolate Cake",
            "Vanilla Cake",
            "Brownie",
            "Cookie"
    );
    public static final int MAX_ITEM_COUNT = 5;
    public static final String MESSAGE_CONSTRAINTS = "Products must be a comma-separated list with up to "
            + MAX_ITEM_COUNT + " items, chosen from:\n"
            + String.join(", ", ALLOWED_PRODUCTS) + ".";
    public static final String MESSAGE_PRODUCTS_REQUIRED = "Error: Products are required.";

    private static final Map<String, String> CANONICAL_BY_LOWERCASE = buildCanonicalLookup();

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
        this.items = normalizeItems(products);
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
        if (parsedItems.size() > MAX_ITEM_COUNT) {
            return false;
        }
        for (String item : parsedItems) {
            if (item.isEmpty() || toCanonical(item) == null) {
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

    private static List<String> normalizeItems(String products) {
        List<String> rawItems = splitAndTrim(products);
        List<String> normalizedItems = new ArrayList<>(rawItems.size());
        for (String item : rawItems) {
            String canonical = toCanonical(item);
            normalizedItems.add(canonical == null ? item : canonical);
        }
        return Collections.unmodifiableList(normalizedItems);
    }

    private static String toCanonical(String item) {
        String trimmed = item.trim();
        return CANONICAL_BY_LOWERCASE.get(trimmed.toLowerCase(Locale.ROOT));
    }

    private static Map<String, String> buildCanonicalLookup() {
        Map<String, String> lookup = new HashMap<>();
        for (String product : ALLOWED_PRODUCTS) {
            lookup.put(product.toLowerCase(Locale.ROOT), product);
        }
        return Collections.unmodifiableMap(lookup);
    }
}
