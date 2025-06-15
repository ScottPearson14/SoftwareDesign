import java.util.concurrent.*;

/**
 * The Main class is the main driver class of the Amazon order processing program. It is the entry point for the program.
 * This class initializes nodes (web server, shipping centers, shipping center sections, docks, and delivery trucks),
 * configures the shared buffers (queues) for inter-node communication, and starts each node as a separate thread
 * in a multithreaded order processing system.
 * The program uses a thread pool to manage and control thread execution for each node, and once all tasks are completed,
 * the thread pool is shut down.
 *
 * @version 1.0.0, 5, Nov 2024
 * @author Scott Pearson
 *
 */
public class Main {
    /**
     * The main method sets up all components of the order processing system and connects each component through shared
     * queues for synchronized communication between threads.
     * The program uses an ExecutorService to manage threads and ensures an orderly shutdown of all threads once
     * all order processing is completed
     *
     * @param args Command line arguments (none)
     */
    public static void main(String[] args) {
        // initialize queues for communication between AmazonWebServer and ShippingCenters
        BlockingQueue<Order> serverToCenter1 = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> serverToCenter2 = new ArrayBlockingQueue<>(100);

        // initialize queues between ShippingCenters and their ShippingCenterSections
        BlockingQueue<Order> center1ToSection1Queue = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> center1ToSection2Queue = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> center2ToSection1Queue = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> center2ToSection2Queue = new ArrayBlockingQueue<>(100);

        // initialize queues between ShippingCenterSections and their shared ShippingDock
        BlockingQueue<Order> dock1Queue = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> dock2Queue = new ArrayBlockingQueue<>(100);

        // initialize queues between ShippingDocks and their DeliveryTrucks
        BlockingQueue<Order> dock1ToTruck1 = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> dock1ToTruck2 = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> dock2ToTruck1 = new ArrayBlockingQueue<>(100);
        BlockingQueue<Order> dock2ToTruck2 = new ArrayBlockingQueue<>(100);

        // initialize executor service for managing threads in the order processing system
        ExecutorService executor = Executors.newCachedThreadPool();

        // create an AmazonWebServer and ShippingCenters
        AmazonWebServer webServer = new AmazonWebServer(serverToCenter1, serverToCenter2);
        ShippingCenter center1 = new ShippingCenter(serverToCenter1, center1ToSection1Queue, center1ToSection2Queue, 1);
        ShippingCenter center2 = new ShippingCenter(serverToCenter2, center2ToSection1Queue, center2ToSection2Queue, 2);

        // create ShippingCenterSections for each ShippingCenter
        ShippingCenterSection section1Center1 = new ShippingCenterSection(center1ToSection1Queue, dock1Queue, 1.1);
        ShippingCenterSection section2Center1 = new ShippingCenterSection(center1ToSection2Queue, dock1Queue, 1.2);
        ShippingCenterSection section1Center2 = new ShippingCenterSection(center2ToSection1Queue, dock2Queue, 2.1);
        ShippingCenterSection section2Center2 = new ShippingCenterSection(center2ToSection2Queue, dock2Queue, 2.2);

        // create ShippingDocks that distribute orders to DeliveryTrucks
        ShippingDock dock1 = new ShippingDock(dock1Queue, dock1ToTruck1, dock1ToTruck2);
        ShippingDock dock2 = new ShippingDock(dock2Queue, dock2ToTruck1, dock2ToTruck2);

        // create DeliveryTrucks assigned to a specific dock's queue
        DeliveryTruck truck1 = new DeliveryTruck(dock1ToTruck1, 1.1);
        DeliveryTruck truck2 = new DeliveryTruck(dock1ToTruck2, 1.2);
        DeliveryTruck truck3 = new DeliveryTruck(dock2ToTruck1, 2.1);
        DeliveryTruck truck4 = new DeliveryTruck(dock2ToTruck2, 2.2);

        // submit all node tasks to the executor to run as separate threads
        executor.submit(webServer);
        executor.submit(center1);
        executor.submit(center2);
        executor.submit(section1Center1);
        executor.submit(section2Center1);
        executor.submit(section1Center2);
        executor.submit(section2Center2);
        executor.submit(dock1);
        executor.submit(dock2);
        executor.submit(truck1);
        executor.submit(truck2);
        executor.submit(truck3);
        executor.submit(truck4);

        // shutdown the executor service after all orders are processed and completed
        executor.shutdown();
        try {
            // await orderly shutdown -> force shutdown if tasks take too long
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Executor did not shutdown in time, forcing a shutdown");
                executor.shutdownNow(); // force a shutdown if the time limit is reached
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted during shutdown wait");
            executor.shutdownNow(); // force a shutdown if the time limit is reached
            Thread.currentThread().interrupt(); // preserve interrupt status
        }

    }

}