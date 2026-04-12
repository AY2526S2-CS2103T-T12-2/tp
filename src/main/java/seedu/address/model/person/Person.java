package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Customer in ClientEase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Contact contact;

    // Data fields
    private final Products products;
    private final Location location;
    private final Deadline deadline;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Products products, Location location, Deadline deadline, Contact contact) {
        requireAllNonNull(name, products, location, deadline, contact);
        this.name = name;
        this.products = products;
        this.location = location;
        this.deadline = deadline;
        this.contact = contact;
    }

    public Name getName() {
        return name;
    }

    public Products getProducts() {
        return products;
    }

    public Location getLocation() {
        return location;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Contact getContact() {
        return contact;
    }

    /**
     * Returns true if both customers have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && products.equals(otherPerson.products)
                && location.equals(otherPerson.location)
                && deadline.equals(otherPerson.deadline)
                && contact.equals(otherPerson.contact);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, products, location, deadline, contact);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("products", products)
                .add("location", location)
                .add("deadline", deadline)
                .add("contact", contact)
                .toString();
    }

}
