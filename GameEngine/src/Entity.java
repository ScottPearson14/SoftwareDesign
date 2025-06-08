/**
 * The Entity class is the base class that represents a standard game entity within a game engine with a name,
 * transform, id, type, active boolean flag, and a transform. Entities are the foundations of the game engine,
 * and they contain additional attributes and methods for customizations
 *
 * @version 1.0.0, 15, Sept 2024
 * @author Scott Pearson
 *
 */
public class Entity {
    /** name of the entity */
    private String name;
    /** transform representing entities position, rotation, and speed */
    private Transform transform;
    // adding 3 new attributes to Entity class for fun/creativity
    /** unique ID for different entities in a game */
    private final int id;
    // - make final since it shouldn't change upon creation
    /** unique type of entity */
    private final String type; // says type of Entity
    // - make final since it shouldn't change upon creation
    /** boolean flag that says whether entity is active or not */
    private boolean isActive;

    /**
     * Constructor to initialize entity objects
     *
     * @param name the name of the entity
     * @param transform the transform representing the position, rotation, and speed of an entity
     * @param id the unique ID of the entity
     * @param type the unique type of the entity
     */
    public Entity(String name, Transform transform, int id, String type) {
        this.name = name;
        this.transform = transform;
        this.id = id;
        this.type = type;
        this.isActive = true; // set the default to active
    }

    /**
     * Gets the name of the entity
     *
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entity
     *
     * @param name the updated name of the entity
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the transform of the entity
     *
     * @return the transform of the entity
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * Sets the transform of the entity
     *
     * @param transform the updated transform of the entity
     */
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /**
     * Gets the ID of the entity
     *
     * @return the id of the entity
     */
    public int getId() { return id; }

    /**
     * Gets the type of the entity
     *
     * @return the type of the entity
     */
    public String getType() { return type; }

    /**
     * Checks if the entity is active
     *
     * @return true if entity is active, false if it is not active
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the state activeness of an entity
     *
     * @param isActive updated state of activeness of the entity
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    // adding 2 new methods for fun/creativity

    /**
     * Updates the x and y position of the entity to allow it to move
     *
     * @param x the x position of the entity
     * @param y the y position of the entity
     */
    public void move(int x, int y) {
        this.transform.setXPos(x);
        this.transform.setYPos(y);
    }

    /**
     * Allows an entity to rotate its direction in degrees
     *
     * @param angle the angle of rotation of the entity in degrees
     */
    public void rotate(float angle) {
        this.transform.setRotation(angle);
    }

    /**
     * Returns a string representation of the entity
     *
     * @return a string that describes the entity
     */
    public String toString() {
        return "I am an Entity. My name is " + name + " (Type: " + type + ", ID: " + id + ")";
    }

}