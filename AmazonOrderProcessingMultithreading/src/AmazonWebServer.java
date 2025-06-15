import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * The AmazonWebServer class is a class that reads Amazon orders from a CSV file and routes them
 * to the correct ShippingCenter queues based on the city of each order. The class implements runnable so its instances
 * can run as a thread
 *
 * @version 1.0.0, 6, Nov 2024
 * @author Scott Pearson
 *
 */
public class AmazonWebServer implements Runnable {
    /** Output queue for orders going to ShippingCenter 1 */
    private final BlockingQueue<Order> outputQueueCenter1; // make final since it shouldn't change upon creation
    /** Output queue for orders going to ShippingCenter 2 */
    private final BlockingQueue<Order> outputQueueCenter2; // make final since it shouldn't change upon creation

    /**
     * Constructor that creates an AmazonWebServer with specified output queues for each ShippingCenter
     *
     * @param outputQueueCenter1 the queue for orders destined for ShippingCenter1
     * @param outputQueueCenter2 the queue for orders destined for ShippingCenter2
     */
    public AmazonWebServer(BlockingQueue<Order> outputQueueCenter1, BlockingQueue<Order> outputQueueCenter2) {
        this.outputQueueCenter1 = outputQueueCenter1;
        this.outputQueueCenter2 = outputQueueCenter2;
    }
    // override run method

    /**
     * The run method is an overridden method that reads orders from a CSV file and routes them to the correct
     * ShippingCenter based on the city.
     * This method sends a termination signal (null) to each queue after processing all orders
     *
     */
    @Override
    public void run() {
        // Create a BufferedReader to read text from the specified CSV file efficiently.
        // The FileReader opens the file, and the BufferedReader provides a buffer for faster line-by-line reading.
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "oral_exam2/S24_AmazonOrderProcessing_Hard" +
                        "/res/S24_AmazonOrderProcessing_OrdersFile.csv"))) {
            String line;
            reader.readLine(); // skip the header
            // A while loop that reads each order line and stops when all lines are read
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t"); // Splits line on tabs into order details

                // create order
                Order order = new Order(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);

                // determine correct ShippingCenter by city, and put the order in the correct queue
                try {
                    if (order.getCity().matches("Los Angles") ||
                            order.getCity().matches("San Francisco") ||
                            order.getCity().matches("Seattle") ||
                            order.getCity().matches("Denver")) {
                        outputQueueCenter1.put(order); // place in 1st center queue
                    } else {
                        outputQueueCenter2.put(order); // place in 2nd center queue
                    }
                } catch (NullPointerException e) {
                    System.err.println("Queue not initialized for order: " + order);
                    e.printStackTrace(); // print the stack trace for exceptions
                }
            }

            // signal the end of order processing by adding poison pill to each ShippingCenter queue
            // create special poison pill to send to output queues
            Order SpecialPoisonPill = new Order("PoisonPill","","","","","","");
            outputQueueCenter1.put(SpecialPoisonPill); // end signal
            outputQueueCenter2.put(SpecialPoisonPill); // end signal

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            System.err.println("An I/O error occurred while reading the file");
        } catch (InterruptedException e) {
            System.err.println("AmazonWebServer was interrupted while processing orders");
        }
    }

}
