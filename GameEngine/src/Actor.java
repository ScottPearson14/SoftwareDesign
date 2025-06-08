/**
 * The Actor class represents entities that have interactive capabilities and can perform actions
 * (like players in the game).
 * This class extends the Entity class to inherit common properties of entities, and it adds additional properties.
 *
 * @see Entity
 * @version 1.0.0, 15, Sept 2024
 * @author Scott Pearson
 */
public class Actor extends Entity {
    /** health of the actor */
    private int health;
    // adding new attribute for fun/creativity
    /** attack strength of the actor */
    private int attackStrength; // holds the strength of an Actor's attack

    /**
     * Constructor to initialize an Actor object
     *
     * @param name the name of the actor
     * @param transform the transform of the actor
     * @param id the unique ID of the actor
     * @param type the unique type of the actor
     * @param health the health of the actor
     * @param attackStrength the attack strength of the actor
     */
    public Actor(String name, Transform transform, int id, String type, int health, int attackStrength) {
        super(name, transform, id, type);
        this.health = health;
        this.attackStrength = attackStrength;

    }

    /**
     * Gets the health of the actor
     *
     * @return the health of the actor
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the actor
     *
     * @param health the updated health of the actor
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the attack strength of the actor
     *
     * @return the attack strength of the actor
     */
    public int getAttackStrength() {
        return attackStrength;
    }

    /**
     * Sets the attack strength of the actor
     *
     * @param attackStrength the updates attack strength of the actor
     */
    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }

    /**
     * Allows an actor to attack another actor within the game
     *
     * @param target the actor that is about to be attacked
     */
    // adding new attack method for Actors
    public void attack(Actor target) {
        System.out.println(getName() + " is attacking " + target.getName());
        target.setHealth(target.getHealth() - this.attackStrength);
        System.out.println(target.getName() + " now has " + target.getHealth() + " health!");
    }

    /**
     * Returns a string representation of the actor
     *
     * @return a string that describes the actor
     */
    @Override
    public String toString() {
        return super.toString() + "\n" + getTransform().toString() + "\nMy health is " + health + "\nAttack Strength: " + attackStrength;
    }
}