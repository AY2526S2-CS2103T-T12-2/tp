package seedu.address.model.product.exceptions;

/**
 * Signals that the operation will result in duplicate Products (Products are considered duplicates if they have the
 * same identity).
 */
public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException() {
        super("Operation would result in duplicate products");
    }
}
