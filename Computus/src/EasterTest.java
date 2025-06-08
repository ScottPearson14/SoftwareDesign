import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The EasterTest class tests the validity and accuracy of Easter class to ensure the month and day of Easter are
 * properly calculated.
 * It uses JUnit5 to test how accurate the Easter date calculations are by comparing them with known Easter dates from:
 * https://www.census.gov/data/software/x13as/genhol/easter-dates.html#par_textimage_924432483
 *
 * @version 1.0.0, 18, Sept 2024
 * @author Scott Pearson
 */
public class EasterTest {
    /**
     * Tests the Easter class by comparing calculated Easter dates with known historical dates.
     * It iterates through a list of known dates, constructs and Easter object for each year, and
     * compares the resulting month and day with expected values
     */
    @Test
    public void testEasterDates() {
        // using list of known Easter dates from 1900-1930: dates are formatted as year, month, day
        String[][] knownDates = {
                {"1900", "April", "15"}, {"1901", "April", "7"}, {"1902", "March", "30"}, {"1903", "April", "12"},
                {"1904", "April", "3"}, {"1905", "April", "23"}, {"1906", "April", "15"}, {"1907", "March", "31"},
                {"1908", "April", "19"}, {"1909", "April", "11"}, {"1910", "March", "27"}, {"1911", "April", "16"},
                {"1912", "April", "7"}, {"1913", "March", "23"}, {"1914", "April", "12"}, {"1915", "April", "4"},
                {"1916", "April", "23"}, {"1917", "April", "8"}, {"1918", "March", "31"}, {"1919", "April", "20"},
                {"1920", "April", "4"}, {"1921", "March", "27"}, {"1922", "April", "16"}, {"1923", "April", "1"},
                {"1924", "April", "20"}, {"1925", "April", "12"}, {"1926", "April", "4"}, {"1927", "April", "17"},
                {"1928", "April", "8"}, {"1929", "March", "31"}, {"1930", "April", "20"}
        };
        // Test each known Easter date
        for (String[] date: knownDates) {
            int year = Integer.parseInt(date[0]); // parse year from String array
            String expectedMonth = date[1]; // gets the month of the Easter date
            int expectedDay = Integer.parseInt(date[2]); // parse date from String array

            // create new Easter object for given year for testing
            Easter easter = new Easter(year);
            String[] result = easter.getEasterDate().split(" "); // gets result from Easter object & splits w/ a space
            String resultMonth = result[0]; // gets the month from the result
            int resultDay = Integer.parseInt(result[1]); // parse the day from the result

            // asserts that the expected month and result month are the same
            assertEquals(expectedMonth, resultMonth, "Month should be correct for the year " + year);
            // asserts that the expected day and result day are the same
            assertEquals(expectedDay, resultDay, "Day should be correct for the year " + year);

        }


    }
}
