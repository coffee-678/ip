package duncan.gui;

import duncan.Duncan;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI: turns what the user types into a call on
 * {@link Duncan} and shows both sides of the conversation as dialog boxes.
 */
public class MainWindow extends AnchorPane {
    /** How long the farewell stays on screen before the window closes. */
    private static final double EXIT_DELAY_IN_SECONDS = 1.5;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duncan duncan;

    /** Keeps the newest dialog box in view as the conversation grows. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Duncan instance this window talks to, and shows its
     * greeting as the first thing in the conversation.
     */
    public void setDuncan(Duncan d) {
        duncan = d;
        dialogContainer.getChildren().add(DialogBox.getDuncanDialog(duncan.getWelcome()));
    }

    /**
     * Creates two dialog boxes, one echoing the user's input and the other
     * holding Duncan's reply, and appends them to the dialog container.
     * Clears the input field afterwards, ready for the next command.
     */
    @FXML
    private void handleUserInput() {
        assert duncan != null;
        String input = userInput.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDuncanDialog(duncan.getResponse(input))
        );
        userInput.clear();

        if (duncan.isExit()) {
            closeAfterShowingGoodbye();
        }
    }

    /**
     * Stops the user typing anything more and closes the window a moment
     * later, so that the farewell is on screen long enough to be read.
     */
    private void closeAfterShowingGoodbye() {
        userInput.setDisable(true);
        sendButton.setDisable(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(EXIT_DELAY_IN_SECONDS));
        pause.setOnFinished(event -> Platform.exit());
        pause.play();
    }
}
