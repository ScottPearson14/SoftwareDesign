import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The MorseCodeClient class is a class that represents a client that will be used to communicate Morse Code with another client.
 *
 * This program has been base code from textbook example fig28_03_061 Client class. The code has been altered and with my own
 * implementations and changes to work for my Morse Code program
 *
 * @version 1.0.0, 19, Nov 2024
 * @author Scott Pearson
 */
public class MorseCodeClient extends JFrame {
    /** JTextField where users enter information */
    private JTextField enterField;
    /** JTextArea where information is displayed to the user */
    private JTextArea displayArea;
    /** ObjectOutputStream that acts as an output stream to the server */
    private ObjectOutputStream output;
    /** ObjectInputStream that acts as an input stream from the server */
    private ObjectInputStream input;
    /** String that is a message from the server */
    private String message = "";
    /** String that is a host server for this Morse Code application */
    private String chatServer;
    /** Socket that allows a connection to the server */
    private Socket client;

    /**
     * Constructor that initializes chatServer and sets up the GUI
     *
     * @param host the hostname/IP address of the server to connect to
     */
    public MorseCodeClient(String host) {
        super("Morse Code Client"); // set the title

        chatServer = host; // set server to which this client connects

        enterField = new JTextField(); // create enterField
        enterField.setEditable(false); // set enterField to not be editable
        /**
         * anonymous inner class for an ActionListener to send a message to the server
         *
         */
        enterField.addActionListener(
                new ActionListener() {
                    // send message to server
                    public void actionPerformed(ActionEvent event) {
                        sendData(event.getActionCommand()); // get action command and send data
                        enterField.setText(""); // set the initial text for enterField to nothing
                    }
                }
        );

        add(enterField, BorderLayout.NORTH); // add the enterField in top of the GUI

        displayArea = new JTextArea(); // create the displayArea
        // create a JScrollPane, add the displayArea, and add to middle of GUI
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(300, 150); // set the size of window
        setVisible(true); // show window
    }

    /**
     * Method that connects the MorseCodeClient to the server and processes messages from the server
     *
     */
    public void runClient() {
        try // connect to the server, get the streams, and process the connection
        {
            connectToServer(); // create a Socket to make connection
            getStreams(); // get the input and output streams
            processConnection(); // process connection
        } catch (EOFException eofException) {
            displayMessage("\nClient terminated connection");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            closeConnection(); // close the connection
        }
    }

    /**
     * Method that connects the MoreCodeClient to the server
     *
     * @throws IOException if client fails to connect to the server
     */
    private void connectToServer() throws IOException {
        // display while client is attempting to connect to server
        displayMessage("Attempting connection\n");

        // create Socket to make connection to server
        client = new Socket(InetAddress.getByName(chatServer), 23555); // changed port to my first port #

        // display connection information once successfully connected to given server
        displayMessage("Connected to: " +
                client.getInetAddress().getHostName());
    }

    /**
     * Method that gets streams to send and receive data
     *
     * @throws IOException if client fails to get streams
     */
    private void getStreams() throws IOException {
        // set up an output stream for objects
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush(); // flush output buffer to send header information

        // set up an input stream for objects
        input = new ObjectInputStream(client.getInputStream());
        // display message once streams are properly retrieved
        displayMessage("\nGot I/O streams\n");
    }

    /**
     * Method to process the connection with the server
     *
     * @throws IOException if connection to server fails
     */
    private void processConnection() throws IOException {
        // enable the enterField so client user can send messages
        setTextFieldEditable(true);

        do // process messages sent from server
        {
            try // read the message and display it
            {
                message = (String) input.readObject(); // read the new message
                displayMessage("\n" + message); // display message
            } catch (ClassNotFoundException classNotFoundException) {
                displayMessage("\nUnknown object type received");
            }

        } while (!message.equals("SERVER>>> TERMINATE")); // break connection if user enters TERMINATE
                                                          // ends client connection to server once entered
    }

    /**
     * Method that closes steams and sockets
     *
     */
    private void closeConnection() {
        displayMessage("\nClosing connection");
        setTextFieldEditable(false); // disable the enterField

        try {
            output.close(); // close output stream
            input.close(); // close input stream
            client.close(); // close socket
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Method that allows client to send messages to the server
     *
     * @param message String that represents message to be sent to server
     */
    private void sendData(String message) {
        try // send object to server
        {
            output.writeObject("CLIENT>>> " + message);
            output.flush(); // flush data to output
            displayMessage("\nCLIENT>>> " + message);
        } catch (IOException ioException) {
            displayArea.append("\nError writing object");
        }
    }

    /**
     * Method that manipulates displayArea in the event-dispatch thread
     *
     * @param messageToDisplay String that is the message to be shown in displayArea
     */
    private void displayMessage(final String messageToDisplay) {
        /**
         * anonymous inner class that updates the display area
         *
         */
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // updates displayArea
                    {
                        displayArea.append(messageToDisplay);
                    }
                }
        );
    }

    /**
     * Method that manipulates the enterField in the event-dispatch thread
     *
     * @param editable boolean flag that represents if enterField editable
     *                 true means you can edit, false means you can't edit
     */
    private void setTextFieldEditable(final boolean editable) {
        SwingUtilities.invokeLater(
                /**
                 * anonymous inner class that sets the state of whether enterField is editable or not
                 *
                 */
                new Runnable() {
                    public void run() // sets enterField's editability
                    {
                        enterField.setEditable(editable);
                    }
                }
        );
    }
}
