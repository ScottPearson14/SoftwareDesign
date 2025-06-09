// Base code from Textbook Fig. 21.4: EmptyListException.java
// Class EmptyListException declaration.
package com.deitel.datastructurs;

/**
 * The EmptyListException class is a class that is used for exceptions when a List is empty. This class extends
 * RunTimeException.
 *
 * @version 1.0.0, 3, Dec 2024
 * @author Scott Pearson
 */
public class EmptyListException extends RuntimeException {
    // constructor
    public EmptyListException() {
        this("List"); // call other EmptyListException constructor
    }

    // constructor
    public EmptyListException(String name) {
        super(name + " is empty"); // call superclass constructor
    }
} // end class EmptyListException
