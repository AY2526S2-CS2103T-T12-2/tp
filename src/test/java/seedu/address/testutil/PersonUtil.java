package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().getFullName() + " ");
        if (!person.getProducts().getItems().isEmpty()) {
            sb.append(PREFIX_PRODUCTS).append(person.getProducts()).append(" ");
        }
        if (!person.getLocation().getValue().isBlank()) {
            sb.append(PREFIX_LOCATION).append(person.getLocation()).append(" ");
        }
        if (!person.getDeadline().isEmpty()) {
            sb.append(PREFIX_DEADLINE).append(person.getDeadline()).append(" ");
        }
        if (!person.getContact().getEntries().isEmpty()) {
            sb.append(PREFIX_CONTACT).append(person.getContact()).append(" ");
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getFullName()).append(" "));
        descriptor.getProducts().ifPresent(products -> {
            if (!products.getItems().isEmpty()) {
                sb.append(PREFIX_PRODUCTS).append(products).append(" ");
            }
        });
        descriptor.getLocation().ifPresent(location -> {
            if (!location.getValue().isBlank()) {
                sb.append(PREFIX_LOCATION).append(location).append(" ");
            }
        });
        descriptor.getDeadline().ifPresent(deadline -> {
            if (!deadline.isEmpty()) {
                sb.append(PREFIX_DEADLINE).append(deadline).append(" ");
            }
        });
        descriptor.getContact().ifPresent(contact -> {
            if (!contact.getEntries().isEmpty()) {
                sb.append(PREFIX_CONTACT).append(contact).append(" ");
            }
        });
        return sb.toString();
    }
}
