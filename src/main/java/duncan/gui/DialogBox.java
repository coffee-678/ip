package duncan.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents one turn of the conversation: a box holding what one speaker
 * said. The user's boxes sit on the right, Duncan's on the left, so the two
 * sides of the conversation can be told apart.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;

    private DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
    }

    /** Returns a dialog box for something the user typed, aligned to the right. */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text);
    }

    /** Returns a dialog box for something Duncan said, aligned to the left. */
    public static DialogBox getDuncanDialog(String text) {
        DialogBox db = new DialogBox(text);
        db.setAlignment(Pos.TOP_LEFT);
        return db;
    }
}
