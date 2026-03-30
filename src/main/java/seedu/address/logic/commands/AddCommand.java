package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

/**
 * Adds a customer to ClientEase.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to ClientEase. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PRODUCTS + "PRODUCTS] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_DEADLINE + "DATE] "
            + "[" + PREFIX_CONTACT + "CONTACT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Samuel "
            + PREFIX_PRODUCTS + "Chocolate Cake "
            + PREFIX_LOCATION + "Boon lay "
            + PREFIX_DEADLINE + "2026-03-10 "
            + PREFIX_CONTACT + "91234567";

    public static final String MESSAGE_SUCCESS = "Added Customer: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This customer already exists in ClientEase";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        validateProductsExist(model);

        if (model.hasPerson(toAdd)) {
            logger.warning("Duplicate customer rejected: " + toAdd.getName());
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        logger.info("Adding customer: " + toAdd.getName());
        model.addPerson(toAdd); // saves new customer in model
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName())); // shows success message
    }

    private void validateProductsExist(Model model) throws CommandException {
        Products products = toAdd.getProducts();
        if (products.getItems().isEmpty()) {
            return;
        }

        List<String> allowedProducts = model.getProductList().stream()
                .map(Product::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
        Set<String> allowedLower = allowedProducts.stream()
                .map(name -> name.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        for (String item : products.getItems()) {
            if (!allowedLower.contains(item.toLowerCase(Locale.ROOT))) {
                throw new CommandException(Products.buildUnknownProductMessage(item, allowedProducts));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
