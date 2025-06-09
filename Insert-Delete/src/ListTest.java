// Base code from Textbook Fig. 21.5: ListTest.java
// ListTest class to demonstrate List capabilities.

import com.deitel.datastructurs.EmptyListException;
import com.deitel.datastructurs.List;

/**
 * The ListTest class is a class that acts as the main driver program for the List class data structure. This class
 * calls methods form the List class and has print messages to ensure that methods are working properly.
 *
 * @version 1.0.0, 3, Dec 2024
 * @author Scott Pearson
 */
public class ListTest {
    /**
     * Method that acts as the main driver method for the Linked List program. It calls methods in the List class and
     * has print messages to ensure the methods are working properly
     *
     * @param args Command-line args (none)
     */
    public static void main(String[] args) {
        List<Integer> list = new List<>();

        // insert integers in list
        list.insertAtFront(-1);
        list.print();
        list.insertAtFront(0);
        list.print();
        list.insertAtBack(1);
        list.print();
        list.insertAtBack(5);
        list.print();

        // Test insertAtSpecificPosition method
        System.out.println("\nTesting insertAtSpecificPosition method: ");
        try {
            list.insertAtSpecificPosition(3, 9); // insert 9 at index position 3
            list.print();
            list.insertAtSpecificPosition(0,15); // insert 15 at index position 0
            list.print();
            list.insertAtSpecificPosition(1, 18); // insert 18 at index position 1
            list.print();
            list.insertAtSpecificPosition(5, 99); // insert 99 at index position 5
            list.print();
            list.insertAtSpecificPosition(4, 77); // insert 77 at index position 4
            list.print();
            list.insertAtSpecificPosition(6, 77); // insert 77 at index position 4
            list.print();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // remove objects from list; print after each removal
        try {
            int removedItem = list.removeFromFront();
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

            removedItem = list.removeFromFront();
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

            removedItem = list.removeFromBack();
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

            removedItem = list.removeFromBack();
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

            // Test removeAtSpecificPosition method
            System.out.println("\nTesting removeAtSpecificPosition method: ");
            removedItem = list.removeAtSpecificPosition(2); // remove item at index position 2
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

            removedItem = list.removeAtSpecificPosition(3); // remove item at index position 3
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

            removedItem = list.removeAtSpecificPosition(0); // remove item at index position 0
            System.out.printf("%n%d removed%n", removedItem);
            list.print();

        } catch (EmptyListException emptyListException) {
            emptyListException.printStackTrace();
        }

    }
}