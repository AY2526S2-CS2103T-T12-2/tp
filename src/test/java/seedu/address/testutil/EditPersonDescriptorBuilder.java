package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        if (!person.getProducts().getItems().isEmpty()) {
            descriptor.setProducts(person.getProducts());
        }
        if (!person.getLocation().getValue().isBlank()) {
            descriptor.setLocation(person.getLocation());
        }
        if (!person.getDeadline().isEmpty()) {
            descriptor.setDeadline(person.getDeadline());
        }
        if (!person.getContact().getEntries().isEmpty()) {
            descriptor.setContact(person.getContact());
        }
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Products} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withProducts(String products) {
        descriptor.setProducts(new Products(products));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withContact(String contact) {
        descriptor.setContact(new Contact(contact));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
