/**
 * this is the model for the GUI that represents the data for the program. It implements methods that
 * the controller will call to perform data manipulation in response to view interactions
 *
 * @version 1.0.0, 23, Oct 2024
 * @author Scott Peasron
 */
public class ArabicToRomanConverterModel {
    /** Arabic number to be converted */
    private int arabicNum;
    /** Roman numeral to be converted */
    private String romanNumeral;
    /** array of the Arabic values corresponding to Roman numeral symbols */
    // declared final because the variable should not change and static to have one copy of the variable in memory
    // regardless of class instances
    private static final int[] symbolValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    /** array of Roman numeral symbols corresponding to numeric values */
    // declared final because the variable should not change and static to have one copy of the variable in memory
    // regardless of class instances
    private static final String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};


    /**
     * Constructor for the model
     * Initializes model with default values
     */
    public ArabicToRomanConverterModel() {
        this.arabicNum = 0;
        this.romanNumeral = "";
    }

    /**
     * Gets the Arabic number corresponding to the Roman numeral
     *
     * @return the converted Arabic number
     */
    public int getArabicNum() {
        return this.arabicNum;
    }

    /**
     * Gets the Roman numeral corresponding to the Arabic number
     *
     * @return the converted Roman numeral
     */
    public String getRomanNumeral() {
        return this.romanNumeral;
    }

    /**
     * Sets the Arabic number and validates the input.
     * If valid, it updates the corresponding Roman numeral
     *
     * @param arabicNum arabicNum the Arabic number to be converted to a Roman numeral
     * @throws IllegalArgumentException if the Arabic number is out the valid range (1 to 3999)
     */
    public void setArabicNum(int arabicNum) {
        // check to see if input number is valid
        if (arabicNum < 1 || arabicNum > 3999) {
            throw new IllegalArgumentException("Invalid Arabic number (must be between 1 & 3900)");
        }
        this.arabicNum = arabicNum;
        this.romanNumeral = convertArabicToRoman(arabicNum);
    }

    /**
     * Sets the Roman numeral and validates the input.
     * If valid, it updates the corresponding Arabic number
     *
     * @param romanNumeral the Roman numeral to be converted to an Arabic number
     * @throws IllegalArgumentException if the Roman numeral is invalid
     */
    public void setRomanNumeral(String romanNumeral) {
        this.romanNumeral = romanNumeral.toUpperCase(); // convert the input Roman numeral to all uppercase
        // check to see if input string is valid
        if (!isValid(romanNumeral)) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.arabicNum = convertRomanToArabic(romanNumeral);
    }

    /**
     * Method that converts a Roman numeral string to an Arabic number
     *
     * @param romanString the Roman numeral string to be converted
     * @return the corresponding Arabic number
     */
    private int convertRomanToArabic(String romanString) {
        romanString = romanString.toUpperCase(); // convert the string to all uppercase
        int arabicNum = 0; // variable to store the resulting Arabic value
        int current = 0; // variable to track the position in the Roman numeral string

        // loop through the Roman numeral symbols
        for (int i = 0; i < symbols.length; i++) {
            // while current portion of the Roman numeral string matches current Roman symbol
            while (romanString.startsWith(symbols[i], current)) {
                arabicNum += symbolValues[i]; // add corresponding Arabic value to total sum
                // move current forward by length of matched Roman symbol
                // this ensures next comparison looks at remaining part of string
                current += symbols[i].length();
            }
        }
        return arabicNum; // return final calculated Arabic number
    }

    /**
     * Method that converts an Arabic number to a Roman numeral string
     *
     * @param numArabic the Arabic number to be converted
     * @return the corresponding Roman numeral string
     */
    private String convertArabicToRoman(int numArabic) {
        // create an instance of a StringBuilder that will be used to build our Roman numeral
        StringBuilder romanString = new StringBuilder();

        // loop through the symbolValues
        for (int i = 0; i < symbolValues.length; i++) {
            // while the Arabic number is greater to the current value i in the symbolValues
            while (numArabic >= symbolValues[i]) {
                numArabic -= symbolValues[i]; // subtract the current number i from the Arabic number
                romanString.append(symbols[i]); // append the symbol correlating to the value i to romanString
            }
        }
        return romanString.toString(); // return the .toString value of the romanString
    }

    /**
     * Validates whether provided Roman numeral is a valid string
     *
     * @param romanString the Roman numeral string to validate
     * @return true if the Roman numeral is valid, false otherwise
     */
    public static boolean isValid(String romanString) {
        // check to see if string is null or empty
        if (romanString == null || romanString.isEmpty()) {
            return false;
        }
        // define several different regular expression patterns as strings for each Roman numeral range
        // I am defining them this way for readability and clarity rather than just in a one line regular expression
        // "?": quantifier modifying amount of times preceding group of characters can appear and makes preceding
        // groups optional as well

        String thousands = "M{0,3}"; // matches 0 to 3 occurrences of M (1,000-3000)
        String hundreds = "(CM|CD|D?C{0,3})"; // matches 900 (CM), 400 (CD), 500-800 (D followed by 0-3 Cs), or 0-300 (C)
        String tens = "(XC|XL|L?X{0,3})"; // matches 90 (XC), 40 (XL), 50-80 (L followed by 0-3 Xs), 0-30 (X)
        String ones = "(IX|IV|V?I{0,3})"; // matches 9 (IX), 4 (IV), 5-8 (V followed by 0-3 Is), or 0-3 (I)

        // combine string patterns to form the complete regular expression
        // "^": start of string anchor
        // "$": end of string anchor
        String romanRegExpression = "^" + thousands + hundreds + tens + ones + "$";

        return romanString.matches(romanRegExpression); // check to see if input string matches Roman numeral patterns
        // returns true if string has valid subtractive notation patterns and false otherwise
    }

}
