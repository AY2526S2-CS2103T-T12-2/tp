package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ProductsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Products(null));
    }

    @Test
    public void constructor_invalidProducts_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Products("Muffin, @@"));
    }

    @Test
    public void isValidProducts() {
        // null products
        assertThrows(NullPointerException.class, () -> Products.isValidProducts(null));

        // invalid products
        assertFalse(Products.isValidProducts("")); // empty string
        assertFalse(Products.isValidProducts(" ")); // spaces only
        assertFalse(Products.isValidProducts("Muffin, ")); // empty item
        assertFalse(Products.isValidProducts(",Muffin")); // empty item
        assertFalse(Products.isValidProducts("Muffin,,Cookie")); // empty item
        assertFalse(Products.isValidProducts("Muffin, @@@")); // invalid items
        assertFalse(Products.isValidProducts("Tiramisu")); // not in placeholder list
        assertFalse(Products.isValidProducts("Muffin:0")); // zero quantity
        assertFalse(Products.isValidProducts("Muffin:-1")); // negative quantity
        assertFalse(Products.isValidProducts("Muffin:abc")); // non-numeric quantity
        assertFalse(Products.isValidProducts("Muffin:")); // colon without number

        // valid products
        assertTrue(Products.isValidProducts("Chocolate Cake"));
        assertTrue(Products.isValidProducts("Chocolate Cake, Vanilla Cake"));
        assertTrue(Products.isValidProducts("chocolate cake")); // case-insensitive
        assertTrue(Products.isValidProducts("  Muffin  ,  Cookie:2  ")); // with extra spaces
        assertTrue(Products.isValidProducts("Muffin:3")); // with quantity
        assertTrue(Products.isValidProducts("Muffin:3, Cookie:2")); // multiple with quantity
        // exactly 5 types (MAX_ITEM_TYPES)
        assertTrue(Products.isValidProducts("Muffin, Chocolate Cake, Vanilla Cake, Brownie, Cookie"));
    }

    @Test
    public void constructor_duplicateProducts_mergesQuantities() {
        Products products = new Products("Muffin, Muffin, Chocolate Cake");
        assertEquals(List.of("Muffin", "Chocolate Cake"), products.getItems());
        assertEquals(3, products.getTotalQuantity()); // Muffin x2 + Chocolate Cake x1
    }

    @Test
    public void getItemMap_returnsCorrectMap() {
        Products products = new Products("Muffin:3, Cookie:1");
        Map<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("Muffin", 3);
        expectedMap.put("Cookie", 1);

        assertEquals(expectedMap, products.getItemMap());

        // Verify immutability
        assertThrows(UnsupportedOperationException.class, () -> products.getItemMap().put("Brownie", 1));
    }

    @Test
    public void getItems_returnsImmutableList() {
        Products products = new Products("Muffin, Cookie");
        List<String> items = products.getItems();

        assertEquals(2, items.size());
        // Verify immutability
        assertThrows(UnsupportedOperationException.class, () -> items.add("Brownie"));
    }

    @Test
    public void getTotalQuantity_test() {
        assertEquals(5, new Products("Muffin:3, Cookie:2").getTotalQuantity());
        assertEquals(1, new Products("Muffin").getTotalQuantity());
        assertEquals(0, Products.empty().getTotalQuantity()); // Covers private constructor & EMPTY
    }

    @Test
    public void toString_test() {
        assertEquals("Muffin", new Products("Muffin").toString());
        assertEquals("Muffin:3, Cookie:2", new Products("Muffin:3, Cookie:2").toString());
    }

    @Test
    public void equals() {
        Products products = new Products("Chocolate Cake, Vanilla Cake");

        assertTrue(products.equals(new Products("Chocolate Cake, Vanilla Cake"))); // same values
        assertTrue(products.equals(products)); // same object
        assertFalse(products.equals(null)); // null
        assertFalse(products.equals(5.0f)); // different type
        assertFalse(products.equals(new Products("Chocolate Cake"))); // different values
    }

    @Test
    public void hashCode_test() {
        Products p1 = new Products("Muffin:3, Cookie:2");
        Products p2 = new Products("Muffin:3, Cookie:2");
        Products p3 = new Products("Muffin:1");

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }
}
