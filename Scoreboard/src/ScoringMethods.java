/**
 * The ScoringMethods class is a class that represents methods for scoring in a game
 *
 * @version 1.0.0., 30, Oct 2024
 * @author Scott Pearson
 */
public class ScoringMethods {
    /** The name of the scoring method for games */
    private final String name; // make final since it shouldn't change upon creation
    /** The points awarded for scoring methods */
    private final int points; // make final since it shouldn't change upon creation

    /**
     * Constructor for the ScoringMethods class. It constructs the scoring methods with a
     * specified name and point value
     *
     * @param name the name of the scoring method
     * @param points the points awarded by the scoring method
     */
    public ScoringMethods(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Getter that retrieves the name of the scoring method
     *
     * @return the scoring name of the scoring method
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that retrieves the points awarded by the scoring method
     *
     * @return the point value
     */
    public int getPoints() {
        return points;
    }
}
