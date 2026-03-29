package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
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
    public static final int MAX_QTY_PER_PRODUCT = 10000;
    public static final int MAX_TOTAL_QTY = 100000;
    public static final String MESSAGE_CONSTRAINTS = "Products must be a comma-separated list of product names, "
            + "each optionally with a positive quantity (e.g. Muffin:3). Quantities must not exceed "
            + MAX_QTY_PER_PRODUCT + " per product or " + MAX_TOTAL_QTY + " in total.";

    private static final Products EMPTY = new Products(Collections.emptyMap());
    private static final java.util.logging.Logger logger =
            seedu.address.commons.core.LogsCenter.getLogger(Products.class);

    private final Map<String, Integer> itemMap;

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
     * Allows duplicate entries, which are merged by name (case-insensitive).
     */
    public static boolean isValidProducts(String test) {
        requireNonNull(test);
        if (test.trim().isEmpty()) {
            return false;
        }

        String[] parts = test.split(",");
        Map<String, Long> perProductTotals = new LinkedHashMap<>();
        long overallTotal = 0;

        for (String part : parts) {
            String trimmedPart = part.trim();
            if (trimmedPart.isEmpty()) {
                return false;
            }

            String[] pair = trimmedPart.split(":", 2);
            String itemName = normalizeSpaces(pair[0]);
            if (itemName.isBlank()) {
                return false;
            }

            // Validate the quantity if a colon is present
            int qty = 1;
            if (pair.length > 1) {
                try {
                    String qtyText = pair[1].trim();
                    if (qtyText.isEmpty()) {
                        return false;
                    }
                    qty = Integer.parseInt(qtyText);
                    if (qty <= 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            String key = itemName.toLowerCase(Locale.ROOT);
            long updated = perProductTotals.getOrDefault(key, 0L) + qty;
            if (updated > MAX_QTY_PER_PRODUCT) {
                return false;
            }
            perProductTotals.put(key, updated);
            overallTotal += qty;
            if (overallTotal > MAX_TOTAL_QTY) {
                return false;
            }
        }

        return true;
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
        Products otherProducts = (Products) other;
        return toCanonicalMap().equals(otherProducts.toCanonicalMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toCanonicalMap());
    }

    private Map<String, Integer> toCanonicalMap() {
        Map<String, Integer> canonical = new LinkedHashMap<>();
        itemMap.forEach((name, qty) -> canonical.put(name.toLowerCase(Locale.ROOT), qty));
        return canonical;
    }

    private static Map<String, Integer> parseItemsWithQuantity(String products) {
        Map<String, Integer> map = new LinkedHashMap<>();
        Map<String, String> displayNames = new LinkedHashMap<>();
        for (String part : products.split(",")) {
            String[] pair = part.trim().split(":", 2);
            String name = normalizeSpaces(pair[0]);
            String key = name.toLowerCase(Locale.ROOT);
            String displayName = displayNames.computeIfAbsent(key, ignored -> name);
            int qty = (pair.length > 1) ? Integer.parseInt(pair[1].trim()) : 1;
            map.merge(displayName, qty, Products::safeAdd);
        }
        return Collections.unmodifiableMap(map);
    }

    private static int safeAdd(int a, int b) {
        long sum = (long) a + b;
        if (sum > MAX_QTY_PER_PRODUCT) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return (int) sum;
    }

    private static String normalizeSpaces(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }
}
