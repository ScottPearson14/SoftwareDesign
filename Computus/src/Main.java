import java.util.Scanner;

/**
 * The Main class is the driver class for the Easter date calculation. It prompts users to input year, and it outputs
 * the date Easter occurs on the given year. It also calls a method for calculating the 5,700,000-year Easter cycle
 * and prints the dates Easter occurs over this timeframe along with the counts of each date.
 *
 * @version 1.0.0, 18, Sept 2024
 * @author Scott Pearson
 */
public class Main {
    /**
     * Uses a scanner to prompt the user to enter a year, creates an Easter object, and displays the calculated
     * Easter date. It also displays the counts of Easter dates over a 5,700,000-year cycle
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Test for Part 1: calculates and prints Easter date when given a single user input
        System.out.print("Enter year: ");
        // this will be the year that will be used to calculate when Easter occurs
        int year = scanner.nextInt();
        // create an Easter instance
        Easter easter = new Easter(year);
        easter.printEasterDate();

        // Test for Part 2: calculate Easter cycle dates over 5,700,000-year cycle and print dates
        System.out.println("\nCalculating Easter dates over 5,700,000-year cycle: ");
        Easter.calculateEasterCycle();

        scanner.close();
    }
}