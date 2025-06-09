import java.util.Scanner;

/**
 * The Main class is the entry point of the scoreboard program
 * It allows users to select a sport, enter team names, and interactively keep score of the game
 * through the command-line interface
 *
 * @version 1.0.0, 30, Oct 2024
 * @author Scott Pearson
 */
public class Main {
    /**
     * The main method prompts users to select a sport to play, pick the team names for the home and away teams, and
     * manage the score of the game.
     * It provides a menu interface where users can perform scoring methods and progress of game periods until the
     * game is over
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select your sport");
        System.out.println("1. Football");
        System.out.println("2. Basketball");
        System.out.println("3. Soccer");
        System.out.println("4. Hockey");
        System.out.print("Enter choice (1-4): ");

        int userChoice = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        System.out.println("-------------------------------");

        System.out.print("Enter Home Team name: ");
        Team homeTeam = new Team(scanner.nextLine());

        System.out.print("Enter Away Team name: ");
        Team awayTeam = new Team(scanner.nextLine());

        Game game; // create game object
        // use switch case for game options
        // this is where polymorphism is used in my program
        switch (userChoice) {
            case 1:
                game = new Football(homeTeam, awayTeam);
                break;
            case 2:
                game = new Basketball(homeTeam, awayTeam);
                break;
            case 3:
                game = new Soccer(homeTeam, awayTeam);
                break;
            case 4:
                game = new Hockey(homeTeam, awayTeam);
                break;
            default:
                throw new IllegalArgumentException("Invalid game type (type # between 1 and 4)");
        }

        game.startGame(); // start the game
        // loop through the game while it is not over
        while (!game.isGameOver()) {
            System.out.println("---------------------------");
            // display the current score of both teams
            System.out.printf("%s - %d, %s - %d%n", homeTeam.getName(), homeTeam.getScore(), awayTeam.getName(), awayTeam.getScore());
            // display the current period
            System.out.println("Current " + game.getPeriodName() + ": " + game.getCurrPeriod());

            // display the menu options for scoring and ending the periods
            System.out.println("Menu:");

            int option = 1; // counter for menu options
            // advanced for loop for scoring methods available for chosen game
            for (ScoringMethods methods : game.getScoringMethods()) {
                System.out.printf("%d. %s %s%n", option++, homeTeam.getName(), methods.getName());
                System.out.printf("%d. %s %s%n", option++, awayTeam.getName(), methods.getName());
            }

            // add option to end current period
            System.out.printf("%d. End %s%n", option, game.getPeriodName());
            // prompt users to enter their choice
            System.out.print("Enter choice: ");
            int userAction = scanner.nextInt(); // read in the input of the user

            // Check if user chooses to end the current period
            if (userAction == option) {
                game.endPeriod(); // end current period and move on to next (if last period, game will end)
            } else {
                // calculate the index of the scoring method the user selects
                int methodIndex = (userAction - 1) / 2;
                // Retrieve the selected scoring method from the list
                ScoringMethods scoringMethods = game.getScoringMethods().get(methodIndex);

                Team scoringTeam; // Determine which team the score should be added to based on user input

                if (userAction % 2 == 1) {
                    // Odd number choice corresponds to the home team
                    scoringTeam = homeTeam;
                } else {
                    // Even number choice corresponds to the away team
                    scoringTeam = awayTeam;
                }
                // Add the points for the selected scoring method to the chosen team
                game.addScore(scoringMethods, scoringTeam);
            }
        }
        System.out.println("---------------------------");
        // Display the final score of the game
        System.out.printf("Final Score: %s - %d, %s - %d%n", homeTeam.getName(), homeTeam.getScore(), awayTeam.getName(), awayTeam.getScore());

        // Determine the winning team, if any
        Team winningTeam = game.getWinningTeam();
        if (winningTeam != null) {
            // Display the winning team's name if there is one (not a tie)
            System.out.println("Winner: " + winningTeam.getName());
        }

    }
}