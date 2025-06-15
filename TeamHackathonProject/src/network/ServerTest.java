package network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ServerTest class is a class that creates a new server for the Who Wants To Be a Millionaire game. It connects to
 * the host executing on a single thread
 */
public class ServerTest {
    /**
     * TODO - update
     * Method:
     *     main (Server entry-point)
     *
     * Purpose:
     *     This creates a new server connecting in port 23755 (one of my SWD ports). The server is located on your computer.
     *
     * @param args: cli arguments when program started
     * @return none
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Server server = new Server(23755);
        executor.execute(server);
    }
}
