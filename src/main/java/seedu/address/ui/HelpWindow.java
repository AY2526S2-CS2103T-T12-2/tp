package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ProductCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-t12-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE =
            "📘 COMMAND SUMMARY\n\n"

                    + AddCommand.COMMAND_WORD
                    + " [fields...]\n"
                    + "    Add a customer\n\n"

                    + EditCommand.COMMAND_WORD
                    + " INDEX [fields...]\n"
                    + "    Edit a customer\n\n"

                    + DeleteCommand.COMMAND_WORD
                    + " INDEX or NAME (alias: "
                    + DeleteCommand.COMMAND_ALIAS + ")\n"
                    + "    Delete a customer\n\n"

                    + FindCommand.COMMAND_WORD
                    + " [fields for find...]\n"
                    + "    Find customers\n\n"

                    + ListCommand.COMMAND_WORD + " (alias: "
                    + ListCommand.COMMAND_ALIAS + ")\n"
                    + "    List all customers\n\n"

                    + ProductCommand.COMMAND_WORD
                    + " add product/NAME (or p/NAME)\n"
                    + "    Add a product to the catalog\n\n"

                    + ProductCommand.COMMAND_WORD
                    + " delete product/NAME (or p/NAME)\n"
                    + "    Delete a product from the catalog\n\n"

                    + ProductCommand.COMMAND_WORD
                    + " list\n"
                    + "    List all products\n\n"

                    + ClearCommand.COMMAND_WORD + "\n"
                    + "    Clear all customers\n\n"

                    + HelpCommand.COMMAND_WORD + "\n"
                    + "    Show this help message\n\n"

                    + ExitCommand.COMMAND_WORD + "\n"
                    + "    Exit the application\n\n"

                    + "*[fields...] = name/NAME products/PRODUCTS location/LOCATION deadline/DATE contact/CONTACT "
                    + "(choose at least one field)\n\n"

                    + "*[fields for find...] = name/NAME product/PRODUCT location/LOCATION contact/CONTACT "
                    + "(each can repeat multiple times)\n\n"

                    + "Refer to the user guide:\n"
                    + USERGUIDE_URL
                    + "    ";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Text helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
