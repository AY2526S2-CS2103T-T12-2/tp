package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Products;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        assert person != null : "Person should not be null";
        assert displayedIndex > 0 : "Displayed index should be positive";

        this.person = person;

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().getFullName());
        products.setText(formatProducts(person.getProducts()));
        locationValue.setText("Location: " + person.getLocation());
        deadline.setText("Deadline: " + person.getDeadline());
        contact.setText("Contact: " + person.getContact());

        applyPriorityStyle(person.getProducts().getTotalQuantity());
    }

    /**
     * Applies a priority tag label and card background color based on total product quantity.
     * No styling is applied if the customer has no products.
     */
    private void applyPriorityStyle(int totalQty) {
        if (totalQty == 0) {
            return;
        }
        String styleClass = totalQty <= 5 ? "tag-green" : totalQty <= 10 ? "tag-yellow" : "tag-red";
        String labelText = totalQty <= 5 ? "LOW" : totalQty <= 10 ? "MEDIUM" : "HIGH";

        Label priorityTag = new Label(labelText);
        priorityTag.getStyleClass().add("priority-tag-base");
        tags.getChildren().add(priorityTag);
        cardPane.getStyleClass().add(styleClass);
    }

    private static String formatProducts(Products products) {
        if (products.getItemMap().isEmpty()) {
            return "Products: None";
        }

        StringBuilder sb = new StringBuilder("Products:");
        products.getItemMap().forEach((name, qty) ->
                sb.append("\n- ").append(name).append(" (x").append(qty).append(")")
        );

        return sb.toString();
    }
}
