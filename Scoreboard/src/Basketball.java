import java.util.ArrayList;
import java.util.List;

/**
 * The Basketball class is a class that represents a basketball game
 * This class extends the Game class to inherit common properties and attributes
 *
 * @version 1.0.0, 30, Oct 2024
 * @author Scott Pearson
 */
public class Basketball extends Game {
    /** List of scoring methods specific to basketball */
    private final List<ScoringMethods> scoringMethods; // make final since it shouldn't change upon creation

    /**
     * Constructor for the Basketball class. It constructs a basketball game with specified scoring methods
     *
     * @param homeTeam the home team
     * @param awayTeam the away team
     */
    public Basketball(Team homeTeam, Team awayTeam) {
        super(homeTeam, awayTeam); // calls superclass constructor
        scoringMethods = new ArrayList<>();

        // define basketball scoring methods and add the scoringMethods
        scoringMethods.add(new ScoringMethods("Free Throw", 1));
        scoringMethods.add(new ScoringMethods("Field Goal", 2));
        scoringMethods.add(new ScoringMethods("Three Pointer", 3));
    }

    /**
     * Overridden method that retrieves the list of scoring methods for a basketball game
     *
     * @return a list of basketball scoring methods
     */
    @Override
    public List<ScoringMethods> getScoringMethods() {
        return scoringMethods;
    }

    /**
     * Overridden method that retrieves the length of periods in a basketball game
     *
     * @return the length of periods in a basketball game in minutes as an int
     */
    @Override
    public int getPeriodLength() {
        return 20; // each half is 20 minutes long
    }

    /**
     * Overridden method that retrieves the name of periods of a basketball game
     *
     * @return the name of periods of a basketball game as a String
     */
    @Override
    public String getPeriodName() {
        return "Half"; // 2 periods -> "half"
    }

    /**
     * Overridden method that retrieves the maximum number of periods in a basketball game
     *
     * @return the maximum number of periods in a basketball game as an int
     */
    @Override
    public int getMaxPeriods() {
        return 2; // there are 2 halves in a game
    }
}