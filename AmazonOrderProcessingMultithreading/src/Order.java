/**
 * The Order class is a class that represents an Order in an Amazon web server
 *
 * @version 1.0.0, 5, Nov 2024
 * @author Scott Pearson
 *
 */
public class Order {
    /** a string that represents the street address of an order */
    private String streetAddress;
    /** a string that represents the city address of an order */
    private String city;
    /** a string that represents the state address of the address of an order */
    private String state;
    /** a string that represents the zip code of an address of an order */
    private String zip;
    /** a string that represents  the name of the person for the order */
    private String name;
    /** a string that represents the item of the order */
    private String item;
    /** a string that represents the category of the item of the order */
    private String category;
    /** a string that represents the shipping center for the order */
    private String shippingCenter;
    /** a string that represents the section for the order*/
    private String section;
    /** a string that represents the delivery truck for the order*/
    private String deliveryTruck;

    // define a public, static, final poison pill so that it has global access across all classes to be used as an "end of orders" signal
    /** an Order that represents an "end of orders" signal to be used for processing completions */
    public static final Order PoisonPill = new Order("PoisonPill","","","","","","");

    /**
     * Constructor for the order class. Initializes order with address information, name of person who ordered, and
     * item and category of the order
     *
     * @param streetAddress street address of order
     * @param city city address of order
     * @param state state address of order
     * @param zip zipcode section of address of order
     * @param name name of person creating order
     * @param item item that is being "ordered"
     * @param category category the item is part of
     */
    public Order(String streetAddress, String city, String state, String zip, String name, String item, String category) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.name = name;
        this.item = item;
        this.category = category;
    }

    /**
     * Getter for the street address
     *
     * @return string representing street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Getter for the city section of address
     *
     * @return string representing city section of address
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter for state section of address
     *
     * @return string representing state section of address
     */
    public String getState() {
        return state;
    }

    /**
     * Getter for zipcode section
     *
     * @return string representing zipcode
     */
    public String getZip() {
        return zip;
    }

    /**
     * Getter for name of person who ordered
     *
     * @return string representing the name of person who ordered
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for item of the order
     *
     * @return string representing item of order
     */
    public String getItem() {
        return item;
    }

    /**
     * Getter for category of the order
     *
     * @return string representing category of order
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter for shipping center order is being sent to
     *
     * @return string representing shipping center order is being sent to
     */
    public String getShippingCenter() {
        return shippingCenter;
    }

    /**
     * Setter for shipping center order is being sent to
     *
     * @param shippingCenter string representing shipping center order is being sent to
     */
    public void setShippingCenter(String shippingCenter) {
        this.shippingCenter = shippingCenter;
    }

    /**
     * Getter for shipping center section order is being sent to
     *
     * @return string representing shipping center section order is being sent to
     */
    public String getSection() {
        return section;
    }

    /**
     * Setter for shipping center section order is being sent to
     *
     * @param section string representing shippeg center order is being sent to
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Getter for delivery truck order is being sent to
     *
     * @return string representing shipping center order is being sent to
     */
    public String getDeliveryTruck() {
        return deliveryTruck;
    }

    /**
     * Setter for delivery truck order is being sent to
     *
     * @param deliveryTruck string representing delivery truck order is being sent to
     */
    public void setDeliveryTruck(String deliveryTruck) {
        this.deliveryTruck = deliveryTruck;
    }

    /**
     * Boolean helper function for detectingPoisonPill order
     *
     * @return true meaning PoisonPill has been detected, false otherwise
     */
    public boolean shouldTerminate() {
        return this.getStreetAddress().equals("PoisonPill");
    }

    // format output so all order details are included in the display for the DeliveryTruck's output
    /**
     * The toString method is an overridden method that formats an order in the correct way
     *
     * @return String format of order
     */
    @Override
    public String toString() {
        // check to see if poison pill detected (orders done)
        if (this == PoisonPill) {
            return "End of orders";
        }
        if (this.shouldTerminate()) return "";

        return String.format("Delivery Address: %s, %s, %s %s\n" +
                        "Name: %s\n" +
                        "Item: %s\n" +
                        "Category: %s\n" +
                        "Shipping Center: %s\n" +
                        "Section: %s\n" +
                        "Truck: %s\n",
                streetAddress, city, state, zip, name, item, category, shippingCenter, section, deliveryTruck);
    }
}