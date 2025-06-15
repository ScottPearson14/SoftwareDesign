/**
 * The MergeSort class is a class that implements my merge sort algorithm for sorting an array of integers
 *
 * @version 1.0.0, 3, Dec 2024
 * @author Scott Pearson
 */
public class MergeSort {
    /**
     * Static method that sorts an array using my merge sort algorithm
     *
     * @param arrayToBeSorted the array to that is to be sorted
     * @param beginning the starting index in the array/subarray
     * @param end the ending index in the array/subarray
     */
    public static void mergeSort(int[] arrayToBeSorted, int beginning, int end) {
        // check if current subarray has more than 1 element
        if (beginning < end) {
            int mid = (beginning + end) / 2; // calculate the middle index

            // Sort the left half of the subarray through recursive function call
            mergeSort(arrayToBeSorted, beginning, mid);
            // Sort the right half of the subarray through recursive function call
            mergeSort(arrayToBeSorted, mid + 1, end);
            // Merge the 2 sorted halves into a singular sorted subarray
            mergeSortedArray(arrayToBeSorted, beginning, mid, mid + 1, end);
        }
    }

    /**
     * Static method that merges 2 sorted sub arrays into a singular sorted subarray
     *
     * @param array original array that contains the sub arrays
     * @param leftStart the starting index of the first subarray
     * @param leftEnd the ending index of the first subarray
     * @param rightStart the starting index of the second subarray
     * @param rightEnd the ending index of the second subarray
     */
    public static void mergeSortedArray(int[] array, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        // determine the size of the left subarray and right subarray
        int nLeft = leftEnd - leftStart + 1; // size of the left subarray
        int nRight = rightEnd - rightStart + 1; // size of the right subarray

        // create temporary arrays to hold elements of the sub arrays
        int[] arrayLeft = new int[nLeft];
        int[] arrayRight = new int[nRight];

        // copy the elements from the original array into the temporary left array
        for (int i = 0; i < nLeft; i++) {
            arrayLeft[i] = array[leftStart + i];
        }

        // copy the elements from the original array into the temporary right array
        for (int j = 0; j < nRight; j++) {
            arrayRight[j] = array[rightStart + j];
        }

        // initialize indices for the left array, right array, and merged array
        int lIndex = 0; // index for arrayLeft
        int rIndex = 0; // index for arrayRight
        int mergedIndex = leftStart; // index for merged array in the original array

        // merge the elements from both sub arrays back into the original array
        // while the left index < left size and right index < right size
        while (lIndex < nLeft && rIndex < nRight) {
            if (arrayLeft[lIndex] <= arrayRight[rIndex]) {
                // if current element in left array is smaller or equal, take the element
                array[mergedIndex] = arrayLeft[lIndex];
                lIndex++; // move to the next element in the left array
            } else {
                // if the current element in the right array is smaller, take it
                array[mergedIndex] = arrayRight[rIndex];
                rIndex++; // move to the next element in the right array
            }
            mergedIndex++; // move to the next position in the merged array
        }

        // copy any remaining elements from the left array (happens when all elements from right have been merged, but
        // some elements in left array are still not sorted

        // while the left index < size of left subarray
        while (lIndex < nLeft) {
            array[mergedIndex] = arrayLeft[lIndex]; // place the current element from left array into original array
            lIndex++; // move to next element in left array
            mergedIndex++; // move to next position in merged array
        }

        // copy any remaining elements from the right array (happens when all elements from left have been merged, but
        // some elements in right array are still not sorted

        // while the right index < size of right subarray
        while (lIndex < nLeft) {
            array[mergedIndex] = arrayRight[lIndex]; // place the current element from right array into original array
            rIndex++; // move to next element in right array
            mergedIndex++; // move to next position in merged array
        }
    }
}