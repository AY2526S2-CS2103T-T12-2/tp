package seedu.address.testutil;

import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;
import seedu.address.model.person.Remark;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PRODUCTS = "Chocolate cake";
    public static final String DEFAULT_LOCATION = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CONTACT = "85355255";

    private Name name;
    private Products products;
    private Location location;
    private Deadline deadline;
    private Contact contact;
    private Remark remark;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        products = new Products(DEFAULT_PRODUCTS);
        location = new Location(DEFAULT_LOCATION);
        deadline = Deadline.empty();
        contact = new Contact(DEFAULT_CONTACT);
        remark = Remark.empty();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        products = personToCopy.getProducts();
        location = personToCopy.getLocation();
        deadline = personToCopy.getDeadline();
        contact = personToCopy.getContact();
        remark = personToCopy.getRemark();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Products} of the {@code Person} that we are building.
     */
    public PersonBuilder withProducts(String products) {
        this.products = new Products(products);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Person} that we are building.
     */
    public PersonBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Person} that we are building.
     */
    public PersonBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Person} that we are building.
     */
    public PersonBuilder withContact(String contact) {
        this.contact = new Contact(contact);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Person build() {
        return new Person(name, products, location, deadline, contact, remark);
    }

}
