import javax.swing.*;

/**
 * The MorseCodeServerTest is a class that is used to run a server.
 * When run, the server is created, and clients can connect to it, so they can communicate via Morse Code
 *
 */
public class MorseCodeServerTest {
    /**
     * The main method acts as the driver program for a server. It creates a server.
     *
     * @param args Command-line arguments (none)
     */
    public static void main(String[] args) {
        MorseCodeServer application = new MorseCodeServer(); // create server
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runServer(); // run server application
    }
}