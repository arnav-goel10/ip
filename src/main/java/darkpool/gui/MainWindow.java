package darkpool.gui;

import darkpool.Darkpool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Darkpool darkpool;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/file.png"));
    private final Image darkpoolImage = new Image(this.getClass().getResourceAsStream("/images/deadpool.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Darkpool instance
     */
    public void setDarkpool(Darkpool d) {
        darkpool = d;
        dialogContainer.getChildren().addAll(DialogBox.getDarkpoolDialog(
                "it’s darkpool. what twisted reason dragged me into your misery?", darkpoolImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Darkpool's reply and then appends them
     * to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = darkpool.getResponse(input);
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                DialogBox.getDarkpoolDialog(response, darkpoolImage));
        userInput.clear();

    }
}
