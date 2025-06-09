// ListTest class to demonstrate List capabilities.

import com.deitel.datastructurs.EmptyListException;
import com.deitel.datastructurs.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ListJunitTest class is a class that tests the validity and accuracy of the List class for inserting and deleting
 * elements. The class uses JUnit 5 for ensuring proper functionality for inserting/deleting elements from the front,
 * back, and specific positions in the list
 *
 * @version 1.0.0, 2, Dec 2024
 * @author Scott Pearson
 */
public class ListJunitTest {
    /**
     * Tests inserting and deleting elements at various positions in the list
     */
    @Test
    public void testInsertAndDelete() {
        List<Integer> list = new List<>();

        // test inserting at the front and back of the list
        list.insertAtFront(10);
        list.insertAtBack(12);
        list.insertAtFront(8);
        list.insertAtBack(14);
        list.print();

        // assert that the elements are inserted properly
        assertEquals(8, list.removeFromFront(), "First element should be 8");
        assertEquals(10, list.removeFromFront(), "First element should be 10");

        // add some values back into the list for later testing
        list.insertAtFront(9);
        list.insertAtBack(89);
        list.print();

        // test insert at specific positions
        list.insertAtSpecificPosition(1, 4);
        list.insertAtSpecificPosition(3, 13);
        list.print();

        // assert that elements are removed at specific positions properly
        assertEquals(4, list.removeAtSpecificPosition(1), "Removed element at position 1 should be 4");
        assertEquals(13, list.removeAtSpecificPosition(2), "Removed element at position 1 should be 13");

        // test edge cases
        assertEquals(9, list.removeFromFront(), "First element should be 9");
        assertEquals(89, list.removeFromBack(), "Last element should be 89");

    }

    /**
     * Tests empty list operations
     */
    @Test
    public void testEmptyList() {
        List<Integer> list = new List<>();
        assertTrue(list.isEmpty(), "List should initially be empty");

    }

}
