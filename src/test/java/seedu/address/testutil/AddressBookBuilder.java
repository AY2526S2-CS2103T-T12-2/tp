package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.product.Product;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Product} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withProduct(Product product) {
        addressBook.addProduct(product);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
