package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label products;
    @FXML
    private Label locationValue;
    @FXML
    private Label deadline;
    @FXML
    private Label contact;
    @FXML
    private Label remark;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().getFullName());
        products.setText(formatProducts(person.getProducts())); // lists products with numbering
        locationValue.setText("Location: " + person.getLocation());
        deadline.setText("Deadline: " + person.getDeadline());
        contact.setText("Contact: " + person.getContact());
        remark.setText("Remark: " + person.getRemark());
    }

    private static String formatProducts(Products products) {
        if (products.getItems().isEmpty()) {
            return "Products:";
        }

        StringBuilder builder = new StringBuilder("Products:");
        int index = 1;
        for (String item : products.getItems()) {
            builder.append("\n- ").append(index).append(". ").append(item);
            index++;
        }
        return builder.toString();
    }
}
