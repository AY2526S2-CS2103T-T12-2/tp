package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;

/**
 * Adds a product to the product catalog.
 */
public class ProductAddCommand extends Command {

    public static final String SUBCOMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = ProductCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Adds a product to the catalog.\n"
            + "Parameters: product/NAME (or p/NAME)\n"
            + "Example: " + ProductCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " product/Muffin";

    public static final String MESSAGE_SUCCESS = "Added product: %1$s";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the catalog.";

    private final Product toAdd;

    /**
     * Creates a ProductAddCommand to add the specified {@code Product}.
     */
    public ProductAddCommand(Product product) {
        requireNonNull(product);
        this.toAdd = product;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProduct(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.addProduct(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }
}
