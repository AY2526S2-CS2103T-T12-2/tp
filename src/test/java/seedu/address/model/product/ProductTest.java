package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    private static final String VALID_NAME_80_CHARS = "a".repeat(80);
    private static final String INVALID_NAME_81_CHARS = "a".repeat(81);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Product(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        // EP: empty strings
        assertThrows(IllegalArgumentException.class, () -> new Product(""));

        // EP: blank strings
        assertThrows(IllegalArgumentException.class, () -> new Product(" "));

        // EP: contains comma
        assertThrows(IllegalArgumentException.class, () -> new Product("Muffin,Cake"));

        // EP: contains colon
        assertThrows(IllegalArgumentException.class, () -> new Product("Muffin:3"));

        // EP: too long
        assertThrows(IllegalArgumentException.class, () -> new Product(INVALID_NAME_81_CHARS));
    }

    @Test
    public void isValidProductName() {
        // EP: empty strings
        assertFalse(Product.isValidProductName(""));

        // EP: blank strings
        assertFalse(Product.isValidProductName("  "));

        // EP: contains comma
        assertFalse(Product.isValidProductName("Muffin,Cake"));

        // EP: contains colon
        assertFalse(Product.isValidProductName("Muffin:3"));

        // BVA: 81 characters
        assertFalse(Product.isValidProductName(INVALID_NAME_81_CHARS));

        // BVA: 1 character
        assertTrue(Product.isValidProductName("A"));

        // EP: valid simple name
        assertTrue(Product.isValidProductName("Muffin"));

        // EP: valid with spaces
        assertTrue(Product.isValidProductName("Red Velvet Cake"));

        // EP: leading/trailing spaces are trimmed
        assertTrue(Product.isValidProductName("  Muffin  "));

        // BVA: 80 characters
        assertTrue(Product.isValidProductName(VALID_NAME_80_CHARS));
    }

    @Test
    public void isSameProduct_caseInsensitive() {
        Product p = new Product("Muffin");
        assertTrue(p.isSameProduct(new Product("muffin")));
        assertTrue(p.isSameProduct(new Product("MUFFIN")));
        assertFalse(p.isSameProduct(new Product("Cookie")));
    }

    @Test
    public void equals_caseInsensitive() {
        assertEquals(new Product("Muffin"), new Product("muffin"));
    }

    @Test
    public void getName_normalizesSpaces() {
        assertEquals("Chocolate Cake", new Product("Chocolate  Cake").getName());
    }
}
