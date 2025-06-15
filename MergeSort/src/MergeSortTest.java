import java.util.Arrays;
import java.util.Random;

/**
 * The MergeSortTest class is a class that is used to test the merge sort algorithm in my MergeSort class.
 * This acts as the main driver class of my MergeSort program. It generates a random array, sorts it using the
 * MergeSort class, and displays the array before and after sorting.
 *
 * @version 1.0.0, 3, Dec 2024
 * @author Scott Pearson
 *
 */
public class MergeSortTest {
    /**
     * The main method is the driver that tests the MergeSort algorithm. It generates a random array, prints it,
     * sorts it, and prints the sorted result
     *
     * @param args Command-line arguments (none)
     */
    public static void main(String[] args) {
        // generate an array with 100 random integers between 0 and 1000
        int[] array = generateRandomArray(100, 0, 1000);

        // print the original array
        System.out.println("Original array: ");
        System.out.println(Arrays.toString(array)); // convert array to string for readability

        // sort the array using the MergeSort class
        MergeSort.mergeSort(array, 0, array.length - 1);

        // print the sorted array
        System.out.println("Sorted array: ");
        System.out.println(Arrays.toString(array)); // convert array to string for readability
    }

    /**
     * Method that generates an array of 100 random integers with values between 0 and 1000
     *
     * @param size the number of elements in the array
     * @param min the minimum value for the random integers
     * @param max the maximum value for the random integers
     * @return an array of randomly generated integers within the specified range
     */
    public static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random(); // create Random object for generating random numbers
        int[] array = new int[size]; // initialize array with specified size

        // Populate the array with random integers between the min and max values
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min; // random number between min and max
        }
        return array; // return the randomly generated array
    }
}