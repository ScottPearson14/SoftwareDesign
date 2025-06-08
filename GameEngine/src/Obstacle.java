/**
 * The Obstacle class represents a static object in the game world (like towers or trees).
 * This class extends the Entity class to inherit common properties of entities, and it adds additional properties.
 *
 * @see Entity
 * @version 1.0.0, 15, Sept 2024
 * @author Scott Pearson
 */
public class Obstacle extends Entity {
    /** size of the obstacle */
    private int size;
    // adding attributes for fun/creativity
    /** boolean flag that determines if obstacle is destructible */
    private boolean isDestructible;
    /** type of material an obstacle is made of (steel, wood, etc.)*/
    private String matType;

    /**
     * Constructor to initialize an Obstacle object
     *
     * @param name the name of the obstacle
     * @param transform the transform of the obstacle
     * @param id the unique ID of the obstacle
     * @param type the unique type of the obstacle
     * @param size the size of the obstacle
     * @param isDestructible the state of whether an obstacle can be destroyed
     * @param matType the type of material the obstacle is made of
     */
    public Obstacle(String name, Transform transform, int id, String type, int size, boolean isDestructible, String matType) {
        super(name, transform, id, type);
        // make sure size is not 0 or negative number
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be a positive value!");
        }
        this.size = size;
        this.isDestructible = isDestructible;
        this.matType = matType;
    }

    /**
     * Gets the size of the obstacle
     *
     * @return the size of the obstacle
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the obstacle
     *
     * @param size updated size of the obstacle
     */
    public void setSize(int size) {
        // make sure size is not 0 or negative number
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be a positive value!");
        }
        this.size = size;
    }

    /**
     * Checks if obstacle is destructible
     *
     * @return ture if obstacle is destructible, false means it is not
     */
    public boolean getDestructible() {
        return isDestructible;
    }

    /**
     * Set whether an obstacle is destructible or not
     *
     * @param isDestructible updated destructible status of the obstacle
     */
    public void setDestructible(boolean isDestructible) {
        this.isDestructible = isDestructible;
    }

    /**
     * Gets the material type of the obstacle
     *
     * @return the material type of the obstacle
     */
    public String getMatType() {
        return matType;
    }
    // no matType setter since it is immutable once Obstacle is created

    /**
     * Attempts to destroy an obstacle by checking the state of the destructibility of the obstacle
     * If destructible, marks the obstacle as inactive and prints a destruction message.
     * Otherwise, it prints a message stating the obstacle can't be destroyed.
     *
     */
    public void destroy() {
        if (isDestructible) {
            System.out.println(getName() + " has been destroyed!");
            setIsActive(false); // mark the obstacle as no longer being active
        } else {
            System.out.println(getName() + " can't be destroyed");
        }
    }

    /**
     * Returns a string representation of the obstacle
     *
     * @return a string that describes the obstacle
     */
    @Override
    public String toString() {
        if (!getIsActive()){
            return getName() + " has been destroyed and is no longer active!";
        }
        return super.toString() + "\n" + getTransform().toString() + "\nMy size is " + size + "\nDestructible: " + isDestructible + "\nMaterial: " + matType;

    }

}