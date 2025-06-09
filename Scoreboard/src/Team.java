/**
 * The Team class is a class that represents a sports team with a name and score
 *
 * @version 1.0.0, 30, Oct 2024
 * @author Scott Pearson
 */
public class Team {
    /** The name of the team */
    private final String name; // make final since it shouldn't change upon creation
    /** The current score of the team */
    private int score;

    /**
     * Constructor for Team class. It constructs a team with a specified name
     *
     * @param name the name of the team
     */
    public Team(String name) {
        this.name = name;
        this.score = 0; // initialize the score to 0
    }

    /**
     * Getter that retrieves the name of the team
     *
     * @return the team's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that retrieves the score of the team
     *
     * @return the score of the team
     */
    public int getScore() {
        return score;
    }

    /**
     * Method that adds points to the team's current score
     *
     * @param points the points to be added to the team's score
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Method that resets the team's score
     *
     */
    public void resetScore() {
        score = 0;
    }
}
