package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCTS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCTS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withProducts("Chocolate Cake").withLocation("123, Jurong West Ave 6, #08-111")
            .withDeadline("2026-03-10").withContact("94351253").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withProducts("Vanilla Cake").withLocation("311, Clementi Ave 2, #02-25")
            .withDeadline("2026-03-12").withContact("98765432").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withProducts("Muffin")
            .withLocation("wall street").withDeadline("2026-04-01").withContact("95352563").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withProducts("Brownie")
            .withLocation("10th street").withDeadline("2026-04-02").withContact("87652533").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withProducts("Cookie")
            .withLocation("michegan ave").withDeadline("2026-04-03").withContact("94822240").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withProducts("Chocolate Cake")
            .withLocation("little tokyo").withDeadline("2026-04-04").withContact("94824270").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withProducts("Vanilla Cake")
            .withLocation("4th street").withDeadline("2026-04-05").withContact("94824420").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withProducts("Brownie")
            .withLocation("little india").withDeadline("2026-04-06").withContact("84824240").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withProducts("Muffin")
            .withLocation("chicago ave").withDeadline("2026-04-07").withContact("84821310").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withProducts(VALID_PRODUCTS_AMY)
            .withLocation(VALID_LOCATION_AMY).withDeadline(VALID_DEADLINE_AMY).withContact(VALID_CONTACT_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withProducts(VALID_PRODUCTS_BOB)
            .withLocation(VALID_LOCATION_BOB).withDeadline(VALID_DEADLINE_BOB).withContact(VALID_CONTACT_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Product product : getTypicalProducts()) {
            ab.addProduct(product);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Product> getTypicalProducts() {
        return new ArrayList<>(List.of(
                new Product("Brownie"),
                new Product("Chocolate Cake"),
                new Product("Cookie"),
                new Product("Muffin"),
                new Product("Vanilla Cake")
        ));
    }
}
