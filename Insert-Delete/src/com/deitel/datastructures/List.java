// Base code from Textbook Fig. 21.3: List.java
// ListNode and List class declarations.
package com.deitel.datastructurs;

// class to represent one node in a list

/**
 * The ListNode<T> class is a class that represents a node in a list
 *
 * @param <T> data for a Node
 *
 * @version 1.0.0, 3, Dec 2024
 * @author Scott Pearson
 */
class ListNode<T> {
    // package access members; List can access these directly
    /** T data for a given node */
    T data; // data for this node
    /** ListNode<T> is a reference to the next node in the list */
    ListNode<T> nextNode; // reference to the next node in the list

    /**
     * Constructor that creates a ListNode that refers to object
     */
    ListNode(T object) {
        this(object, null); // initialize the node as null
    }

    /**
     * Constructor that creates a ListNode that refers to the specified object and to the next ListNode
     */
    ListNode(T object, ListNode<T> node) {
        data = object;
        nextNode = node;
    }

    /**
     * Getter for the data in a node
     *
     * @return reference for the data in a node
     */
    T getData() {
        return data;
    }

    /**
     * Getter for the next node in the list
     *
     * @return reference to the next node in the list
     */
    ListNode<T> getNext() {
        return nextNode;
    }
} // end class ListNode<T>

// class List definition
/**
 * The List<T> class is a class that represents a List definition
 *
 * @param <T> data for a node
 *
 * @version 1.0.0, 3, Dec 2024
 * @author Scott Pearson
 */
public class List<T> {
    /** ListNode<T> that represents the first node in the list */
    private ListNode<T> firstNode;
    /** ListNode<T> that represents the last node in the list */
    private ListNode<T> lastNode;
    /** String that is used when printing the list */
    private String name; // string like "list" used in printing

    /**
     * Constructor that creates an empty List with "list" as the name
     */
    public List() {
        this("list");
    }


    /**
     * Constructor creates an empty List with a name
     *
     * @param listName String that represents the name of the list
     */
    public List(String listName) {
        name = listName;
        firstNode = lastNode = null; // initialize first and last name to null
    }

    /**
     * Method to insert item at front of the List
     *
     * @param insertItem T data to be inserted in the List
     */
    public void insertAtFront(T insertItem) {
        if (isEmpty()) // firstNode and lastNode refer to same object
            firstNode = lastNode = new ListNode<T>(insertItem);
        else // firstNode refers to new node
            firstNode = new ListNode<T>(insertItem, firstNode);
    }


    /**
     * Method to insert item at end of List
     *
     * @param insertItem T data to be inserted in the List
     */
    public void insertAtBack(T insertItem) {
        if (isEmpty()) // firstNode and lastNode refer to same object
            firstNode = lastNode = new ListNode<T>(insertItem);
        else // lastNode's nextNode refers to new node
            lastNode = lastNode.nextNode = new ListNode<T>(insertItem);
    }


    /**
     * Method to remove first node from List
     *
     * @return T data node that is to be removed from the List
     * @throws EmptyListException if List is empty
     */
    public T removeFromFront() throws EmptyListException {
        if (isEmpty()) // throw exception if List is empty by calling isEmpty() method
            throw new EmptyListException(name);

        T removedItem = firstNode.data; // retrieve data being removed

        // update references firstNode and lastNode
        if (firstNode == lastNode)
            firstNode = lastNode = null;
        else
            firstNode = firstNode.nextNode;

        return removedItem; // return removed node data
    }

    // remove last node from List
    /**
     * Method to remove last node from List
     *
     * @return T data node that is to be removed from the List
     * @throws EmptyListException if List is empty
     */
    public T removeFromBack() throws EmptyListException {
        if (isEmpty()) // throw exception if List is empty by calling isEmpty() method
            throw new EmptyListException(name);

        T removedItem = lastNode.data; // retrieve data being removed

        // update references firstNode and lastNode
        if (firstNode == lastNode)
            firstNode = lastNode = null;
        else // locate new last node
        {
            ListNode<T> current = firstNode; // create current node and set to firstNode position

            // loop while current node does not refer to lastNode
            while (current.nextNode != lastNode)
                current = current.nextNode;

            lastNode = current; // current is new lastNode
            current.nextNode = null;
        }

        return removedItem; // return removed node data
    }

