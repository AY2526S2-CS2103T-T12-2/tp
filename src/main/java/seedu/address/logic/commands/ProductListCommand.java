package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;

/**
 * Lists all products in the product catalog.
 */
public class ProductListCommand extends Command {

    public static final String SUBCOMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = ProductCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Lists all products in the catalog.\n"
            + "Example: " + ProductCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD;

    public static final String MESSAGE_EMPTY = "No products in the catalog.";
    public static final String MESSAGE_SUCCESS = "Products in catalog:\n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<String> products = model.getProductList().stream()
                .map(Product::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        if (products.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        String formatted = StringUtil.formatListByLines(products, 5, " ");
        return new CommandResult(String.format(MESSAGE_SUCCESS, formatted));
    }
}
