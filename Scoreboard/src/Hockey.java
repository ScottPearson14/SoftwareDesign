import java.util.ArrayList;
import java.util.List;
/**
 * The Hockey class is a class that represents a hockey game
 * This class extends the Game class to inherit common properties and attributes
 *
 * @version 1.0.0, 30, Oct 2024
 * @author Scott Pearson
 */
public class Hockey extends Game {
    /** List of scoring methods specific to hockey */
    private final List<ScoringMethods> scoringMethods; // make final since it shouldn't change upon creation
    /**
     * Constructor for the Hockey class. It constructs a hockey game with specified scoring methods
     *
     * @param homeTeam the home team
     * @param awayTeam the away team
     */
    public Hockey(Team homeTeam, Team awayTeam) {
        super(homeTeam, awayTeam);
        scoringMethods = new ArrayList<>();

        // define football scoring methods and add the scoringMethods
        scoringMethods.add(new ScoringMethods("Goal", 1));
    }
    /**
     * Overridden method that retrieves the list of scoring methods for a hockey game
     *
     * @return a list of soccer scoring methods
     */
    @Override
    public List<ScoringMethods> getScoringMethods() {
        return scoringMethods;
    }
    /**
     * Overridden method that retrieves the length of periods in a hockey game
     *
     * @return the length of periods in a soccer game in minutes as an int
     */
    @Override
    public int getPeriodLength() {
        return 20; // each period is 20 minutes
    }
    /**
     * Overridden method that retrieves the name of periods of a soccer game
     *
     * @return the name of periods of a soccer game as a String
     */
    @Override
    public String getPeriodName() {
        return "Period"; // just called periods in hockey
    }
    /**
     * Overridden method that retrieves the maximum number of periods in a soccer game
     *
     * @return the maximum number of periods in a soccer game as an int
     */
    @Override
    public int getMaxPeriods() {
        return 3; // hokey has 3 periods
    }
}