/* This Java file is part of Matt Krueger's Introduction to Software Design Oral Exam 2 */

package network;

import utils.DBConnection;
import utils.HWTBAMMessage;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Class:
 *     Worker (h.w.t.b.a.m)
 *
 * Purpose:
 *     The Worker class handles a connection on a thread included in the MTS Server.
 *
 *     This class is the liaison between the Client and Server, responsible for:
 *         - connection I/O with client (contains connection socket)
 *         - connection with Server (server passed into client upon in constructor); I/O not required as this code lives server-side.
 *
 * Multithreaded:
 *     No, this class itself is not a multithreaded class as it is used only to echo responses (i.e. not controlled by a human via CLI).
 */
public class Worker implements Runnable {
    /** server: Server instance of program (only one instance) */
    private final Server server;

    /** connection: socket connection to server host & port for bidirectional communication */
    private final Socket connection;

    /** ID: integer representing which user this connection (Client) is */
    private final int ID;

    /** input: ObjectInputStream to obtain HWTBAMMessage. Manages input communication from client */
    private ObjectInputStream input;

    /** output: ObjectOutputStream to write HWTBAMMessage. Manages output communication to client */
    private ObjectOutputStream output;

    /** username: username of this workers client */
    private String username;


    /** db: database connection for this client/worker */
    private DBConnection conn;

    /**
     * Constructor:
     *     Worker
     *
     * Purpose:
     *     Create new endpoint connection between Client and Server.
     *
     *     A Worker is created by passing the following:
     *         - server: Server creating 'this' worker to enable server-side connection
     *         - connection: fresh socket created by serverSocket.accept()
     *         - id: Client id passed into initialization
     *         - db: connection to db
     *
     * @return none
     */
    public Worker(Server server, Socket connection, int id) {
        this.server = server;
        this.connection = connection;
        this.ID = id;
    }

    /**
     * Method:
     *     receiveMessage
     *
     * Purpose:
     *      Steps:
     *          This method takes the input from the Client connection, determining if it is a valid action.
     *              If the action is valid, it then passes it to the server.processAction to receive message to send back.
     *              Again, this method determines if a valid response, and maps output to send data to wanted location:
     *                  - add/remove -> broadcast to all workers
     *                  - show/list -> send only to Client connected to this Worker
     *
     * @throws IOException: error occurring during I/O processing
     * @return none
     */
    private void receiveMessage() throws IOException, ClassNotFoundException, SQLException {
        HWTBAMMessage hwtbamMessageIn = (HWTBAMMessage) input.readObject();

        if (!(hwtbamMessageIn.getAction().equals("EXIT"))) {
            // map all other actions to server
            System.out.println("\n FORWARDING TO SERVER (Worker --> Server): " + hwtbamMessageIn.getAction() + "\n");
            HWTBAMMessage hwtbamMessageOut = server.processAction(hwtbamMessageIn, conn);
            System.out.println("\n PROCESSING COMPLETE (Worker ---> Client): " + hwtbamMessageOut);
            sendMessage(hwtbamMessageOut);
        } else {
            // close client
            closeAll();
        }
    }

    /**
     * Method:
     *     run
     *
     * Purpose:
     *     This method is called when the new Client/Server connection is created by MTS. It simply obtains I/O and listens for Client actions to process.
     *
     * @override Runnable
     * @return none
     */
    @Override
    public void run() {
        try {
            getIO();

            // listen loop
            while (true) {
                receiveMessage();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method:
     *     getIO
     *
     * Purpose:
     *     Initial call after new Worker is executed, obtaining the methods of communication:
     *         - input: PrintWriter
     *         - output: BufferedReader
     *
     * @throws IOException: error occurring during initialization
     * @return none
     */
    private void getIO() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
        output.flush();
    }

    /**
     * Method:
     *     closeAll
     *
     * Purpose:
     *     Safely close client-side-error-producing objects:
     *         1. output
     *         2. input
     *         3. connection (with socket)
     *     Additionally, remove 'this' worker from the Server ArrayList tracking current Workers.
     *
     * @throws IOException: error occurring during shutdown
     * @return none
     */
    public void closeAll() throws IOException {
        try {
            // close the output
            output.close();

            // remove the worker from the arraylist tracking current players
            server.removeWorker(this);

            // continue closing connections
            input.close();
        } catch (IOException e) {
            System.out.println("Failed to close worker");
            throw new IOException(e);
        }
    }

    /**
     * Getter that retrieves the ID
     *
     * @return int that represents the ID
     */
    public int getID() {
        return ID;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Method:
     *     sendMessage
     *
     * Purpose:
     *     saves redundant code when sending object over output stream
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(HWTBAMMessage message) throws IOException {
        output.writeObject(message);
        output.flush();
    }
}