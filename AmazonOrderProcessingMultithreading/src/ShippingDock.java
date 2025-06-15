import java.util.concurrent.BlockingQueue;

/**
 * The ShippingDick class is a class that retrieves the processed orders from ShippingCenterSections and routes
 * them to the DeliveryTrucks
 * The class implements runnable so its instances can run as a thread
 *
 * @version 1.0.0, 6, Nov 2024
 * @author Scott Pearson
 *
 */
public class ShippingDock implements Runnable {
    /** Input queue that the ShippingDock receives orders from */
    private final BlockingQueue<Order> inputQueue; // make final since it shouldn't change upon creation
    /** Output queue for orders going to DeliveryTruck1 */
    private final BlockingQueue<Order> truck1Queue; // make final since it shouldn't change upon creation
    /** Output queue for orders going to DeliveryTruck2 */
    private final BlockingQueue<Order> truck2Queue; // make final since it shouldn't change upon creation
    /** Int to keep track of which truck to send the next order to (Trucks 1.1/1.2 or Trucks 2.1/2.2) */
    private int currentTruck = 1; // don't define in constructor since this will be used as flag for which truck to send
                                    // orders to

    /**
     * Constructor that creates a ShippingDock with specified input and output queues for delivery trucks
     *
     * @param inputQueue the input queue from which orders are received from
     * @param truck1Queue the output queue for orders going to DeliveryTruck1
     * @param truck2Queue the output queue for orders going to DeliveryTruck2
     */
    public ShippingDock(BlockingQueue<Order> inputQueue, BlockingQueue<Order> truck1Queue, BlockingQueue<Order> truck2Queue) {
        this.inputQueue = inputQueue;
        this.truck1Queue = truck1Queue;
        this.truck2Queue = truck2Queue;
    }

    /**
     * The run method is an overridden method that routes orders to the appropriate delivery truck based on city.
     * The method sends an end signal (null) to both truck queues after processing all orders
     *
     */
    @Override
    public void run() {
        try {
            // while loop to process orders
            while (true) {
                Order order = inputQueue.take(); // retrieve an order from the input queue
                // check for an end signal
                if (Order.PoisonPill.equals(order)) {
                    // notify both trucks that processing is done by passing null
                    truck1Queue.put(Order.PoisonPill); // notify truck1Queue that processing is done
                    truck2Queue.put(Order.PoisonPill); // notify truck2Queue that processing is done
                    break;
                }

                // Logic to determine which truck to send orders to by checking value of currentTruck
                if (currentTruck == 1) {
                    truck1Queue.put(order); // send the order to Truck 1
                    currentTruck = 2; // switch to Truck 2 for next order
                } else {
                    truck2Queue.put(order); // Send order to Truck 2
                    currentTruck = 1; // switch to Truck 1 for next order
                }

            }
        } catch (InterruptedException e) {
            System.err.println("ShippingDock interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // restore the interrupt status

        }
    }
}
