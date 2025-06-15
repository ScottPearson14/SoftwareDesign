# 21-26_InsertDelete_Medium
[Home](Home)

## Problem Statement
For this problem, one must update and expand upon an already existing textbook problem to be capable of inserting/deleting anywhere in a LinkedList in Java. The base code textbook problem is from fig21_03_05.

## Developer Documentation
My code for this specific problem expansion involves 5 java classes in total. However, only the List<T> class has updated code, 2 added methods, that actually make the implementations so the program is able to insert/delete anywhere within a linked list. The main driver program, ListTest, is also updated so that there are tests that the 2 new added methods are working properly. Also, for creativity, I decided to implement a class, ListJUnitTest, that uses JUnit testing for the updated program methods and already existing methods. Here is a a more in-depth breakdown of the classes, their attributes, and methods:
1. **ListNode<T>**: this is a class that represents a node in a list. 

Attributes for the class consist of:
- data: T data for a given node
- nextNode: ListNode<T> is a reference to the next node in the list

Methods for this class consist of:
- a constructor that creates a ListNode that refers to object
- a constructor that creates a ListNode that refers to the specified object and to the next ListNode
- getData: Getter for the data in a node
- getNext: Getter for the next node in the list

2. **List<T>**: class that represents a List definition.

Attributes for this class consist of:
- firstNode: ListNode<T> that represents the first node in the list
- lastNode: ListNode<T> that represents the last node in the list
- name: String that is used when printing the list

Methods for this class consist of:
- a constructor that creates an empty List with "list" as the name
- a constructor creates an empty List with a name
- insertAtFront: Method to insert item at front of the List
- insertAtBack: Method to insert item at end of the List
- removeFromFront: Method to remove first node from List
- removeFromBack: Method to remove last node from List
- isEmpty: Method to determine whether list is empty
- print: Method to output List contents
- **insertAtSpecificPosition**: This is my own implemented method that allows a node to be inserted at any position within the Linked List
- **removeAtSpecificPosition**: This is my own implemented method that allows a node to be removed at a specific position in a Linked List
3. **EmptyListException**: class that is used for exceptions when a List is empty. This class extends RunTimeException.
4. **ListTest**: The ListTest class is a class that acts as the main driver program for the List class data structure. This class calls methods form the List class and has print messages to ensure that methods are working properly.
5. ****ListJUnitTest****: This is my own class that tests the validity and accuracy of the List class for inserting and deleting elements. The class uses JUnit 5 for ensuring proper functionality for inserting/deleting elements from the front, back, and specific positions in the list


## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/21-26_InsertDelete_Medium/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam2/21-26_InsertDelete_Medium/doc/21-26_InsertDelete_Medium_UML.png?ref_type=heads)

## User Documentation
Here are the steps to use/run the program:
1. Navigate to the ListTest class and run the program. The list should be printed to the screen following each of the methods that are used for unit testing. You can scroll through the data printed to the console to ensure that all of the methods are working and properly printed to the screen.
2. Navigate over to the ListJUnitTest class and run the program. This program contains 2 tests - 1 of them tests all of the insert/delete tests (including the 2 new methods that were added for removing and inserting anywhere in the list), and the 2nd test tests the already existing isEmpty() method.
3. If you wish to add more methods in any of the classes, go over to the ListJUnitTest to implement more tests to see if your new methods work/make sure you didn't break old tests.

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/21-26_InsertDelete_Medium/src?ref_type=heads)