    /**
     * Method to determine whether list is empty
     *
     * @return true is List is empty, false otherwise
     */
    public boolean isEmpty() {
        return firstNode == null; // return true if list is empty
    }


    /**
     * Method to output List contents
     */
    public void print() {
        if (isEmpty()) {
            System.out.printf("Empty %s%n", name);
            return; // exit method since print is completed
        }

        System.out.printf("The %s is: ", name); // print out List name
        ListNode<T> current = firstNode; // set current node to firstNode

        // while not at end of list, output current node's data
        while (current != null) {
            System.out.printf("%s ", current.data);
            current = current.nextNode;
        }

        System.out.println(); // skip to next line for formatting nicely
    }

    /**
     * Method that allows a node to be inserted at any position within the Linked List
     *
     * @param position int that represents the index of where the new Node will be inserted in the Linked List
     * @param insertListItem T data that represents the numerical value that wil be added to the Linked List
     */
    public void insertAtSpecificPosition(int position, T insertListItem) {
        // check to see if insertion is valid
        if (position < 0) {
            throw new IndexOutOfBoundsException("Invalid index position");
        }

        // insert at front of list if the index position is 0
        if (position == 0) {
            insertAtFront(insertListItem); // call method to insert node at beginning of list
            return; // exit method since insertion is completed
        }

        // create current node that references the firstNode
        ListNode<T> current = firstNode;
        int index = 0; // initialize a counter for the index to keep track of the current position

        // throw an error if we reach end of list before specific position
        if (current == null) {
            throw new IndexOutOfBoundsException("Position of current out of range");
        }

        // traverse through the list until we reach node right before desired position
        while (current != null && index < position - 1) {
            current = current.nextNode; // update current to be the next node
            index++; // increment position index counter
        }

        // create new node that will hold the value of the item to be inserted into the list
        ListNode<T> newNode = new ListNode<>(insertListItem, current.nextNode);
        current.nextNode = newNode; // update current node's next node to point to newNode

        // if newNode's next pointer is null, this means that newNode is now the last node in the list
        if (newNode.nextNode == null) {
            lastNode = newNode; // update lastNode's reference to point to newNode
        }
    }

    /**
     * Method that allows a node to be removed at a specific position in a Linked List
     *
     * @param position int that represents the index of the node to be removed
     * @return the data of the removed node
     */
    public T removeAtSpecificPosition(int position) {
        // check to see if specific position is invalid or if the list is empty
        if (position < 0 || isEmpty()) {
            throw new IndexOutOfBoundsException("Invalid index position or empty list");
        }

        // if the position is 0, remove the first node of the list
        if (position == 0) {
            return removeFromFront(); // call method to remove node from the front
        }

        // create a current node to traverse the list
        ListNode<T> current = firstNode; // set current to the firstNode
        int index = 0; // increment position index counter

        // traverse through the list until we reach the node right before the specific position or end of the list
        while (current != null && index < position - 1) {
            current = current.nextNode; // set current to next node
            index++; // increment the current index position counter
        }

        // check if specific position is out of range (see if current is null or next node is null)
        if (current == null || current.nextNode == null) {
            throw new IndexOutOfBoundsException("Position of current out of range");
        }

        // create variable to store the data of the node that will be removed
        T removedItem = current.nextNode.data;

        // bypass the node to be removed by updating the next pointer of the current node to skip over node being removed
        // and point to node after
        current.nextNode = current.nextNode.nextNode;

        // if removed node was the last node in the list, update the lastNode pointer to point to current node
        if (current.nextNode == null) {
            lastNode = current; // current node is now the last node
        }
        // return the data of the removed node
        return removedItem;

    }

} // end class List<T>
