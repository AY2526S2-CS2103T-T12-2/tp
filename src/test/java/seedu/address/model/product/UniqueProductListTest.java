package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;

public class UniqueProductListTest {

    private final UniqueProductList list = new UniqueProductList();

    @Test
    public void contains_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> list.contains(null));
    }

    @Test
    public void contains_productNotInList_returnsFalse() {
        assertFalse(list.contains(new Product("Muffin")));
    }

    @Test
    public void contains_productInList_returnsTrue() {
        list.add(new Product("Muffin"));
        assertTrue(list.contains(new Product("muffin"))); // case-insensitive
    }

    @Test
    public void add_duplicateProduct_throwsDuplicateProductException() {
        list.add(new Product("Muffin"));
        assertThrows(DuplicateProductException.class, () -> list.add(new Product("Muffin")));
    }

    @Test
    public void remove_productNotInList_throwsProductNotFoundException() {
        assertThrows(ProductNotFoundException.class, () -> list.remove(new Product("Muffin")));
    }

    @Test
    public void remove_existingProduct_success() {
        list.add(new Product("Muffin"));
        list.remove(new Product("Muffin"));
        assertFalse(list.contains(new Product("Muffin")));
    }

    @Test
    public void setProducts_withDuplicates_throwsDuplicateProductException() {
        assertThrows(DuplicateProductException.class, () ->
                list.setProducts(List.of(new Product("Muffin"), new Product("muffin"))));
    }
}
