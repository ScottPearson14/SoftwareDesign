# S1_Computus_Hard
[Home](Home)

## Problem Statement
For this problem, a java class must be created with the power to calculate the month and day of Easter  in the Gregorian calendar for a given year. The date of Easter varies each year, but it can be determined by a complex algorithm: the Meeus/Jones/Butcher Gregorian algorithm. The order of the dates of Easter repeat themselves every 5,700,000 years. This program provides an automated method that calculates the reoccurrences of Easter dates over this extensive cycle. This program also contains testing capabilities to verify the correctness of Easter date calculations against known historical data.

## Developer Documentation
My code for this specific problem contains a central class, a main driver class for user input, and a testing class to test the correctness of Easter date calculations.

Here is an overview of the classes along with some of their main instance variables/methods:
1. Easter Class: this is the central class of my program that calculates the date of Easter using the Meeus/Jones/Butcher Gregorian algorithm. It contains 3 attributes:
- year: an int to hold the year of Easter
- month: an int to hold the month of Easter
- day: an int to hold the day of Easter

Some of its main methods include:
- calculateEaster: uses the Meeus/Jones/Butcher Gregorian algorithm to calculate Easter for a given year
- printEasterDate: prints calculated Easter date as a string
- getEasterDate: returns calculated Easter date as a string
- calculateEasterCycle: a static method that calculates and prints the frequency of Easter dates over a 5,700,000 year cycle saying how often each date occurs

2. Main class: this is the driver class for my program. It prompts users for a year, creates an Easter object for the given year, and displays the calculated Easter date. It also prints out the easterCycle calculation for repeated Easter dates. 

3. EasterTest class: this class is strictly meant for verifying the correctness for calculating Easter dates using JUnit Testing. It takes dates from 1900-1930 and can be found at this website: 

https://www.census.gov/data/software/x13as/genhol/easter-dates.html#par_textimage_924432483 

## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam1/S1_Computus_Hard/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam1/S1_Computus_Hard/doc/Computus_UML.png?ref_type=heads)

## User Documentation
Here are the steps to use/run the program:
1. Have Java installed on the machine you are using
2. Go to the Main.java file (the driver program)
3. Run the program. You will be prompted to enter in a year. The program will calculate and display the date of Easter for the inputted year.
4. In the terminal, the program will also show the frequency of Easter dates over a 5,700,000 year cycle since the program will run the calculateEasterCycle method.
5. You can head over to Easter.Test.java to observe the JUnit Testing with historical data for correctness.
   
## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam1/S1_Computus_Hard/src?ref_type=heads)
