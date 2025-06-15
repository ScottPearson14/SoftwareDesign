import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The DeliveryTruck class is a class that outputs processed orders to the screen. This simulates that the orders
 * have been delivered. The class implements runnable so its instances can run as a thread
 *
 * @version 1.0.0, 6, Nov 2024
 * @author Scott Pearson
 *
 */
public class DeliveryTruck implements Runnable {
    /** Input queue that the DeliveryTruck receives orders from */
    private final BlockingQueue<Order> inputQueue; // make final since it shouldn't change upon creation
    /** Identifier for a DeliveryTruck  */
    private final double truckID; // make final since it shouldn't change upon creation

    /**
     * Constructor that creates a DeliveryTruck with a specified input queue
     *
     * @param inputQueue the queue from which orders are received from
     * @param truckID the identifier for a DeliveryTruck
     */
    public DeliveryTruck(BlockingQueue<Order> inputQueue, double truckID) {
        this.inputQueue = inputQueue;
        this.truckID = truckID;
    }

    /**
     * The run method is an overridden method that processes each order by printing delivery information to the console.
     * The method terminates once an end signal (null order) is received
     *
     */
    @Override
    public void run() {
        try {
            int deliveredCount = 0; // track the number of deliveries completed
            // continue delivering orders until 4 deliveries are made or termination signal is received (null)
            while (deliveredCount < 4) {
                Order order = inputQueue.take(); // retrieve the next order from the input queue,
                                                 // blocking if none are available

                // PoisonPill marks end of deliveries
                if (Order.PoisonPill.equals(order)) {
                    if (deliveredCount == 0) {
                        System.out.println("Truck " + truckID + " received no orders");
                    }
                    break; // exit loop and end thread execution
                }

                // mark the order with the truck ID for delivery tracking
                order.setDeliveryTruck("Delivered by Truck " + truckID);

                // simulate delivery time (0-10 seconds)
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 10000));

                System.out.println(order); // print the order details after the delivery is completed
                deliveredCount++; // increment the delivery count after a successful delivery
            }

            System.out.println("Truck " + truckID + " has completed deliveries.\n");

        } catch (InterruptedException e) {
            System.err.println("Delivery Truck " + truckID + " interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // preserve the interrupt status
        }
    }
}
