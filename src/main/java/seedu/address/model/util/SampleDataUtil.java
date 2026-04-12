package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;
import seedu.address.model.product.Product;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Products("Chocolate Cake"),
                new Location("Blk 30 Geylang Street 29, #06-40"),
                new Deadline("2030-12-31 23:59"), new Contact("87438807")),
            new Person(new Name("Bernice Yu"), new Products("Vanilla Cake:3, Muffin:3"),
                new Location("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Deadline("2026-03-10"), new Contact("berniceyu@example.com")),
            new Person(new Name("Charlotte Oliveiro"), new Products("Brownie:12"),
                new Location("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Deadline("2026-04-01 09:00"), new Contact("93210283")),
            new Person(new Name("David Li"), new Products("Cookie"),
                new Location("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Deadline("2026-02-28"), new Contact("lidavid@example.com")),
            new Person(new Name("Irfan Ibrahim"), new Products("Muffin: 15"),
                new Location("Blk 47 Tampines Street 20, #17-35"),
                new Deadline("2026-05-15"), new Contact("92492021")),
            new Person(new Name("Roy Balakrishnan"), new Products("Chocolate Cake:1, Cookie:9"),
                new Location("Blk 45 Aljunied Street 85, #11-31"),
                new Deadline("2026-06-20"), new Contact("royb@example.com"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Product product : getSampleProducts()) {
            sampleAb.addProduct(product);
        }
        return sampleAb;
    }

    public static Product[] getSampleProducts() {
        return new Product[] {
            new Product("Brownie"),
            new Product("Chocolate Cake"),
            new Product("Cookie"),
            new Product("Muffin"),
            new Product("Vanilla Cake")
        };
    }

}
