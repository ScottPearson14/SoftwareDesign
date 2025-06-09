import java.util.ArrayList;
import java.util.List;

/**
 * The Football class is a class that represents a football game
 * This class extends the Game class to inherit common properties and attributes
 *
 * @version 1.0.0, 30, Oct 2024
 * @author Scott Pearson
 */
public class Football extends Game {
    /** List of scoring methods specific to football */
    private final List<ScoringMethods> scoringMethods; // make final since it shouldn't change upon creation
    /**
     * Constructor for Football class. It constructs a football game with specified teams
     *
     * @param homeTeam the home team
     * @param awayTeam the away team
     */
    public Football(Team homeTeam, Team awayTeam) {
        super(homeTeam, awayTeam); // calls superclass constructor
        scoringMethods = new ArrayList<>();

        // define football scoring methods and add the scoringMethods
        scoringMethods.add(new ScoringMethods("Touchdown", 6));
        scoringMethods.add(new ScoringMethods("Field Goal", 3));
        scoringMethods.add(new ScoringMethods("Extra Point", 1));
        scoringMethods.add(new ScoringMethods("Two-Point Conversion", 2));
        scoringMethods.add(new ScoringMethods("Safety", 2));
    }

    /**
     * Overridden method that retrieves the list of scoring methods for football games
     *
     * @return a list of football scoring methods
     */
    @Override
    public List<ScoringMethods> getScoringMethods() {
        return scoringMethods;
    }

    /**
     * Overridden method that retrieves the length of a period in a football game in minutes
     *
     * @return the length of each period in minutes as an int
     */
    @Override
    public int getPeriodLength() {
        return 15; // each quarter is 15 minutes long
    }

    /**
     * Overridden method that retrieves the name of periods in a football game
     *
     * @return the name of periods in a football game as a String
     */
    @Override
    public String getPeriodName() {
        return "Quarter"; // the name of the periods are quarters in football
    }

    /**
     * Overridden method that retrieves the maximum number of periods in a football game
     *
     * @return the maximum number of periods in a football game as an int
     */
    @Override
    public int getMaxPeriods() {
        return 4; // football has 4 quarters
    }
}