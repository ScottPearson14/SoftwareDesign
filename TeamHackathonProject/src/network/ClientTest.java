package network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ClientTest class is a class that creates a new client for the Who Wants To Be a Millionaire game. It connects the
 * client to the "localhost" executing on a single thread.
 *
 */
public class ClientTest {
    /**
     * Method:
     *     main (entry-point)
     *
     * Purpose:
     *     This creates a new client connecting to "localhost" in port 23755 (one of my SWD ports) executing on a single thread.
     *
     * @param args: cli arguments when program started
     * @return none
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Client client = new Client("localhost", 23755);
        //Client client = new Client("172.17.98.166", 23755);
        executorService.execute(client);
    }
}
