# S52_WordleGUI
[Home](Home)

## Problem Statement
For this problem, a java class must be created to construct a Wordle-like game. For this game, users have to guess a 5-letter word. The program allows users six attempts to guess the correct word, providing color-coded feedback to the user based on how close their guess is. The game features a graphical user interface (GUI) designed using Java Swing, and the logic of the program handles user input, word validation, and visual feedback.

## Developer Documentation
The Wordle game is implemented using a single class: WordleGame. This class extends JFrame to create the GUI. There is also a main driver class that creates an instance of the Wordle game, sets title and size of the GUI, and launches the program. Here is an overview of the attributes and methods of the WordleGame class:

WordleGame Class: This is the core class that defines the behavior and GUI for the Wordle game. The class has many instance variables and methods. The instance variables consist of:
* correctGuess: String that sores the randomly selected 5-letter word that the user needs to guess
* attemptsRemaining: an int that tracks how many attempts users have left (defaulted to 6)
* wordList: A pre-defined list of 25 possible 5-letter words that the game will select from
* guessField: a JTextField where the user inputs their guesses
* gridGuessPanel: a JPanel containing the visual grid for displaying guessed letters and feedback for users
* letterBoxes: a list of JLabels that represent individual letters in the GUI
* solveButton a JButton that processes the user's guess when clicked

The key methods of the class consist of:
* WordleGame(): The constructor that initializes the game window, sets up the grid layout, and assigns the correct word from the list of words
* processGuessAndCheck(): method that handles user input validation, checks the guess against the correct word, and updates the grid with color-coded feedback
* updateGrid(String guess): Updates the visual grid based on the user's guess, making letters as either green (correct letter in correct location, yellow (correct letter in wrong location), or gray (letter not in correct word). It also accounts for multiple occurrences of the same letter through using a two-pass system. The first pass identifies correct letters that are in the correct location (green letters) and removes them from consideration. The second pass marks remaining correct letters that are in the wrong position (yellow letters) which ensures that no double-counting occurs for repeated letters.


## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam1/S52_WordleGUI/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam1/S52_WordleGUI/doc/WordleGUI_UML.png?ref_type=heads)

## User Documentation
1. Compile and run the WordleGame program in the main driver class
2. A graphical window will open. Click on the "User Guess:" panel and type in a 5-letter guess
3. The program will provide feedback after each user guess by highlighting guessed letters in the following colors:
- Green: correct letter in correct location
- Yellow: correct letter in wrong location
- Gray: letter not in correct word
4. The game ends when users either guess the correct word or when they run out of attempts (they get 6 attempts)

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam1/S52_WordleGUI/src?ref_type=heads)
