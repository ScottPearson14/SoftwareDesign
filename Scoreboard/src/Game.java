import java.util.List;

/**
 * The Game class is an abstract class that represents a general sports game. The clas does not define all the details
 * necessary for a specific type of game. Instead, it defines common functionalities and attributes that sports
 * share in common.
 *
 * @version 1.0.0, 30, Oct 2024
 * @author Scott Pearson
 *
 */
public abstract class Game {
    /** the home team in a sports game */
    private final Team homeTeam; // make final since it shouldn't change upon creation
    /** The away team in a sports game */
    private final Team awayTeam; // make final since it shouldn't change upon creation
    /** The current period in a game */
    private int currPeriod = 1; // initialize to first period since game will have just started
    /** A boolean flag representing the state of a game (if it is still going or over) */
    private boolean isGameOver = false; // initialize to false since game will have started

    /**
     * Constructor for the Game class. Initializes a game with specified home and away teams
     *
     * @param homeTeam The home team
     * @param awayTeam The away team
     */
    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
    // abstract methods that have to be implemented by each specific game subclass
    // these are common methods shared across all sports games

    /**
     * Abstract method that retrieves the list of scoring methods specific to a game
     *
     * @return A list of scoring methods for a specific game
     */
    public abstract List<ScoringMethods> getScoringMethods();

    /**
     * Abstract method that retrieves the length of each period in a game
     *
     * @return an int representation of the length of a period in minutes
     */
    public abstract int getPeriodLength();

    /**
     * Abstract method that retrieves the name of the period of a game
     *
     * @return a string representation of the name of the period
     */
    public abstract String getPeriodName();

    /**
     * Abstract method that retrieves the maximum number of periods in a game
     *
     * @return an int representation of the max number of periods
     */
    public abstract int getMaxPeriods();
    /**
     * Starts the game by initializing the current period and scores.
     * Resets the game state so the scores are reset and the game is not over
     */
    public void startGame() {
        currPeriod = 1; // set game to 1st period
        isGameOver = false; // game is not over since it just started
        homeTeam.resetScore(); // rest home team score to 0
        awayTeam.resetScore(); // reset away team score to 0
    }
    /**
     * Ends the current period of the game. It either increments to the next period or ends the game
     * if the maximum period count is reached
     *
     */
    public void endPeriod() {
        if (currPeriod >= getMaxPeriods()) {
            isGameOver = true; // game is over if max periods are reached
        } else {
            currPeriod++; // move on to next period since max periods haven't been reached
        }
    }

    /**
     * Adds points to specified team's score based on the scoring method
     * Only adds score if the game is still ongoing
     *
     * @param scoringMethods The scoring method used to add points
     * @param team The team that scored the points
     */
    public void addScore(ScoringMethods scoringMethods, Team team) {
        // only add points if game is ongoing
        if (!isGameOver) {
            team.addPoints(scoringMethods.getPoints()); // add points to team from scoring methods
        }
    }

    /**
     * Checks if the game is over
     *
     * @return True if game is over, false if game is ongoing
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Determines the winning team based on comparing scores
     *
     * @return the team with the higher score, or null if there is a tie
     */
    public Team getWinningTeam() {
        if (homeTeam.getScore() > awayTeam.getScore()) {
            return homeTeam; // return home team if score is higher
        }
        if (homeTeam.getScore() < awayTeam.getScore()) {
            return awayTeam; // return away team if score is higher
        }
        return null; // return null if scores are equal  (indicates a tie)
    }

    /**
     * Getter that retrieves the home team
     *
     * @return the home team
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * Getter that retrieves the away team
     *
     * @return the away team
     */
    public Team getAwayTeam() {
        return awayTeam;
    }

    /**
     * Getter that retrieves the current period in a game
     *
     * @return the current period
     */
    public int getCurrPeriod() {
        return currPeriod;
    }


}
