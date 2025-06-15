import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The ShippingCenterSection class is a class that processes orders and forwards them to the ShippingDock
 * The class implements runnable so its instances can run as a thread
 *
 * @version 1.0.0, 6, Nov 2024
 * @author Scott Pearson
 *
 */
public class ShippingCenterSection implements Runnable {
    /** Input queue that the ShippingCenterSection receives orders from */
    private final BlockingQueue<Order> inputQueue; // make final since it shouldn't change upon creation
    /** Output queue for orders to be sent to the ShippingDock */
    private final BlockingQueue<Order> dockQueue; // make final since it shouldn't change upon creation
    /** Identifier for the ShippingCenterSection (1 or 2) */
    public final double sectionID; // make final since it shouldn't change upon creation

    /**
     * Constructor that creates a ShippingCenterSection with specified input and output queues
     *
     * @param inputQueue the input queue for receiving orders
     * @param dockQueue the output queue for sending orders to the ShippingDock
     * @param sectionID the identifier for the ShippingCenterSection (0 or 1)
     */
    public ShippingCenterSection(BlockingQueue<Order> inputQueue, BlockingQueue<Order> dockQueue, double sectionID) {
        this.inputQueue = inputQueue;
        this.dockQueue = dockQueue;
        this.sectionID = sectionID;
    }

    /**
     * The run method is an overridden method that processes orders by adding section tracking for orders.
     * The method also delays for processing time. It sends an end signal to the ShippingDock queue once all orders
     * are processed
     *
     */
    @Override
    public void run() {
        try {
            // while loop to process orders
            while (true) {
                Order order = inputQueue.take(); // take an order from the input queue
                // check for poison pill (end of order)
                if (Order.PoisonPill.equals(order)) {
                    dockQueue.put(Order.PoisonPill); // send end signal to ShippingDock
                    break;
                }
                // update tracking with section information
                order.setSection("Section " + sectionID);

                // simulate processing delay time (0-5 seconds)
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 5000));

                // send processed order to the dock queue
                dockQueue.put(order);
            }
        } catch (InterruptedException e) {
            System.err.println("ShippingCenterSection interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // preserve interrupt status
        }
    }
}
