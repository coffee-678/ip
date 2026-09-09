package duncan.gui;

import java.io.IOException;

import duncan.Duncan;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duncan using FXML. Builds the window from
 * {@code /view/MainWindow.fxml} and hands the controller the {@link Duncan}
 * instance that does the actual work.
 */
public class Main extends Application {
    private final Duncan duncan = new Duncan();

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Duncan");
            fxmlLoader.<MainWindow>getController().setDuncan(duncan);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
