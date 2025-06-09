import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The WordleGame class represents GUI implementation of Wordle using Java Swing.
 * This class allows 6 attempts to guess a 5-letter word from a list of words
 *
 */
public class WordleGame extends JFrame {
    /** the correct word user needs to guess */
    private String correctGuess;
    /** attempts user has remaining to guess the correct word */
    private int attemptsRemaining = 6; // users only get 6 guesses
    /** List of possible 5-letter words for the game */
    private List<String> wordList = Arrays.asList("APPLE", "BRAVE", "FLUTE", "GRAPE", "STAIR",
            "SUGAR", "DREAM", "RIVER", "EXIST", "CLOWN", "SPARK", "WORLD", "SMILE", "BLAZE",
            "GREEN", "DRIFT", "POWER", "EARTH", "MOUTH", "LIGHT", "CROWN", "PIANO", "FRUIT",
            "BREAD", "LAUGH");
    /** input field for users to type their guesses */
    private JTextField guessField;
    /** panel that holds the grid where user guesses are displayed */
    private JPanel gridGuessPanel;
    /** list of labels representing each letter box within the guess grid  */
    private List<JLabel> letterBoxes;
    /** button that triggers the process of checking the user's guess with the correct word */
    private JButton solveButton;

    /**
     * Constructor that initializes the Wordle game GUI. It sets up the layout, creates the word grid,
     * creates the input components, and sets the game in play
     */
    public WordleGame() {
        // Randomly select what the correct word is
        correctGuess = wordList.get(new Random().nextInt(wordList.size()));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // code for initializing the GUI

        // access content pane and set its layout
        Container container = getContentPane();
        container.setLayout(new BorderLayout()); // main layout of the game

        // create main grid layout for guessing window in the center
        gridGuessPanel = new JPanel(new GridLayout(attemptsRemaining, 5, 5, 5)); // 6 attempts, 5-letter words
        letterBoxes = new ArrayList<>();

        // setting up format for guess panel
        for (int i = 0; i < attemptsRemaining * 5; i++) { // 6 rows of 5 letters each
            JLabel letterBox = new JLabel("", SwingConstants.CENTER);
            letterBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            letterBox.setOpaque(true); // ensure background color is visible
            letterBox.setBackground(Color.LIGHT_GRAY);
            gridGuessPanel.add(letterBox);
            letterBoxes.add(letterBox);
        }
        container.add(gridGuessPanel, BorderLayout.CENTER); // add panel to container in center

        //  create a panel to hold user input field on the left and solve button on the right
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // create panel for user input
        JPanel userInputPanel = new JPanel(new GridLayout(1,2,5,5));
        guessField = new JTextField(5);
        userInputPanel.add(new JLabel("Enter Guess:"));
        userInputPanel.add(guessField);
        bottomPanel.add(userInputPanel, BorderLayout.WEST); // place user input to the left side of bottom panel

        // create panel for "Solve" button
        solveButton = new JButton("Solve");
        bottomPanel.add(solveButton, BorderLayout.EAST); // place solve button to right side of bottom panel

        // add bottom panel to main container in the bottom
        container.add(bottomPanel, BorderLayout.SOUTH);

        // set up action listener for SolveButton using anonymous inner class that implements ActionListener interface
        solveButton.addActionListener(new ActionListener() { // adds an actionListener to the solveButton
                                                             // actionListener "listens" for button to be clicked

            /**
             * This method is triggered when the "Solve" button is pressed
             * It calls the method to process and check the user's guess
             *
             * @param e ActionEvent event to be processed by clicking "Solve"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuessAndCheck(); // check the guess when user preses "Solve"
            }
        });
        setVisible(true); // Make the frame visible

    }

    /**
     *
     * Method to process user's guess. It compares the guess with the correct word and provides feedback based on
     * the location of where the letters in the guess align with the location of the letters in the correct word.
     * If the guess is correct, the game ends. If it is wrong, the number of user attempts is decremented
     *
     */
    private void processGuessAndCheck() {
        String guess = guessField.getText().toUpperCase(); // converts input to upper case if not already so
        // check if input is exactly 5 letters and contains only letters by using a regular expression
        if (guess.length() == 5 && guess.matches("[A-Z]+")) {
            updateGrid(guess); // update the guess gird if input is valid
            if (guess.equals(correctGuess)) {
                JOptionPane.showMessageDialog(this, "Congratulations! You guessed the correct word!");
                solveButton.setEnabled(false); // disable the solve button since game is over

            } else {
                attemptsRemaining--; // decrement attempts if the guess is wrong
                if (attemptsRemaining == 0) {
                    JOptionPane.showMessageDialog(this, "No attempts left! The correct word was: " + correctGuess);
                    solveButton.setEnabled(false); // end game when no attempts are left
                }
            }
            guessField.setText(""); // clear the input field for next user guess
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a 5-letter word.");
        }
    }

    /**
     * Method to update the guess grid with feedback based on the user guess. It marks the letters based on the
     * following rules:
     * 1. Green -> guessed letter appears in correct word in correct location
     * 2. Yellow -> guessed letter appears in correct word in wrong location
     * 3. Gray -> guessed letter does not appear in the correct word
     *
     * @param guess The user's guessed word
     */
    private void updateGrid(String guess) {
        // create 2 arrays to track the occurrences of letters in the correct word and guess
        int[] correctLetterCounts = new int[26]; // track counts of letters in the correct word (from A-Z)
        int[] guessedLetterMark = new int[26]; // track counts of guessed letters

        // have an initial pass to get the counts of the letters in the correct word
        for (int i = 0; i < correctGuess.length(); i++) {
            char correctChar = correctGuess.charAt(i); // get the char of correct word
            correctLetterCounts[correctChar - 'A']++; // increment count for each letter in correct word by subtracting
            // the ASCII value of the correctChar from the ASCII value of 'A' -> add this number to correctLetterCounts
        }

        // pass 1: mark green letters and record the counts of the guessed letters
        for (int i = 0; i < 5; i++) {
            JLabel label = letterBoxes.get(5 * (6 - attemptsRemaining) + i); // get label for each letter in the grid
            char guessedChar = guess.charAt(i); // get the char at current position in the guess

            // if letter is correct and in correct position, mark it green
            if (guessedChar == correctGuess.charAt(i)) {
                label.setText(String.valueOf(guessedChar));
                label.setBackground(Color.GREEN); // correct letter in correct location
                correctLetterCounts[guessedChar - 'A']--; // uses up available occurrences of this given letter
            } else {
                // initially set other letters gray (some will change yellow later)
                label.setText(String.valueOf(guessedChar));
                label.setBackground(Color.GRAY); // default to gray for now
            }
            guessedLetterMark[guessedChar - 'A']++; // increment count of guessed letters
        }

        // pass 2: mark yellow letters (correct letter in wrong location)
        for (int i = 0; i < 5; i++) {
            JLabel label = letterBoxes.get(5 * (6 - attemptsRemaining) + i); // get label for each letter
            char guessedChar = guess.charAt(i); // get the current char in the guess

            // only process letters that are not already marked green
            if (label.getBackground() != Color.GREEN) {
                // if guessed letter is in correct word and still has available occurrence in word
                if (correctGuess.contains(String.valueOf(guessedChar)) && correctLetterCounts[guessedChar - 'A'] > 0) {
                    label.setBackground(Color.YELLOW); // correct letter in wrong location
                    correctLetterCounts[guessedChar - 'A']--; // uses up the occurrence for this give letter
                } else {
                    label.setBackground(Color.GRAY); // letter not in word or no remaining occurrences of letter
                }
            }
        }

    }


}