/* This Java file is part of Matt Krueger's Introduction to Software Design Oral Exam 2 */

package network;

import utils.DBConnection;
import utils.HWTBAMMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class:
 *     Server
 *
 * Purpose:
 *     The Server class is endpoint of server for the socket connection.
 *     There is only one instance of this class, but it utilizes multithreading to allow for >1 Client connections
 *     creating a multithreaded server (MTS).
 *
 *     This Server class is responsible for the following:
 *             1. MTS - servicing threads with clients by utilizing ArrayList, ExecutorService, and Worker objects to do this.
 *             2. TODO - db communicaiton
 *             2. TODO - game logic communication
 *
 * Multithreaded:
 *     Yes. This is a multithreaded class with Worker threads processed concurrently.
 *
 * @see Worker
 * @see Client
 */
public class Server implements Runnable {
    /** port: integer port for endpoint */
    private final int port;

    /** serverSocket: serverSocket used as a listener for new Client connections */
    private ServerSocket serverSocket;

    /** connections: counter for number of connections; used to assign IDs to each user */
    private int connections;

    /** executorService: ExecutorService to handle the execution of threads (per SWD requirements) */
    private final ExecutorService executorService;

    /** workers: ArrayList of Workers used to broadcast server messages */
    private ArrayList<Worker> workers;

    /**
     * Constructor:
     *     Server
     *
     * Purpose:
     *     The Server constructor takes the port endpoint as its parameter:
     *         1. port
     *     And sets port as well as other attributes:
     *         - connections: 0
     *         - executorService: cached (no set number of threads, caching doesn't drastically improve efficiency for this project)
     *         - workers: new (0 initial)
     *
     * @param port: integer port number
     * @return port:
     */
    public Server(int port) {
        this.port = port;
        connections = 0;
        executorService = Executors.newCachedThreadPool();
        workers = new ArrayList<>();
    }

    /**
     * TODO - update
     * Method:
     *     incrementConnections
     *
     * Purpose:
     *     add 1 connection when Server accepts new connection
     *
     * @synchronized: added as server is multithreaded
     * @return none
     */
    private synchronized void incrementConnections() {
        connections++;
    }

    /**
     * TODO - update
     * Method:
     *
     * Purpose:
     *     remove 1 connection when a Worker connection (ultimately Client) is shutdown
     *
     * @synchronized: added as server is multithreaded
     * @return none
     */
    private synchronized void decrementConnections() {
        connections--;
    }

    /**
     * TODO - update
     * Method:
     *     broadcastAction
     *
     * Purpose:
     *     Broadcast wanted message to all Worker threads.
     *
     * @synchronized: added as server is multithreaded
     * @param message: string message representing action created by user
     * @return none
     */
    public synchronized void broadcastAction(HWTBAMMessage message) throws IOException {
        System.out.println("BROADCASTING");
        for (Worker worker : workers) {
            if (worker != null) {
                System.out.println("+");
                worker.sendMessage(message);
            }
        }
    }

