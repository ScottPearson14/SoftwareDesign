import java.util.HashMap;
import java.util.Map;

/**
 * The Easter class calculates the date of Easter for a given year using the Meeus/Jones/Butcher Gregorian algorithm.
 * It also includes a method that calculates the repeated dates of Easter over an entire 5,700,000-year cycle
 *
 * @version 1.0.0, 18, Sept 2024
 * @author Scott Pearson
 */
public class Easter {
    /** holds the year of Easter that is being calculated */
    private final int year; // make final since it shouldn't be changed once initialized
    /** holds the month of Easter that is being calculated */
    private int month;
    /** holds the day of Easter that is being calculated */
    private int day;

    /**
     * Constructs an Easter object for a given year through using the Meeus/Jones/Butcher Gregorian algorithm.
     *
     * @param year The year of the Easter date that is being calculated
     */
    public Easter(int year) {
        this.year = year;

        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19*a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2*e + 2*i - h - k) % 7;
        int m = (a + 11*h + 22*l) / 451;
        // use my attributes for updating the month and the day
        month = (h + l - 7*m + 114) / 31; // March = 3 and April = 4
        day = ((h + l - 7*m +114) % 31) + 1;
    }

    /**
     * Prints Easter date in format (month day)
     */
    public void printEasterDate() {
        String nameOfMonth;
        if (month == 3) {
            nameOfMonth = "March";
        } else if (month == 4){
            nameOfMonth = "April";
        } else {
            // this is impossible to not be March or April with current algorithm
            throw new IllegalArgumentException("Can't calculate month");
        }
        System.out.println("Easter: " + nameOfMonth + " " + day + ", " + year);
    }

    /**
     * Gets Easter date as a String to help for calculateEasterCycle method
     *
     * @return A string that represents the Easter date in format: (month day)
     */
    public String getEasterDate() {
        String easterMonth;
        if (month == 3) {
            easterMonth = "March";
        } else if (month == 4) {
            easterMonth = "April";
        } else {
            // this is impossible to not be March or April with current algorithm
            throw new IllegalArgumentException("Can't calculate month");
        }
        return easterMonth + " " + day;
    }

    /**
     * Calculates the repeated dates of Easter over an entire 5,700,000-year cycle and prints the number of times
     * Easter falls on each specific day
     *
     */
    // this method is static since it performs a calculation on a broad range of years and is not specific to a certain Easter object
    // this also allows the method to have global access and can be called without making an instance of an Easter object
    public static void calculateEasterCycle() {
        // create Hashmap to hold the counts of the Easter dates in the 5,700,000-year cycle
        Map<String, Integer> easterCount = new HashMap<String, Integer>();

        // loop over the 5,700,000-year cycle
        for (int yr = 1; yr <= 5700000; yr++) {
            Easter easter = new Easter(yr); // calculate easter for the given year (yr)
            String easterDate = easter.getEasterDate(); // call getEasterDate to get month & day of Easter as a String
            // update the easterCount Hashmap
            if (easterCount.containsKey(easterDate)) {
                int counter = easterCount.get(easterDate); // add a counter for if the date already exists
                easterCount.put(easterDate, counter + 1); // updates the count in the Hashmap

            } else {
                // initialize count to 1 if the date does not exist
                easterCount.put(easterDate, 1);
            }
        }
        // print results of dates using an advanced for loop going over each key in the keySet
        // (# of times Easter falls on each date)
        for (String dates: easterCount.keySet()) {
            Integer dateCounter = easterCount.get(dates); // get the count for the Easter dates
            System.out.println(dates + " - " + dateCounter);
        }
    }

}