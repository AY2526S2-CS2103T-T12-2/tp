package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Customer's products list with quantities.
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
    public static final int MAX_ITEM_TYPES = 5;
    public static final String MESSAGE_CONSTRAINTS = "Products must be a comma-separated list with up to "
            + MAX_ITEM_TYPES + " types, each optionally with a quantity (e.g. Muffin:3), chosen from: "
            + String.join(", ", ALLOWED_PRODUCTS) + ".";

    private static final Map<String, String> CANONICAL_BY_LOWERCASE = buildCanonicalLookup();
    private static final Products EMPTY = new Products(Collections.emptyMap());

    private final Map<String, Integer> itemMap;

    private static final java.util.logging.Logger logger =
            seedu.address.commons.core.LogsCenter.getLogger(Products.class);

    /**
     * Constructs a {@code Products} from a comma-separated string.
     * Each item may optionally include a quantity suffix (e.g. "Muffin:3").
     */
    public Products(String products) {
        requireNonNull(products);
        checkArgument(isValidProducts(products), MESSAGE_CONSTRAINTS);
        this.itemMap = parseItemsWithQuantity(products);
        logger.fine("Products created: " + this.itemMap.size() + " types, total qty: " + getTotalQuantity());
    }

    private Products(Map<String, Integer> items) {
        this.itemMap = Collections.unmodifiableMap(new LinkedHashMap<>(items));
    }

    public static Products empty() {
        return EMPTY;
    }

    /**
     * Returns true if the given string is a valid products input.
     * Allows duplicate entries in the string as long as the number of UNIQUE
     * types does not exceed MAX_ITEM_TYPES.
     */
    public static boolean isValidProducts(String test) {
        requireNonNull(test);
        if (test.trim().isEmpty()) {
            return false;
        }

        String[] parts = test.split(",");
        java.util.Set<String> uniqueTypes = new java.util.HashSet<>();

        for (String part : parts) {
            String[] pair = part.trim().split(":", 2);
            String itemName = pair[0].trim();
            String canonicalName = toCanonical(itemName);

            // Fail if the product name isn't in our ALLOWED_PRODUCTS list
            if (canonicalName == null) {
                return false;
            }

            // Validate the quantity if a colon is present
            if (pair.length > 1) {
                try {
                    if (Integer.parseInt(pair[1].trim()) <= 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            // Add the canonical name to the set to track unique types
            uniqueTypes.add(canonicalName);
        }

        // Finally, check if the number of unique types is within the limit
        return uniqueTypes.size() <= MAX_ITEM_TYPES;
    }

    /**
     * Returns an immutable list of product names (without quantities).
     */
    public List<String> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(itemMap.keySet()));
    }

    /**
     * Returns an immutable map of product names to their quantities.
     */
    public Map<String, Integer> getItemMap() {
        return itemMap;
    }

    /**
     * Returns the total quantity of all products combined.
     */
    public int getTotalQuantity() {
        return itemMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public String toString() {
        List<String> parts = new ArrayList<>();
        itemMap.forEach((name, qty) -> parts.add(qty > 1 ? name + ":" + qty : name));
        return String.join(", ", parts);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Products)) {
            return false;
        }
        return itemMap.equals(((Products) other).itemMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemMap);
    }

    private static Map<String, Integer> parseItemsWithQuantity(String products) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String part : products.split(",")) {
            String[] pair = part.trim().split(":", 2);
            String name = toCanonical(pair[0].trim());
            int qty = (pair.length > 1) ? Integer.parseInt(pair[1].trim()) : 1;
            map.merge(name, qty, Integer::sum);
        }
        return Collections.unmodifiableMap(map);
    }

    private static String toCanonical(String item) {
        return CANONICAL_BY_LOWERCASE.get(item.trim().toLowerCase(Locale.ROOT));
    }

    private static Map<String, String> buildCanonicalLookup() {
        Map<String, String> lookup = new HashMap<>();
        for (String product : ALLOWED_PRODUCTS) {
            lookup.put(product.toLowerCase(Locale.ROOT), product);
        }
        return Collections.unmodifiableMap(lookup);
    }
}
