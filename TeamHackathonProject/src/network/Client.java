package network;

import game.Game;
import game.GameChat;
import game.GameDriver;
import utils.HWTBAMMessage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO - update
 * Class:
 *     Client (BinarySearchTree)
 *
 * Purpose:
 *     The Client class is endpoint of user for the socket connection.
 *     The connection is handled on a thread of the server inside the Worker class.
 *
 *     Aside from creating the connection with the Server at endpoint (host, port), this class
 *         handles two thread:
 *             1. inputThread - thread for receiving messages from linked Worker & from Server broadcasts
 *             2. outputThread - thread for sending messages to linked Worker (which are ultimately sent to server to interact with the h.w.t.b.a.m. game & database)
 *
 * Multithreaded:
 *     Yes. This is a multithreaded class with I/O threads processed concurrently.
 *
 * @see Worker
 * @see Server
 */
public class Client implements Runnable {
    /** connection: socket connection to server host & port for bidirectional communication */
    private Socket connection;

    /** input: ObjectInputStream to obtain HWTBAMMessage. Manages input communication from server */
    private ObjectInputStream input;

    /** output: ObjectOutputStream to write HWTBAMMessage. Manages output communication to server */
    private ObjectOutputStream output;

    /** host: host string (ex "localhost") for the server's ip */
    private final String host;

    /** port: integer port for endpoint */
    private final int port;

    /** executorService: ExecutorService to handle the execution of threads (per SWD requirements) */
    private ExecutorService executorService;

    /** link gamed chat */
    GameChat gameChat;

    // TODO
    private GameDriver gui;

    // TODO
    private String username;

    /**
     * TODO - update
     *
     * Constructor:
     *     Client
     *
     * Purpose:
     *     The Client constructor takes the location of the endpoint as its parameters:
     *         1. host str
     *         2. port number
     *     And sets attributes.
     *
     * @param host: String hostname (ex "localhost")
     * @param port: integer port number
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * TODO - CHANGE FROM CLI -> SWING (gamedriver)
     *
     * Method:
     *     run
     *
     * Purpose:
     *     This method is called when Worker class is executor, creating an endpoint between new ('this') Client and the Server.
     *
     *     Steps for achieving communication:
     *         1. connect to server to open connection & get I/O
     *         2. create input thread -> add to executor service
     *         3. create output thread -> add to executor service
     *
     *     The multithreaded I/O continuously 'listen' for input from Server & output from the user to send data to the Server.
     *
     * @override Runnable
     * @return none
     */
    @Override
    public void run() {
        try {
            connectToServer();
            executorService = Executors.newFixedThreadPool(2);

            // input thread to handle messages being sent from server
            Runnable inputThread = new Runnable() {
                @Override
                public void run() {
                    try {
                        HWTBAMMessage message;
                        while ((message = (HWTBAMMessage) input.readObject()) != null) {
                            System.out.println("\n--- CLIENT IN: NEW ---\n");
                            System.out.println(message);

                            if (message.getAction().equals("startGame")) {
                                System.out.println("GAME STARTING");
                            } else if (message.getAction().equals("receiveNewQuestion")) {
                                System.out.println("RECEIVED FIRST");
                            } else if (message.getAction().equals("receiveLeaderBoard")) {
                                System.out.println("RECEIVED FIRST");
                            } else if (message.getAction().equals("receivePowerUpResult")) {
                                System.out.println("RECEIVED FIRST");
//                            } else if (message.getAction().equals("gameChatRECEIVE")) {
//                                System.out.println("GAME CHAT RECEIVED");
//                                gameChat.logMessage(message);
//                            }
                            } else {
                                System.out.println("unrecognized");
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };

            gui = new GameDriver(this);
            gui.start();

            // add the threads to the service
            executorService.submit(inputThread);
        } catch (IOException e) {
            try {
                closeAll();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO - update
     *
     * Method:
     *     connectToServer
     *
     * Purpose:
     *     This method initiates the TCP 3 way handshake connection to open communication with Server & obtains the I/O reader/writer.
     *
     * @throws IOException: error occurring during initialization
     * @return none
     */
    public void connectToServer() throws IOException {
        try {
            connect();
            getIO();

        } catch (IOException e) {
            System.out.println("IO Exception: failed to create input/output objects for Client");
            e.printStackTrace();
        }
    }

    /**
     * TODO - update
     *
     * Method:
     *     connect
     *
     * Purpose:
     *     First step in connectToServer(), initializing a connection with the Server. This then triggers a Worker thread to link to 'this' Client.
     *
     * @throws IOException: error occurring during initialization
     * @return none
     */
    private void connect() throws IOException {
        try {
            connection = new Socket(host, port);
        } catch (ConnectException e) {
            System.out.println("Failed to connect to " + host + " on " + port);
        }
    }

    /**
     * TODO - update
     * Method:
     *     getIO
     *
     * Purpose:
     *     Second step in connectToServer(), obtaining the methods of communication:
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
     * TODO - update
     * Method:
     *     closeAll
     *
     * Purpose:
     *     Safely close client-side-error-producing objects:
     *         1. output
     *         2. input
     *         3. connection (with socket)
     *     After connection dependent objects are closed, exit the program.
     *
     * @throws IOException: error occurring during shutdown
     * @return none
     */
    private void closeAll() throws IOException {
        try {
            System.out.println("Exiting program...");

            // send message to worker to also close its socket connection
            HWTBAMMessage exitMessage = new HWTBAMMessage(username);
            exitMessage.setAction("EXIT");
            sendMessage(exitMessage);

            // continue closing all connections
            output.close();
            input.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Failed to close client");
            throw new IOException(e);
        }
    }

    /**
     * Getter that retrieves username
     *
     * @return String that represents username
     */
    public String getUsername() {
        return username;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setGameChat(GameChat gameChat) {
        this.gameChat = gameChat;
    }

    public void setUsername(String username) {
        this.username = username;
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
