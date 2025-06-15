# S24_AmazonOrderProcessing_Hard
[Home](Home)

## Problem Statement
For this problem, ArrayBlockingQueues will be used to construct a program that will emulate the processing of an order from Amazon.com. The program will mimic from when you place the order to when it gets put on the truck for delivery to your home. The program will be structured like the tree below:
![Amazon_Order_Illustration](uploads/84af2826bad050cefb7434ee7d4612c8/Amazon_Order_Illustration.jpg)
Each node will:
1. Run its own thread
2. Contain 1 input buffer and 1-2 output buffers w/ 2 expections:
- The Web Server nodes read from a file and and doesn't have an input buffer
- The Delivery Truck nodes print to the screen using a graphic display and have no output buffers

Application Specifications:
- the buffer between 2 nodes must be shared
- the only means of communication between nodes is the buffer between them

Functionality of each node:

**Amazon Web Server node**: receives order containing following info:
- Delivery address
-> Street Address, City, State, Zip
- Name on order
- Item ordered
- Item category

The node reads orders from the S24_AmazonOrderProcessing_OrdersFile.csv file.
For each order, it decides which Shipping Center to send it to based on the following:

Orders to the following cities got to Shipping Center 1:
Los Angeles, San Francisco, Seattle, Denver

Orders to the following cities go to Shipping Center 2:
Des Moines, Chicago, St. Louis, Any other city not already mentioned
- When this node reaches the end of orders in the file, it sends indication to all the Shipping Center nodes through shared buffer that end has been reached and then terminate.

**Shipping Center node**: this node must decide which part of the warehouse the item is in and deliver it to the shipping dock.

The Shipping Center organizes items based on the first letter of their category. It also appends the Shipping Center number to the order for tracking purposes.

The processing is as follows:
- Orders with categories beginning with A-P go to Section 1
- Orders with categories beginning with Q-Z go to Section 2

**Shipping Center Sections node**: this node will do the following:
1. Receive order:
2. Append the Shipping Center Section number to the order for tracking purposes
3. Pause for 0-5 seconds (decided randomly)
4. Put orders on the Shipping Dock's buffer

**Shipping Dock** node: nodes will fill up the buffers to the trucks as there is room available. They will start with truck 1.  If the buffer for Truck 1 is full, then try truck 2.  If they are all full, then wait until one buffer becomes available.

**Delivery Truck nodes**: these nodes will perform the following sequence:
1. Read from their input buffer until they have received 4 deliveries or receive notification that there are no more deliveries. On each delivery, they must append the truck number for tracking purposes.
2. For each of the four deliveries (or less if the "no more deliveries" notification was received), do the following:
- Sleep for 0-10 seconds (decided randomly)
- Print out the information on the shipping order to the screen

The truck also must also print out a message to the screen after it has received the “no more deliveries” notification and delivered its last order indicating that it is done delivering.  This message should specify the truck’s number and the number of its associated shipping center.

The Shipping order information printed to the screen must include the following information:

- Delivery address
- Name on order
- Item ordered
- Item category
- Shipping Center the order went through
- Shipping Center Section the order went through
- Delivery Truck the order was delivered on

## Developer Documentation
My code for this specific program is divided into 6 classes along with a Main driver class to act as the entry point for the program. The Order class acts as the building blocks for an order in the program. The other classes act as nodes for the ordering process. Here is a more in-depth explanation of each class:

1. **Order class**: this is a class that represents an Order in an Amazon web server

The attributes for this class are:
- streetAddress: String representing street address of order
- city: String representing city address info of order
- state: String representing state address info of order
- zip: String representing zip code address info of order
- name: String representing name of person who ordered
- item: String representing item of order
- category: String representing category item falls under
- shippingCenter: String representing shipping center for order
- section: String representing section for order
- deliveryTruck: String representing delivery truck for order
- PoisonPill: an Order object representing a blank order to be used as a way to mark all orders being done processed

The methods of this class consist of:
- a constructor that initializes an order with address info, name of person who ordered, item of order, and category item falls under
- getters and setters for attributes
- shouldTerminate: boolean helper function for detecting PoisonPill 
- Overridden toString method to properly format the order

2. **AmazonWebServer class**: this class reads Amazon orders from a CSV file and routes them to the correct ShippingCenter queues based on the city of each order. The class implements runnable so its instances can run as a thread