    /**
     * TODO - update
     * Method:
     *     processAction
     *
     * Purpose:
     *     Functionality of the h.w.t.b.a.m/Server interaction.
     *     This method takes the action from hwtbamMessage object parsed from Worker and maps to wanted action.
     *
     *      Steps:
     *          1. obtain input
     *          3. mapping logic
     *          4. server action
     *          5. return result back to Worker
     *
     * @synchronized: added as server is multithreaded
     * @param message represents incoming message from the client containing action and other relevant data
     * @return result response message containing the result of the action and any necessary data.
     */
    public synchronized HWTBAMMessage processAction(HWTBAMMessage message, DBConnection conn) throws SQLException, IOException {
        HWTBAMMessage hwtbamMessageOut = new HWTBAMMessage(message.getUsername());
        System.out.println("\n--- SERVER IN: NEW ---\n");
        System.out.println(message);

        // map action to SQL method
        if (message.getAction().equals("register")) {
            // add a player
            System.out.println("--register");
            hwtbamMessageOut = conn.registerUser(hwtbamMessageOut.getUsername(), hwtbamMessageOut.getPassword());

        } else if (message.getAction().equals("removePlayer")) {
            // remove a player
            System.out.println("-- removePlayer");
            hwtbamMessageOut = conn.removeUser(hwtbamMessageOut.getUsername(), hwtbamMessageOut.getPassword());

        } else if (message.getAction().equals("login")) {
            // login with credentials
            System.out.println("-- login");

        } else if (message.getAction().equals("enter")) {
            // player enters server
            System.out.println("-- enter");
            hwtbamMessageOut.setAction("Welcome");
            hwtbamMessageOut.setMessage(hwtbamMessageOut.getUsername() + " entered the server.");

        } else if (message.getAction().equals("getNewQuestion")) {
            // get a new random
//            System.out.println("-- getNewQuestion");
//                if (message.getDifficulty() == 0) {
//                    conn.getDifficulty0Question();
//                } else if (message.getDifficulty() == 0) {
//                    conn.getDifficulty1Question();
//                } else if (message.getDifficulty() == 0) {
//                    conn.getDifficulty2Question();
//                } else {
//                    System.out.println("--- error: getNewQuestion - unrecognized");
//                }
//
        }  else if (message.getAction().equals("createQuestion")) {
            System.out.println("-- createQuestion");

        }  else if (message.getAction().equals("removeQuestion")) {
            System.out.println("-- removeQuestion");
        }  else if (message.getAction().equals("guess")) {
            System.out.println("-- guess");
        }  else if (message.getAction().equals("buyPowerUp")) {
            System.out.println("-- buyPowerUp");
        }  else if (message.getAction().equals("usePowerUp")) {
            System.out.println("-- usePowerUp");
        }  else if (message.getAction().equals("getLeaderBoard")) {
            System.out.println("-- getLeaderBoard");
        } else if (message.getAction().equals("gameChatSEND")) {
            message.setAction("gameChatRECEIVE"); // simply forward as RECEIVE
            broadcastAction(message);
        }

        return hwtbamMessageOut;
    }

    /**
     * TODO - update
     * Method:
     *     removeWorker
     *
     * Purpose:
     *     This method is called by Worker thread to remove itself from the Server's ArrayList of Workers.
     *
     * @synchronized: added as server is multithreaded
     * @see Worker
     * @param worker: Worker to be removed from ArrayList
     * @return none
     */
    public synchronized void removeWorker(Worker worker) throws IOException {
        workers.remove(worker);
        decrementConnections();

        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(worker.getUsername());
        hwtbamMessage.setBroadcast(true);
        hwtbamMessage.setAction("playerExit");
        broadcastAction(hwtbamMessage);
    }

    /**
     * TODO - update
     * Method:
     *     closeAll
     *
     * Purpose:
     *     Close the listening ServerSocket. This class does not directly have any I/O, only connection.
     *
     * @throws IOException: error occurring during shutdown
     * @return none
     */
    private void closeAll() throws IOException {
        serverSocket.close();
    }

    /**
     * TODO - update
     * Method:
     *     run (Server)
     *
     * Purpose:
     *     This method begins the MTS.
     *
     *     Steps:
     *          1. create binding serverSocket (start server)
     *          2. listen for new connections
     *          3. bind new connection in a new Worker thread (begin serving)
     *
     * @override Runnable
     * @see Worker
     * @return none
     */
    @Override
    public void run() {
        System.out.println("--- [Who Wants to Be a Millionaire Server Started] ---");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            Worker worker;
            try {
                Socket connectionSocket = serverSocket.accept();
                incrementConnections();
                worker = new Worker(this, connectionSocket, connections);
                workers.add(worker);
                executorService.submit(worker);
            } catch (IOException e) {
                try {
                    closeAll();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
