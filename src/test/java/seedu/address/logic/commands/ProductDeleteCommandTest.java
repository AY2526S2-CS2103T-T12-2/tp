package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;
import seedu.address.model.product.Product;

public class ProductDeleteCommandTest {

    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProductDeleteCommand(null));
    }

    @Test
    public void execute_productDeleted_success() throws Exception {
        ModelManager model = new ModelManager();
        Product product = new Product("Muffin");
        model.addProduct(product);
        CommandResult result = new ProductDeleteCommand(product).execute(model);
        assertEquals(String.format(ProductDeleteCommand.MESSAGE_SUCCESS, "Muffin"),
                result.getFeedbackToUser());
        assertFalse(model.hasProduct(product));
    }

    @Test
    public void execute_productNotInCatalog_throwsCommandException() {
        ModelManager model = new ModelManager();
        assertThrows(CommandException.class, ProductDeleteCommand.MESSAGE_PRODUCT_NOT_FOUND, () ->
                        new ProductDeleteCommand(new Product("Muffin")).execute(model));
    }

    @Test
    public void execute_productInUse_throwsCommandException() {
        ModelManager model = new ModelManager();
        Product product = new Product("Muffin");
        model.addProduct(product);
        Person person = new Person(new Name("Alice"), new Products("Muffin"),
                Location.empty(), Deadline.empty(), Contact.empty());
        model.addPerson(person);
        assertThrows(CommandException.class, () ->
                new ProductDeleteCommand(product).execute(model));
    }

    @Test
    public void execute_productInUseCaseInsensitive_throwsCommandException() {
        ModelManager model = new ModelManager();
        model.addProduct(new Product("Muffin"));
        Person person = new Person(new Name("Alice"), new Products("muffin"),
                Location.empty(), Deadline.empty(), Contact.empty());
        model.addPerson(person);
        assertThrows(CommandException.class, () ->
                new ProductDeleteCommand(new Product("Muffin")).execute(model));
    }
}
