import java.util.Map;
import java.util.HashMap;

/**
 * The MorseCodeTranslator class is a class that translates plain text to Morse code and pain text to Morse code
 *
 * @version 1.0.0, 21, Nov 2024
 * @author Scott Pearson
 *
 */
public class MorseCodeTranslator {
    /** Hashmap to hold translations from plain text to Morse Code */
    private static final Map<Character, String> toMorse = new HashMap<>();
    /** Hashmap to hold translations from Morse Code to plain text */
    private static final Map<String, Character> toPlainText = new HashMap<>();
    // initialize mappings for Morse Code translations

    /**
     * Morse code static mappings for the alphabet and numerical values
     */
    static {
        // Morse Code mappings for the alphabet
        toMorse.put('A', ".-");
        toMorse.put('B', "-...");
        toMorse.put('C', "-.-.");
        toMorse.put('D', "-..");
        toMorse.put('E', ".");
        toMorse.put('F', "..-.");
        toMorse.put('G', "--.");
        toMorse.put('H', "....");
        toMorse.put('I', "..");
        toMorse.put('J', ".---");
        toMorse.put('K', "-.-");
        toMorse.put('L', ".-..");
        toMorse.put('M', "--");
        toMorse.put('N', "-.");
        toMorse.put('O', "---");
        toMorse.put('P', ".--.");
        toMorse.put('Q', "--.-");
        toMorse.put('R', ".-.");
        toMorse.put('S', "...");
        toMorse.put('T', "-");
        toMorse.put('U', "..-");
        toMorse.put('V', "...-");
        toMorse.put('W', ".--");
        toMorse.put('X', "-..-");
        toMorse.put('Y', "-.--");
        toMorse.put('Z', "--..");

        // More code mappings for numerical values 0-9
        toMorse.put('1', ".----");
        toMorse.put('2', "..---");
        toMorse.put('3', "...--");
        toMorse.put('4', "....-");
        toMorse.put('5', ".....");
        toMorse.put('6', "-....");
        toMorse.put('7', "--...");
        toMorse.put('8', "---..");
        toMorse.put('9', "----.");
        toMorse.put('0', "-----");

        // reverse the map for application decoding purposes so plain text is initialized properly
        for (Map.Entry<Character, String> entry : toMorse.entrySet()) {
            toPlainText.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * Method that converts a String input to Morse code
     *
     * @param input String that will be converted to Morse code
     * @return morseCode String of the message
     */
    public static String toMorse(String input) {
        StringBuilder morseCode = new StringBuilder();

        // Identify the prefix for the client message, so it is not also translated into Morse code -> "CLIENT>>>"
        int messageBeginningIndex = input.indexOf(">>>") + 3;
        String prefix = input.substring(0, messageBeginningIndex).trim(); // extract the prefix to the message
        String message = input.substring(messageBeginningIndex).trim(); // extract the actual message being sent
        morseCode.append(prefix).append(" ");// append the prefix as it is to the output

        // Iterate through each character of the message, converted to uppercase, as an array of characters
        for (char c : message.toUpperCase().toCharArray()) {
            if (toMorse.containsKey(c)) {
                // append Morse code for the character
                morseCode.append(toMorse.get(c)).append(" ");
            } else if (c == ' ') {
                morseCode.append("   "); // separator for words (3 spaces)
            } else {
                morseCode.append("? "); // placeholder for unknown character
            }
        }
        return morseCode.toString().trim(); // use trim to help with white space formatting for displaying message
    }

    /**
     * Method that converts Morse code to plain text
     *
     * @param morseCode String that will be converted to plain text
     * @return plainText String of the message
     */
    public static String toPlainText(String morseCode) {
        StringBuilder plainText = new StringBuilder();
        for (String x : morseCode.split(" ")) {
            if (x.equals("   ")) { // identifier separator for words (3 spaces)
                plainText.append(" ");  // separate words in plain text
            } else if (toPlainText.containsKey(x)) {
                plainText.append(toPlainText.get(x));
            } else {
                plainText.append("?"); // placeholder for unknown Morse code
            }
        }

        return plainText.toString(); // return plain text
    }
}
