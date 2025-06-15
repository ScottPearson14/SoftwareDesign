import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The MergeSortJUnitTest class is a class that tests the validity and accuracy of the MergeSort class. This class uses
 * JUnit 5 for ensuring proper functionality for sorting arrays. The class has 5 Tests in total
 *
 * @version 1.0.0, 4, Dec 2024
 * @author Scott Pearson
 *
 */
public class MergeSortJUnitTest {
    /**
     * Tests the merge sort algorithm by calling mergeSort on an unsorted array and comparing the
     * array to an already sorted array
     */
    @Test
    public void testMergeSort() {
        // unsorted array that will be used to test the Merge Sort algorithm
        int[] unsortedArray = {21, 102, 12, 34, 42, 60, 56, 81, 99, 79};
        // array that is already sorted that will be used as a comparison to test the validity of the Merge Sort algorithm
        int[] expectedSortedArray = {12, 21, 34, 42, 56, 60, 79, 81, 99, 102};

        // call merge sort on the unsorted array
        MergeSort.mergeSort(unsortedArray, 0, unsortedArray.length - 1);
        // compare the array that just got sorted with the already sorted array
        assertArrayEquals(unsortedArray, expectedSortedArray, "The array should be sorted in ascending order");
    }

    /**
     * Tests the merge sort algorithm on an already sorted array to ensure that the algorithm still works. Compares the
     * already sorted array to an expected sorted array
     */
    @Test
    public void alreadySortedArray() {
        // already sorted array that will be used to test the Merge Sort algorithm
        int[] alreadySorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // array that is already sorted that will be used as a comparison to test the validity of the Merge Sort algorithm
        int[] expectedSortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // call merge sort on the unsorted array
        MergeSort.mergeSort(alreadySorted, 0, alreadySorted.length - 1);
        // compare the array that just got sorted with the already sorted array
        assertArrayEquals(alreadySorted, expectedSortedArray, "They should already be in ascending order");
    }

    /**
     * Tests that the merge sort algorithm works on an unsorted array with duplicates
     */
    @Test
    public void testArrayWithDuplicates() {
        // unsorted array with duplicates that will be used to test the Merge Sort algorithm
        int[] unsortedArrayWithDuplicates = {21, 102, 12, 21, 21, 60, 56, 79, 99, 79};
        // array with duplicates that is already sorted that will be used as a comparison to test the
        // validity of the Merge Sort algorithm
        int[] expectedSortedArray = {12, 21, 21, 21, 56, 60, 79, 79, 99, 102};

        // call merge sort on the unsorted array with duplicates
        MergeSort.mergeSort(unsortedArrayWithDuplicates, 0, unsortedArrayWithDuplicates.length - 1);
        // compare the array that just got sorted with the already sorted array
        assertArrayEquals(unsortedArrayWithDuplicates, expectedSortedArray, "The array should be sorted in ascending order");
    }

    /**
     * Tests that the merge sort algorithm works for empty arrays
     */
    @Test
    public void testEmptyArray() {
        // empty array that will be used to test the Merge Sort algorithm
        int[] emptyArray = {};
        // expected empty array that will be used as a comparison to test the validity of the Merge Sort algorithm
        int[] expectedSortedArray = {};

        // call merge sort on the empty array
        MergeSort.mergeSort(emptyArray, 0, emptyArray.length - 1);
        // compare the array that just got sorted with the already sorted array
        assertArrayEquals(emptyArray, expectedSortedArray, "The array should be sorted in ascending order");
    }

    /**
     * Tests that the merge sort algorithm works for single element arrays
     */
    @Test
    public void testSingleElementArray() {
        // single element array that will be used to test the Merge Sort algorithm
        int[] singleElementArray = {46920};
        // expected array that will be used as a comparison to test the validity of the Merge Sort algorithm
        int[] expectedSortedArray = {46920};

        // call merge sort on the empty array
        MergeSort.mergeSort(singleElementArray, 0, singleElementArray.length - 1);
        // compare the array that just got sorted with the already sorted array
        assertArrayEquals(singleElementArray, expectedSortedArray, "The array should be sorted in ascending order");
    }
}
