import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The ArabicToRoman class represents a GUI implementation of an application that converts Arabic numbers
 * to Roman numerals, and vise versa, in real time.
 *
 * @version 1.0.0, 17, Oct 2024
 * @author Scott Pearson
 *
 */
public class ArabicToRoman extends Application {

    /**
     * this is the main driver method that creates an ArabicToRoman object and calls its start method to launch the GUI
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create a ArabicToRoman object and call its start method
        launch(args);
    }

    /**
     * This is the main entrypoint of my JavaFX program.
     * It is an overridden method that that constructs the scene graph of the GUI by loading the FXML file.
     * It also sets the title of the stage, attaches it to the stage, and displays the stage to the screen
     *
     * @param stage the stage for this JavaFX application. This is the main window that will be displayed
     * @throws Exception if there is a problem loading the FXML file or initializing JavaFX components
     */
    @Override
    public void start(Stage stage) throws Exception {
        // construct scene graph
        Parent root =
                FXMLLoader.load(getClass().getResource("ArabicToRoman.fxml"));

        Scene scene = new Scene(root); // attach the scene graph to scene
        stage.setTitle("Arabic to Roman Conversion"); // displayed in window's title bar
        stage.setScene(scene); // attach the scene to stage
        stage.show(); // displays the stage
    }
}
