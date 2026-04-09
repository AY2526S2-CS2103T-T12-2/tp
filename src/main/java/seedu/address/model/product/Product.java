package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a product name in the product catalog.
 * Guarantees: immutable; is valid as declared in {@link #isValidProductName(String)}.
 */
public class Product {

    public static final String MESSAGE_CONSTRAINTS =
            "Error: Invalid product name. Names must be 1 to 80 characters long, and cannot contain ',' or ':'.";

    private final String name;
    private final String normalizedName;

    /**
     * Constructs a {@code Product}.
     *
     * @param name A valid product name.
     */
    public Product(String name) {
        requireNonNull(name);
        checkArgument(isValidProductName(name), MESSAGE_CONSTRAINTS);
        String normalized = normalizeSpaces(name);
        this.name = normalized;
        this.normalizedName = normalized.toLowerCase(Locale.ROOT);
    }

    /**
     * Returns true if a given string is a valid product name.
     */
    public static boolean isValidProductName(String test) {
        requireNonNull(test);
        String normalized = normalizeSpaces(test);
        return !normalized.isBlank()
                && normalized.length() <= 80
                && !normalized.contains(",")
                && !normalized.contains(":");
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if both products have the same identity (case-insensitive).
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }
        return otherProduct != null && normalizedName.equals(otherProduct.normalizedName);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Product)) {
            return false;
        }
        Product otherProduct = (Product) other;
        return normalizedName.equals(otherProduct.normalizedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedName);
    }

    private static String normalizeSpaces(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }
}
