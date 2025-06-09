import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The MorseCodeServer class is a class that represents a server that will create a connection with 2 clients to allow
 * them to be able to communicate with each other via Morse Code
 *
 * This program has been base code from textbook example fig28_03_061 Server class. The code has been altered and with my own
 * implementations and changes to work for my Morse Code program
 *
 * @version 1.0.0, 19, Nov 2024
 * @author Scott Pearson
 */
public class MorseCodeServer extends JFrame {
    /** JTextArea where information is displayed to the user */
    private JTextArea displayArea;
    /** ServerSocket for server*/
    private ServerSocket server;
    /** Array to manage connections for 2 clients */
    private Communicator[] clients = new Communicator[2];
    /** ExecutorService thread pool for managing client handlers */
    private ExecutorService threadPool = Executors.newFixedThreadPool(2);

    /**
     * Constructor that sets up the GUI
     *
     */
    public MorseCodeServer() {
        super("Morse Code Server"); // set the title

        displayArea = new JTextArea(); // create displayArea
        displayArea.setEditable(false); // set displayArea to not be editable

        // create a JScrollPane, add the displayArea, and add to middle of GUI
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(450, 300); // set size of window
        setVisible(true); // show window
    }

    /**
     * Method that sets up and runs the server
     *
     */
    public void runServer() {
        try // set up server to receive connections and process connections
        {
            server = new ServerSocket(23555, 100); // create ServerSocket and use my 1st port #
            displayMessage("Server has started. Waiting for connections...\n");

            // while loop for while the server is running
            for (int i = 0; i < 2; i++) { // only accept connections from 2 clients so they can communicate
                try {
                    Socket connection = server.accept(); // accept a connections
                    displayMessage("Connection received from " + connection.getInetAddress().getHostName() + "\n");

                    Communicator clientHandler = new Communicator(connection, i); //
                    clients[i] = clientHandler; // store the client handler in clients array

                    threadPool.execute(clientHandler); // start the clientHandler by adding it to the thread pool
                } catch (IOException ioException) {
                    displayMessage("\nError accepting client connection");
                    ioException.printStackTrace();
                }
            }

            displayMessage("Both clients successfully connected. Communication can now commence.\n");

        } catch (IOException ioException) {
            displayMessage("Error setting up the server\n");
            ioException.printStackTrace();
        }
    }
    // private inner class Communicator manage each Communicator as a runnable

    /**
     * The Communicator class is a private, anonymous inner class that manages each Communicator as runnable
     *
     */
    private class Communicator implements Runnable {
        /** Socket that acts as connection to the client */
        private Socket connection; // connection to the client
        /** int that tracks which communicator is talking */
        private int communicatorNum; // tracks which communicator is speaking
        /** ObjectInputStream input from the client */
        private ObjectInputStream input; // input stream from the client
        /** ObjectOutputStream output from the client */
        private ObjectOutputStream output; // output stream from the client

        // set up Communicator thread
        /**
         * Constructor to initialize client handler
         *
         * @param socket the socket for the client
         * @param number tracks the number of the communicator
         */
        public Communicator(Socket socket, int number) {
            communicatorNum = number;
            connection = socket; // store the socket for the client
        }
        // control thread's execution

        /**
         * Overridden run method that processes messages from clients
         *
         */
        @Override
        public void run() {
            // process massages from client
            try {
                setupSteams(); // initialize I/O streams
                processConnection(); // handle communication with the client

            } catch (EOFException eofException) {
                displayMessage("Client " + communicatorNum + " terminated connection.\n");
            } catch (IOException ioException) {
                displayMessage("Error with Client " + communicatorNum + " connection.\n");
                ioException.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        /**
         * Method to set up I/O streams for the client
         *
         * @throws IOException if stream setup fails
         */
        private void setupSteams() throws IOException {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush(); // flush the output buffer

            // set up an input stream for objects
            input = new ObjectInputStream(connection.getInputStream());
            displayMessage("Streams properly set up for Client " + communicatorNum + "\n");
        }

        /**
         * Method to process server connection with the client
         *
         * @throws IOException if connection to client is unsuccessful
         */
        private void processConnection() throws IOException {
            String message = "Connection successful";
            sendToClient(message); // send connection successful message to the client

            // enable enterField so server user can send messages
            setTextFieldEditable(true);

            do // process messages sent from client
            {
                try // read message and display it
                {
                    message = (String) input.readObject(); // read new message
                    displayMessage("Client " + communicatorNum + " sent: " + message + "\n"); // display message

                    // Translate message to Morse code
                    String morseCode = MorseCodeTranslator.toMorse(message);
                    displayMessage("Translated to Morse code: " + morseCode + "\n");

                    // forward the Morse code message to the other client
                    String clientFormattedMessage = "Client " + communicatorNum + " sent: " + morseCode;
                            //communicatorNum + morseCode;
                    sendToOtherClient(clientFormattedMessage);

                } catch (ClassNotFoundException classNotFoundException) {
                    displayMessage("\nUnknown object type received");
                }

            } while (!message.equals("CLIENT>>> TERMINATE")); // break loop connection if user enters TERMINATE
            // ends client connection to server once entered
        }

        /**
         * Method that sends messages to client
         *
         * @param message a String representing the message to be sent
         */
        private void sendToClient(String message) {
            try {
                output.writeObject(message);
                output.flush(); // flush output buffer
            } catch (IOException ioException) {
                displayMessage("Error sending message to Client " + communicatorNum + "\n");
            }
        }

        /**
         * Method to allow a client to send messages to the other client
         *
         * @param message a String that represents the message to be sent
         */
        private void sendToOtherClient(String message) {
            int receiverNum = (communicatorNum + 1) % 2; // calculate the communication number of the other client
            // if there is in fact a message
            if (clients[receiverNum] != null) {
                try {
                    clients[receiverNum].output.writeObject(message);
                    clients[receiverNum].output.flush(); // flush output buffer
                } catch (IOException ioException) {
                    displayMessage("Error forwarding message to Client " + receiverNum + "\n");
                }
            }
        }
        /**
         * Method that closes streams and sockets
         *
         */
        private void closeConnection() {
            displayMessage("\nTerminating connection\n");
            setTextFieldEditable(false); // disable enterField

            try {
                output.close(); // close output stream
                input.close(); // close input stream
                connection.close(); // close socket
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    /**
     * Method that manipulates displayArea in the event-dispatch thread
     *
     * @param messageToDisplay String that is the message to be shown in displayArea
     */
    private void displayMessage(final String messageToDisplay) {
        /**
         * anonymous inner class that updates the displayArea
         */
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // updates displayArea
                    {
                        displayArea.append(messageToDisplay); // append message
                    }
                }
        );
    }

    /**
     * Method that manipulates displayArea in the event-dispatch thread
     *
     * @param editable boolean flag that represents if displayArea editable
     *                 true means you can edit, false means you can't edit
     */
    private void setTextFieldEditable(final boolean editable) {
        /**
         * anonymous inner class that sets the state of whether displayArea is editable or not
         */
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // sets displayArea's editability
                    {
                        displayArea.setEditable(editable);
                    }
                }
        );
    }
}
