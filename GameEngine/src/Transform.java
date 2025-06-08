/**
 * The Transform class represents the position, rotation, and speed of an Entity object
 *
 * @version 1.0.0, 15, Sept 2024
 * @author Scott Pearson
 */
public class Transform {
    /** x-coordinate of entity's position */
    private int xPos;
    /** y-coordinate of entity's position */
    private int yPos;
    /** rotation of entity in degrees */
    private float rotation;
    /** speed of entity  */
    private int speed;

    /**
     * Constructor to initialize Transform object
     *
     * @param xPos the x-coordinate of the entity
     * @param yPos the y-coordinate of the entity
     * @param rotation the rotation of the entity in degrees
     * @param speed the speed of the entity
     */
    public Transform(int xPos, int yPos, float rotation, int speed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.speed = speed;
    }

    /**
     * Gets the x-coordinate of the entity
     *
     * @return the x-coordinate of the entity
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Sets the x-coordinate of the entity
     *
     * @param xPos the updated x-coordinate of the entity
     */
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    /**
     * Gets the y-coordinate of the entity
     *
     * @return the y-coordinate of the entity
     */
    public int getYPos() {
        return yPos;
    }
    /**
     * Sets the y-coordinate of the entity
     *
     * @param yPos the updated y-coordinate of the entity
     */
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Gets rotation of the entity in degrees
     *
     * @return the rotation of the entity in degrees
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the entity in degrees
     *
     * @param rotation the updated rotation of the entity in degrees
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the speed of the entity
     *
     * @return the speed of the entity
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the entity
     *
     * @param speed the updated speed of the entity
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    /**
     * Returns a string representation of the transform
     *
     * @return a string that describes the entity's position, rotation, and speed
     */
    public String toString() {
        return "I am at " + xPos + ", " + yPos + ", facing " + rotation + " degrees, with a speed of " + speed;
    }

}