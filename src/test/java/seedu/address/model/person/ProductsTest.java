package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Products(null));
    }

    @Test
    public void constructor_invalidProducts_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Products("Cake, @@"));
    }

    @Test
    public void isValidProducts() {
        // null products
        assertThrows(NullPointerException.class, () -> Products.isValidProducts(null));

        // invalid products
        assertFalse(Products.isValidProducts("")); // empty string
        assertFalse(Products.isValidProducts(" ")); // spaces only
        assertFalse(Products.isValidProducts("Cake, ")); // empty item
        assertFalse(Products.isValidProducts(",Cake")); // empty item
        assertFalse(Products.isValidProducts("Cake,,Pie")); // empty item
        assertFalse(Products.isValidProducts("Cake, @@@")); // invalid characters
        assertFalse(Products.isValidProducts("a".repeat(81))); // item too long

        // valid products
        assertTrue(Products.isValidProducts("Chocolate cake"));
        assertTrue(Products.isValidProducts("Chocolate cake, Vanilla_cake"));
        assertTrue(Products.isValidProducts("Item-1:small;batch."));
    }

    @Test
    public void equals() {
        Products products = new Products("Chocolate cake, Vanilla_cake");

        // same values -> returns true
        assertTrue(products.equals(new Products("Chocolate cake, Vanilla_cake")));

        // same object -> returns true
        assertTrue(products.equals(products));

        // null -> returns false
        assertFalse(products.equals(null));

        // different types -> returns false
        assertFalse(products.equals(5.0f));

        // different values -> returns false
        assertFalse(products.equals(new Products("Chocolate cake")));
    }
}
