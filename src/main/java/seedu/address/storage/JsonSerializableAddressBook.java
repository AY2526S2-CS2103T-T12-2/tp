package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "Products list contains duplicate product(s).";
    public static final String MESSAGE_PRODUCT_NOT_IN_CATALOG =
            "Persons list contains products that are not in the product catalog.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<String> products = new ArrayList<>();
    private final boolean hasProductsField;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("products") List<String> products) {
        this.persons.addAll(persons);
        this.hasProductsField = products != null;
        if (products != null) {
            this.products.addAll(products);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        products.addAll(source.getProductList().stream().map(Product::getName).collect(Collectors.toList()));
        hasProductsField = true;
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        addProducts(addressBook);
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        if (hasProductsField) {
            validateProductsInCatalog(addressBook);
        } else {
            mergeProductsFromPersons(addressBook);
        }
        return addressBook;
    }

    private void addProducts(AddressBook addressBook) throws IllegalValueException {
        Set<String> seen = new HashSet<>();
        for (String productName : products) {
            Product product = toModelProduct(productName);
            String key = product.getName().toLowerCase(Locale.ROOT);
            if (!seen.add(key)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PRODUCT);
            }
            addressBook.addProduct(product);
        }
    }

    private void mergeProductsFromPersons(AddressBook addressBook) throws IllegalValueException {
        for (Person person : addressBook.getPersonList()) {
            for (String item : person.getProducts().getItems()) {
                Product product = toModelProduct(item);
                if (!addressBook.hasProduct(product)) {
                    addressBook.addProduct(product);
                }
            }
        }
    }

    private void validateProductsInCatalog(AddressBook addressBook) throws IllegalValueException {
        for (Person person : addressBook.getPersonList()) {
            for (String item : person.getProducts().getItems()) {
                Product product = toModelProduct(item);
                if (!addressBook.hasProduct(product)) {
                    throw new IllegalValueException(MESSAGE_PRODUCT_NOT_IN_CATALOG);
                }
            }
        }
    }

    private Product toModelProduct(String productName) throws IllegalValueException {
        try {
            return new Product(productName);
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException(Product.MESSAGE_CONSTRAINTS);
        }
    }

}
