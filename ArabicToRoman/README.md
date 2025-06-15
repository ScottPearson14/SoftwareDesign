# S10_ArabicToRomanGUI
[Home](Home)

## Problem Statement
For this problem, a java class must be created to construct a GUI, using only two text areas, that will allow users to convert numbers in both directions between Roman numerals and Arabic numbers using JavaFX and Scene Builder. The program is capable of handling positive integers between 1 and 3999, using only the symbols I, V, X, L, C, D, and M. The interface must display only 2 JTextAreas: 1 for Arabic and the other for Roman numerals. The same JTextArea will be used for both input and output of either type of number. Typing in the Arabic text area will result in a new value being displayed in the Roman text area and vise versa. Input verification for invalid Roman numerals must also be implemented to ensure users are typing valid Roman numerals. Also, as users type in either text area, the equivalent number in the other text area should be updated as each key is pressed in real time. Conversions must comply with modern rules of Roman numerals. 


## Developer Documentation
The ArabicToRomanGUI is implemented in 3 classes and an FXML file. The 3 classes are a main driver class, a model class, and a controller class:

**ArabicToRoman.xfml**: this is an FXML file that is generated and auto-updated when changes are made using Scene Builder. The file enables users to define the application's user interface, keeping it separate from the application logic. 

**ArabicToRoman class**: this is the main driver program where the GUI is launched from. 
It consists of 2 methods:
- main: this method creates an ArabicToRoman object and calls its start method to launch the GUI
- start: this is an overridden method that constructs the scene graph of the GUI by loading the FXML file. It also sets the title of the stage, attaches it to the stage, and displays the stage to the screen

**ArabicToRomanConverterModel**: this is the model class for the GUI, and it represents the data for the program. The class implements methods that the controller class will call to perform data manipulation in response to view interactions.
The instance variables consist of:
- arabicNum: an int that represents an Arabic number to be converted
- romanNumeral: a string that represents a Roman numeral to be converted
- symbolValues: a static, final array of integers, related to Arabic values, that hold the numerical values corresponding to Roman numerals symbols
- symbols: a static, final array of Roman numeral symbols corresponding to numeric values

The class has a constructor that initializes the model with default values, and it also has getters and setters for arabicNum and romanNumeral. The arabicNum setter ensures the Arabic number entered is within the valid range (1 to 3999), and the romanNumeral setter ensures the entered Roman numeral follows the modern rules of Roman numerals. 
The other methods of this class consist of:
- convertRomanToArabic: this method converts an inputted Roman numeral string to an Arabic number
- convertArabicToRoman: this method converts an inputted Arabic number to a Roman numeral string
- isValid: this method validates whether provided Roman numeral is a valid string. It returns true if the Roman numeral string is valid and false otherwise.
 
**ArabicToRomanController**: this class is the controller for the GUI, and it handles user interactions in the ArabicToRoman converter GUI. The class binds view (TextFields) with the model (ArabicToRomanConverterModel) to update conversions in real-time. 
The instance variables of the class consist of:
- arabicTextArea: a TextField for entering and displaying Arabic numbers
- romanTextArea: a TextField for entering and displaying Roman numerals
- model: instance of the model class to handle conversions
There is only one method for this class, but the method has 2 anonymous inner classes within it:
- initialize: This method initializes the controller class. It is automatically called by the FXMLLoader when the associated XFML (ArabicToRoman.xfml) is loaded
The 2 anonymous inner are:
- arabicTextArea.textProperty().addListener((observable, oldValue, newValue): This is an anonymous inner class listener that responds when users type into the Arabic number TextField. this is an anonymous inner class for handling changes in the Arabic number TextField
- romanTextArea.textProperty().addListener((observable, oldValue, newValue): this is an anonymous inner class for handling changes in the Roman numeral TextField

## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S10_ArabicToRomanGUI/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam2/S10_ArabicToRomanGUI/doc/S10_ArabicToRomanGUI_UML.png?ref_type=heads)

## User Documentation
1. Compile and run the ArabicToRoman program
2. A graphical window will pop up. Click either the text area for the Roman numerals or Arabic numbers.
3. Begin to type in the selected text area. See the other text field update in real time
4. You can go back and type in the other text area to convert both ways if you wish

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S10_ArabicToRomanGUI/src?ref_type=heads)