The attributes for this class are:
- outputQueueCenter1: Output queue for orders going to ShippingCenter 1
- outputQueueCenter2: Output queue for orders going to ShippingCenter 2

The methods for this class are:
- a constructor that creates an AmazonWebServer with specified output queues for each ShippingCenter
- run: an overridden method that reads orders from a CSV file and routes them to the correct ShippingCenter based on the city.This method sends a termination signal (null) to each queue after processing all orders

3. **ShippingCenter Class**: this is a class that routes orders to specific ShippingCenterSections based on the category of the item in the order. The class implements runnable so its instances can run as a thread.

The attributes for this class are:
- inputQueue: Input queue that ShippingCenter receives orders from
- section1Queue: Output queue for orders going to Section 1
- section2Queue: Output queues for orders going to Section 2
- centerID: Identifier for ShippingCenters (1 or 2)

The methods consist of:
- A constructor that creates a ShippingCenter with specified input and output queues
- run: an overridden method that processes orders from an input queue. It routes the orders to the appropriate ShippingCenterSection based on category. The method sends an end signal (null) to both section queues after processing all orders.

4. **ShippingCenterSection Class**: This is a class that processes orders and forwards them to the ShippingDock. The class implements runnable so its instances can run as a thread

The attributes consist of:
- inputQueue: Input queue that the ShippingCenterSection receives orders from
- dockQueue: Output queue for orders to be sent to the ShippingDock
- sectionID: Identifier for the ShippingCenterSection (1 or 2)

The methods consist of:
- a constructor that creates a ShippingCenterSection with specified input and output queues
- run: an overridden method that processes orders by adding section tracking for orders.The method also delays for processing time. It sends an end signal to the ShippingDock queue once all orders are processed

5. **ShippingDock Class**: this class retrieves the processed orders from ShippingCenterSections and routes them to the DeliveryTrucks. The class implements runnable so its instances can run as a thread

The attributes of this class consist of:
- inputQueue: Input queue that the ShippingDock receives orders from
- truck1Queue: Output queue for orders going to DeliveryTruck1
- truck2Queue: Output queue for orders going to DeliveryTruck2

The methods of this class consist of:
- a constructor that creates a ShippingDock with specified input and output queues for delivery trucks
- run: an overridden method that routes orders to the appropriate delivery truck based on city. The method sends an end signal (null) to both truck queues after processing all orders

6. DeliveryTruck Class: this class outputs processed orders to the screen. This simulates that the orders have been delivered. The class implements runnable so its instances can run as a thread.

The attributes of this class consist of:
- inputQueue: Input queue that the DeliveryTruck receives orders from
- truckID: Identifier for a DeliveryTruck

The methods for this class are:
- a constructor that creates a DeliveryTruck with a specified input queue
- run: an overridden method that processes each order by printing delivery information to the console. The method terminates once an end signal (null order) is received

7. **Main Class**: this class is the main driver class of the Amazon order processing program. It is the entry point for the program. It initializes nodes (web server, shipping centers, shipping center sections, docks, and delivery trucks), configures the shared buffers (queues) for inter-node communication, and starts each node as a separate thread in a multi-threaded order processing system. The program uses a thread pool to manage and control thread execution for each node, and once all tasks are completed, the thread pool is shut down.


## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S24_AmazonOrderProcessing_Hard/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam2/S24_AmazonOrderProcessing_Hard/doc/UML_S24_AmazonOrderProcessing_Hard.png?ref_type=heads)

## User Documentation
Here are the steps to use/run the program:
1. Ensure that the .csv file exists and is in the res directory so the program can read in the information
2. Go to the Main.java file (the driver program for the AmazonOrderProcessing program)
3. Compile and run the program
4. As the multithreaded program runs it will process each order sequentially, simulating the steps and transfers across the nodes. Each order's details are printed to the console upon reaching the Delivery Truck stage
5. The following information is shown for each order upon completion:
- Order ID
- Customer details
- Destination city
- Delivery Truck ID that completed the delivery

In addition, notifications are printed when:
- Each Delivery Truck has completed its delivery load (usually after four deliveries).
- All processing components have stopped after handling all orders.
6. The program will stop on its own once all orders have been processed and delivered

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S24_AmazonOrderProcessing_Hard?ref_type=heads)
