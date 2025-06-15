import java.util.concurrent.BlockingQueue;

/**
 * The ShippingCenter class is a class that routes orders to specific ShippingCenterSections based on the category
 * of the item in the order. The class implements runnable so its instances can run as a thread
 *
 * @version 1.0.0, 5, Nov 2024
 * @author Scott Pearson
 */
public class ShippingCenter implements Runnable {
    /** Input queue that ShippingCenter receives orders from */
    private final BlockingQueue<Order> inputQueue; // make final since it shouldn't change upon creation
    /** Output queue for orders going to Section 1 */
    private final BlockingQueue<Order> section1Queue; // make final since it shouldn't change upon creation
    /** Output queues for orders going to Section 2 */
    private final BlockingQueue<Order> section2Queue; // make final since it shouldn't change upon creation
    /** Identifier for ShippingCenters (1 or 2) */
    private final int centerID; // make final since it shouldn't change upon creation

    /**
     * Constructor that creates a ShippingCenter with specified input and output queues
     *
     * @param inputQueue the input queue for receiving orders
     * @param section1Queue the output queue for orders going to Section 1
     * @param section2Queue the output queue for orders going to Section 2
     * @param centerID the identifier for a ShippingCenter (1 or 2)
     */
    public ShippingCenter(BlockingQueue<Order> inputQueue, BlockingQueue<Order> section1Queue, BlockingQueue<Order> section2Queue, int centerID) {
        this.inputQueue = inputQueue;
        this.section1Queue = section1Queue;
        this.section2Queue = section2Queue;
        this.centerID = centerID;
    }

    /**
     * The run method is an overridden method that processes orders from an input queue. It routes the orders to
     * the appropriate ShippingCenterSection based on category.
     * The method sends an end signal (null) to both section queues after processing all orders.
     *
     */
    @Override
    public void run() {
        try {
            // while loop to process orders
             while (true) {
                Order order = inputQueue.take(); // retrieve an order from the input queue
                // check for poison pill (end of orders)
                if (Order.PoisonPill.equals(order)) {

                    // notify both sections that processing is done by passing poison pill
                    section1Queue.put(Order.PoisonPill); // send end signal to Section 1
                    section2Queue.put(Order.PoisonPill); // send end signal to Section 2
                    break;
                }
                // update the tracking of the order with center information
                order.setShippingCenter("Shipping Center " + centerID);

                // route the order based on the category's starting letter using regular expression
                if (order.getCategory().matches("^[A-P].*")) {
                    section1Queue.put(order); // send to Section 1
                } else {
                    section2Queue.put(order); // send to Section 2
                }

            }
        } catch (InterruptedException e) {
            System.err.println("ShippingCenter interrupted " + e.getMessage());
            Thread.currentThread().interrupt(); // preserve interrupt status
        }
    }
}
