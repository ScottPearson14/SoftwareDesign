# S5_Scoreboard
[Home](Home)

## Problem Statement
For this problem, a class hierarchy must be designed to create a software scoreboard system that can handle a variety of sporting games: football, basketball, soccer, and hockey. First, a class hierarchy must be designed to allow the scoreboard to keep track of scores for the different sports. The classes must allow users to set the teams, get the teams, get the scores for each team, add scores, get scoring methods, start the game, end the current period of the game, get the current period of the game, get the length of the period of a game, get the name of the period of a game, tell whether the game is over, and determine the winning team once the match is complete. Next, the program must have a command line interface to allow users to select the game from the hierarchy above and interact with the scoreboard to keep the score of the given game. The interface must be written using polymorphism. For example, the Football class should not have a method that prints out portions of the user interface.


## Developer Documentation
My code for this specific problem contains 8 classes in total. The base class for this program is the Game class, an abstract class that represents a general sports game. The Game class is initialized by a Team class that acts as teams for the sporting games. A ScoringMethods class also exists that represents methods for scoring in a game. Also, the Game class has 4 subclasses that act as the different sports that the scoreboard is capable of running - Football, Basketball, Soccer, and Hockey classes. The final class of the program is the Main driver class that represents the actual "scoreboard" that has a command line interface where users can run the scoreboard. 

Here is a more in-depth explanation of each class along with their attributes and methods:

1. **Game class**: The Game class is an abstract class that represents a general sports game. The class defines common functionalities and attributes that sports share in common.

attributes: 

- **homeTeam**: a final Team variable that represents the home team in a sporting game
- **awayTeam**: a final Team variable that represents the away team in a sporting game
- **currPeriod**: an int variable that represents the current period in a sporting game (initialized to 1 since it be the 1st period when the game is created)
- **isGameOver**: a boolean variable that represents the if a game is ongoing or over (initialized to false since the game will be ongoing once the game is instantiated)

methods:

- the Game class has a constructor that initializes the home and away team as well as getters that get the attributes.
- The class has 4 abstract methods that have to be implemented by each specific game subclass. These are common methods shared across all sports games: 
- **getScoringMethods**: gets the scoring methods for a game
- **getPeriodLength**: gets the length of periods for a game
- **getPeriodName**: gets the name of the periods for a game
- **getMaxPeriods**: gets the maximum number of periods in a game
- **startGame**: starts the game by initializing the current score and period along with resetting the the game state so scores are reset and the game is ongoing.
- **endPeriod**: Ends the current period of the game. It either increments to the next period or ends the game if the maximum period count is reached
- **addScore**: Adds points to specified team's score based on the scoring method. It only adds score if the game is still ongoing
- **getWinningTeam**: Determines the winning team based on comparing scores

2. **Team class**: The team class is a class that represents a sports team with a name and score

attributes: 

- **name**: a final String that represents the name of a team
- **score**: an int that represents the current score of a team

methods:

- a constructor that constructs a team with a specified name. It initializes the score of the team to 0.
- Getters for the attributes of the class
- **addPoints**: adds points to the specified team
- **resetScore**: resets the score of a specified team to 0

3. **ScoringMethods class**: The ScoringMethods class is a class that represents the scoring methods for scoring in a sports game.

attributes: 

- **name**: a final String that represents the name of scoring methods for a sports game
- **points**: an int that represents the points awarded for scoring methods

methods:

- a constructor that constructs the scoring methods with a specified name and point value
- Getters for the attributes of the class

4. **Main class**: The Main class is the entry point of the scoreboard program. It allows users to select a sport, enter team names, and interactively keep score of the game through the command-line interface. It has one main method that prompts users to select a sport to play, pick the team names for the home and away teams, and manage the score of the game.It provides a menu interface where users can perform scoring methods and progress of game periods until the game is over.

5. **Football class**: The Football class is a class that represents a football game. This class extends the Game class to inherit common properties and attributes.

attributes: 

- **scoringMethods**: a final list of scoring methods specific to football

methods:

- a constructor that creates a football game with specified teams (home and away) and initializes scoring methods
- getScoringMethods: Overridden method that retrieves the list of scoring methods for football games
- getPeriodLength: Overridden method that retrieves the length of a period in a football game in minutes
- getPeriodName: Overridden method that retrieves the name of periods in a football game
- getMaxPeriod: Overridden method that retrieves the maximum number of periods in a football game

6. **Basketball class**: The Basketball class is a class that represents a basketball game. This class extends the Game class to inherit common properties and attributes.

attributes: 

- **scoringMethods**: a final list of scoring methods specific to basketball

methods:

- a constructor that creates a basketball game with specified teams (home and away) and initializes scoring methods
- getScoringMethods: Overridden method that retrieves the list of scoring methods for basketball games
- getPeriodLength: Overridden method that retrieves the length of a period in a basketball game in minutes
- getPeriodName: Overridden method that retrieves the name of periods in a basketball game
- getMaxPeriod: Overridden method that retrieves the maximum number of periods in a basketball game

7. **Soccer class**: The Soccer class is a class that represents a soccer game. This class extends the Game class to inherit common properties and attributes.

attributes: 

- **scoringMethods**: a final list of scoring methods specific to soccer

methods:

- a constructor that creates a soccer game with specified teams (home and away) and initializes scoring methods
- getScoringMethods: Overridden method that retrieves the list of scoring methods for soccer games
- getPeriodLength: Overridden method that retrieves the length of a period in a soccer game in minutes
- getPeriodName: Overridden method that retrieves the name of periods in a soccer game
- getMaxPeriod: Overridden method that retrieves the maximum number of periods in a soccer game

8. **Hockey class**: The Hockey class is a class that represents a hockey game. This class extends the Game class to inherit common properties and attributes.

attributes: 

- **scoringMethods**: a final list of scoring methods specific to hockey

methods:

- a constructor that creates a hockey game with specified teams (home and away) and initializes scoring methods
- getScoringMethods: Overridden method that retrieves the list of scoring methods for hockey games
- getPeriodLength: Overridden method that retrieves the length of a period in a hockey game in minutes
- getPeriodName: Overridden method that retrieves the name of periods in a hockey game
- getMaxPeriod: Overridden method that retrieves the maximum number of periods in a hockey game


## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S5_Scoreboard/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam2/S5_Scoreboard/doc/S5_Scoreboard_UML.png?ref_type=heads)

## User Documentation
Here are the steps to use/run the program:
1. Go to the Main.java file (the driver program for the scoreboard)
3. Compile and run the program
4. Users will be prompted to select a sport by entering a number between 1-4 (select the sport of your choosing)
5. Enter the name of the home team
6. Enter the name of the away team
7. The state of the game will be displayed in the console. You will see the home team name and score along with the away team name and score. You will see the current period of the game, and you will see a menu of options to press to advance the mechanics of the game (a team scores a certain way and a way to end the period)
8. The game will continue as long as the current period doesn't exceed the max periods in a game
9. Once the game is over, The final score will be shown along with the home team and away team scores. As long as one team has more points that the other, a winner will be displayed with the name of the team that had more points. If it is a tie, nothing will show up since there is no winner.

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/S5_Scoreboard/src?ref_type=heads)
