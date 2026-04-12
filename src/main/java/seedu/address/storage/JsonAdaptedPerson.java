package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    private final String name;
    private final String products;
    private final String location;
    private final String deadline;
    private final String contact;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("products") String products,
            @JsonProperty("location") String location, @JsonProperty("deadline") String deadline,
            @JsonProperty("contact") String contact) {
        this.name = name;
        this.products = products;
        this.location = location;
        this.deadline = deadline;
        this.contact = contact;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().getFullName();
        products = source.getProducts().getItems().isEmpty()
                ? null
                : source.getProducts().toString(); // store null when products are missing
        location = source.getLocation().getValue().isBlank()
                ? null
                : source.getLocation().getValue(); // store null when location is missing
        deadline = source.getDeadline().isEmpty()
                ? null
                : source.getDeadline().toString(); // store null when deadline is missing
        contact = source.getContact().getEntries().isEmpty()
                ? null
                : source.getContact().toString(); // store null when contact is missing
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        Products modelProducts = Products.empty(); // defaults when products are missing
        if (products != null && !products.isBlank()) {
            if (!Products.isValidProducts(products)) {
                throw new IllegalValueException(Products.MESSAGE_CONSTRAINTS);
            }
            modelProducts = new Products(products);
        }

        Location modelLocation = Location.empty(); // defaults when location is missing
        if (location != null) {
            if (!Location.isValidLocation(location)) {
                throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
            }
            modelLocation = new Location(location);
        }

        Deadline modelDeadline = Deadline.empty(); // defaults when deadline is missing
        if (deadline != null && !deadline.isBlank()) {
            if (!Deadline.isValidDeadline(deadline)) {
                throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
            }
            modelDeadline = new Deadline(deadline);
        }

        Contact modelContact = Contact.empty(); // defaults when contact is missing
        if (contact != null) {
            if (!Contact.isValidContact(contact)) {
                throw new IllegalValueException(Contact.MESSAGE_CONSTRAINTS);
            }
            modelContact = new Contact(contact);
        }

        return new Person(modelName, modelProducts, modelLocation, modelDeadline, modelContact);
    }

}
