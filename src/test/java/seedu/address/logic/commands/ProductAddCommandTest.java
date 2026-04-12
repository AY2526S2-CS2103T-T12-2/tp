package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.product.Product;

public class ProductAddCommandTest {

    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProductAddCommand(null));
    }

    @Test
    public void execute_productAccepted_addSuccessful() throws Exception {
        ModelManager model = new ModelManager();
        Product product = new Product("Muffin");
        CommandResult result = new ProductAddCommand(product).execute(model);
        assertEquals(String.format(ProductAddCommand.MESSAGE_SUCCESS, "Muffin"),
                result.getFeedbackToUser());
        assertTrue(model.hasProduct(product));
    }

    @Test
    public void execute_duplicateProduct_throwsCommandException() {
        ModelManager model = new ModelManager();
        Product product = new Product("Muffin");
        model.addProduct(product);
        assertThrows(CommandException.class, ProductAddCommand.MESSAGE_DUPLICATE_PRODUCT, () ->
                new ProductAddCommand(product).execute(model));
    }

    @Test
    public void execute_caseInsensitiveDuplicate_throwsCommandException() {
        ModelManager model = new ModelManager();
        model.addProduct(new Product("Muffin"));
        assertThrows(CommandException.class, ProductAddCommand.MESSAGE_DUPLICATE_PRODUCT, () ->
                new ProductAddCommand(new Product("muffin")).execute(model));
    }
}
