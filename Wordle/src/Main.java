/**
 * The Main class is the driver class for the Wordle game.
 * It creates an instance of the Wordle game, and sets the title and size of the game
 *
 * @version 1.0.0, 18, Sept 2024
 * @author Scott Pearson
 *
 */
public class Main {
    /**
     * Main method that creates an instance of the WordleGame to launch the game
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // create new instance of Wordle game
        WordleGame wordleGame = new WordleGame();
        // set the title of game and size
        wordleGame.setTitle("Wordle Game");
        wordleGame.setSize(600, 600);

    }

}
