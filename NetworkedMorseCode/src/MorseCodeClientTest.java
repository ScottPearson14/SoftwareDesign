import javax.swing.*;

/**
 * The MorseCodeClientTest is a class that is used to run a client.
 * The client is then connected to the server. When another client is run and connected to the server,
 * the two clients will be able to communicate to each other via Morse Code
 *
 * @version 1.0.0, 19, Nov 2024
 * @author Scott Pearson
 */
public class MorseCodeClientTest {
    /**
     * The main method acts as the driver program for clients. It declares client applications and connects them to the
     * host, so they can connect to the server.
     *
     * @param args Command-line arguments (none)
     */
    public static void main(String[] args) {
        MorseCodeClient application; // declare client application

        // if no command line args
        if (args.length == 0)
            application = new MorseCodeClient("127.0.0.1"); // connect to localhost
        else
            application = new MorseCodeClient(args[0]); // use args to connect

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient(); // run client application
    }
}