package seedu.address.logic.commands;

/**
 * Command word and usage for product catalog management.
 */
public final class ProductCommand {

    public static final String COMMAND_WORD = "product";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Manages the product catalog.\n"
            + "Subcommands:\n"
            + "  " + COMMAND_WORD + " add product/NAME (or p/NAME)\n"
            + "  " + COMMAND_WORD + " delete product/NAME (or p/NAME)\n"
            + "  " + COMMAND_WORD + " list";

    private ProductCommand() {}
}
