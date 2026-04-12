package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ProductAddCommand;
import seedu.address.logic.commands.ProductCommand;
import seedu.address.logic.commands.ProductDeleteCommand;
import seedu.address.logic.commands.ProductListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Product;

/**
 * Parses input arguments for product catalog commands.
 */
public class ProductCommandParser implements Parser<Command> {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<subcommand>\\S+)(?<arguments>.*)");

    @Override
    public Command parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProductCommand.MESSAGE_USAGE));
        }

        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmed);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProductCommand.MESSAGE_USAGE));
        }

        String subcommand = matcher.group("subcommand").toLowerCase(Locale.ROOT);
        String arguments = matcher.group("arguments");

        return switch (subcommand) {
        case ProductAddCommand.SUBCOMMAND_WORD -> parseAdd(arguments);
        case ProductDeleteCommand.SUBCOMMAND_WORD -> parseDelete(arguments);
        case ProductListCommand.SUBCOMMAND_WORD -> parseList(arguments);
        default -> throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProductCommand.MESSAGE_USAGE));
        };
    }

    private Command parseAdd(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeProductArgs(args, ProductAddCommand.MESSAGE_USAGE);
        Product product = ParserUtil.parseProduct(argMultimap.getValue(PREFIX_PRODUCT).get());
        return new ProductAddCommand(product);
    }

    private Command parseDelete(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeProductArgs(args, ProductDeleteCommand.MESSAGE_USAGE);
        Product product = ParserUtil.parseProduct(argMultimap.getValue(PREFIX_PRODUCT).get());
        return new ProductDeleteCommand(product);
    }

    private Command parseList(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProductListCommand.MESSAGE_USAGE));
        }
        return new ProductListCommand();
    }

    private ArgumentMultimap tokenizeProductArgs(String args, String usageMessage) throws ParseException {
        String normalizedArgs = normalizeProductPrefix(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(normalizedArgs, PREFIX_PRODUCT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRODUCT);

        if (argMultimap.getValue(PREFIX_PRODUCT).isEmpty()
                || argMultimap.getValue(PREFIX_PRODUCT).get().trim().isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usageMessage));
        }

        return argMultimap;
    }

    private String normalizeProductPrefix(String args) {
        return args.replaceAll("(?i)(?<=^|\\s)(?:p/|product/)", PREFIX_PRODUCT.getPrefix());
    }
}
