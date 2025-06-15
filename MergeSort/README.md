# S18_MergeSort
[Home](Home)

## Problem Statement
For this problem, a unique MergeSort algorithm must be created in Java. The program must be able to undergo the following conditions:
1. Generate an array with 100 random integers between 0 and 1000
2. Print the original array to the screen
3. Use your merge sort algorithm to sort the array from lowest to highest
4. Print the sorted array to the screen

## Developer Documentation
My code for this specific problem contains 3 classes in total. Here is a more in-depth explanation of the classes:
1. **MergeSort**: This class implements my unique merge sort algorithm for sorting an array of integers. There are 2 methods in this class:
- mergeSort: This is a static method that sorts an array using my merge sort algorithm
- mergeSortedArray: This is a static method that merges 2 sorted sub arrays into a singular sorted subarray
2. **MergeSortTest**: This class is used to test the merge sort algorithm in my MergeSort class. This acts as the main driver class of my MergeSort program. It generates a random array, sorts it using the MergeSort class, and displays the array before and after sorting. There are 2 methods in this class:
- generateRandomArray: This is a method that generates an array of 100 random integers with values between 0 and 1000
- main: This is the driver method that tests the MergeSort algorithm. It generates a random array, prints it, sorts it, and prints the sorted result
3. **MergeSortJUnitTest**: This is a class that tests the validity and accuracy of the MergeSort class. This class uses JUnit 5 for ensuring proper functionality for sorting arrays. The class has 5 Tests in total:
- testMergeSort: Tests the merge sort algorithm by calling mergeSort on an unsorted array and comparing the array to an already sorted array
- alreadySortedArray: Tests the merge sort algorithm on an already sorted array to ensure that the algorithm still works. Compares the already sorted array to an expected sorted array
- testArrayWithDuplicates: Tests that the merge sort algorithm works on an unsorted array with duplicates
- testEmptyArray: Tests that the merge sort algorithm works for empty arrays
- testSingleElementArray: Tests that the merge sort algorithm works for single element arrays

## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S18_MergeSort/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam2/S18_MergeSort/doc/S18_MergeSort_UML.png?ref_type=heads)

## User Documentation
Here are the steps to use/run the program:
1. Compile and run the MergeSortTest class. The original randomly generated array will print to the screen. The MergeSort class will be called to run the merge sort algorithm, and the array will be printed to the screen again after the algorithm is run. The array will now be sorted.
2. Compile and run the MergeSortJUnitTest class. The already existing 5 tests will be run and will pass. If you wish to add more code to the MergeSort class, refer back to the MergeSortJUnitTest to ensure that no past tests were broken. Feel free to add more tests to this class as well. 

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S18_MergeSort/src?ref_type=heads)
