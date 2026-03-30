package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Locale;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Deletes a product from the product catalog.
 */
public class ProductDeleteCommand extends Command {

    public static final String SUBCOMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = ProductCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Deletes a product from the catalog.\n"
            + "Parameters: product/NAME (or p/NAME)\n"
            + "Example: " + ProductCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " product/Muffin";

    public static final String MESSAGE_SUCCESS = "Deleted product: %1$s";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "This product does not exist in the catalog.";
    public static final String MESSAGE_PRODUCT_IN_USE =
            "not allowed to delete, there is still existing orders with %1$s";

    private final Product toDelete;

    /**
     * Creates a {@code ProductDeleteCommand} to delete the specified product.
     */
    public ProductDeleteCommand(Product product) {
        requireNonNull(product);
        this.toDelete = product;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasProduct(toDelete)) {
            throw new CommandException(MESSAGE_PRODUCT_NOT_FOUND);
        }

        if (isProductInUse(model, toDelete)) {
            throw new CommandException(String.format(MESSAGE_PRODUCT_IN_USE, toDelete.getName()));
        }

        model.deleteProduct(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getName()));
    }

    private boolean isProductInUse(Model model, Product product) {
        String target = product.getName().toLowerCase(Locale.ROOT);
        for (Person person : model.getAddressBook().getPersonList()) {
            for (String item : person.getProducts().getItems()) {
                if (item.toLowerCase(Locale.ROOT).equals(target)) {
                    return true;
                }
            }
        }
        return false;
    }
}
