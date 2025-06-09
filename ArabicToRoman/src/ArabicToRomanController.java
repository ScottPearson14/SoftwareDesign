import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * This class is the controller class that handles user interactions in the Arabic to Roman Converter GUI
 * This class  binds the view (TextFields) with the model (ArabicToRomanConverterModel) to update
 * conversions in real-time
 *
 * @version 1.0.0, 10, Oct 2024
 * @author Scott Pearson
 *
 */
public class ArabicToRomanController {
    // @FXML annotation preceding instance variables indicates the
    //variable’s name can be used in the FXML file that describes the app’s GUI
    @FXML
    /** TextField for entering and displaying Arabic numbers */
    private TextField arabicTextArea;
    @FXML
    /** TextField for entering and displaying Roman numerals */
    private TextField romanTextArea;
    /** instance of the model to handle conversions */
    private ArabicToRomanConverterModel model = new ArabicToRomanConverterModel();

    /**
     * This method initializes the controller class. It is automatically called by the FXMLLoader when
     * the associated XFML (ArabicToRoman.xfml) is loaded
     */
    @FXML
    public void initialize() {
        // Anonymous inner class for handling changes in the Arabic number TextField
        /**
         * Anonymous inner class listener that responds when users type into the Arabic number TextField.
         * When a new value is entered, the model is updated with the Arabic number, and the corresponding Roman numeral
         * is displayed in the Roman TextField. If the Arabic number is out of bounds [1, 3999], an error message
         * is displayed. It listens for real-time changes and automatically updates the other TextField
         *
         */
        arabicTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // check if new value in Arabic TextField is not empty
            if (!newValue.isEmpty()) {
                try {
                    // validate and convert the Arabic number using the model
                    int arabicNum = Integer.parseInt(newValue); // parse the input as an integer
                    model.setArabicNum(arabicNum); // update model with Arabic number
                    romanTextArea.setText(model.getRomanNumeral()); // display converted Roman numeral in Roman TextField
                } catch (NumberFormatException exception) {
                    // handle invalid input (non-numeric) in Arabic TextField
                    romanTextArea.setText("Invalid input");
                } catch (IllegalArgumentException exception) {
                    // Handle numbers outside of range [1, 3999] by showing error message
                    romanTextArea.setText("Out of range");
                }
            } else {
                romanTextArea.clear(); // clear RomanTextField if Arabic TextField is empty
            }
        });

        // Anonymous inner class for handling changes in the Roman numeral TextField
        /**
         * Anonymous inner class listener that responds when users type in the Roman numeral TextField.
         * When a new value is entered, the model is updated with the Roman numeral, and the corresponding Arabic number
         * is displayed in the Arabic TextField. If the Roman numeral is invalid, an error message will be displayed.
         * It listens for real-time changes and automatically updates the other TextField
         *
         */
        romanTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // check if the new value in the Roman TextField is not empty
            if (!newValue.isEmpty()) {
                try {
                    // validate and convert Roman numeral using the model
                    model.setRomanNumeral(newValue); // update model with the Roman numeral
                    // display converted Arabic number in Arabic TextField
                    arabicTextArea.setText(String.valueOf(model.getArabicNum()));
                } catch (IllegalArgumentException exception) {
                    // handle invalid Roman numerals by showing an error message in the Arabic TextField
                    arabicTextArea.setText("Invalid input"); // handle conversion errors
                }
            } else {
                arabicTextArea.clear(); // clear Arabic TextField if Roman TextField is empty
            }
        });
    }
}
