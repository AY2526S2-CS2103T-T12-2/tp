package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.product.Product;

public class ProductListCommandTest {

    @Test
    public void execute_emptyList_showsEmptyMessage() throws Exception {
        ModelManager model = new ModelManager();
        CommandResult result = new ProductListCommand().execute(model);
        assertEquals(ProductListCommand.MESSAGE_EMPTY, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleProducts_sortedAlphabetically() throws Exception {
        ModelManager model = new ModelManager();
        model.addProduct(new Product("Vanilla Cake"));
        model.addProduct(new Product("Muffin"));
        model.addProduct(new Product("Brownie"));
        CommandResult result = new ProductListCommand().execute(model);
        assertEquals(String.format(ProductListCommand.MESSAGE_SUCCESS,
                        "Brownie, Muffin, Vanilla Cake"),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_sixProducts_wrapsAfterFive() throws Exception {
        ModelManager model = new ModelManager();
        model.addProduct(new Product("Brownie"));
        model.addProduct(new Product("Chocolate Cake"));
        model.addProduct(new Product("Cookie"));
        model.addProduct(new Product("Muffin"));
        model.addProduct(new Product("Vanilla Cake"));
        model.addProduct(new Product("Croissant"));
        CommandResult result = new ProductListCommand().execute(model);
        // sorted: Brownie, Chocolate Cake, Cookie, Croissant, Muffin / Vanilla Cake
        assertEquals(String.format(ProductListCommand.MESSAGE_SUCCESS,
                        "Brownie, Chocolate Cake, Cookie, Croissant, Muffin\n Vanilla Cake"),
                result.getFeedbackToUser());
    }
}
