/**
 * The Main class is the driver class for the game engine. It is the entry point of the game engine.
 * It initializes instances of different entities (Actors and Obstacles), shows their creation, properties, interaction
 * capabilities, and prints their states to the console.
 * This class also contains many tests for many attributes and methods of the Entity, Actor, Obstacle, and Transform
 * classes. These tests output their results to verify the functionality of the game
 *
 * @version 1.0.0, 18, Sept 2024
 * @author Scott Pearson
 *
 */
public class Main {
    /**
     * This is the entry point of the game engine program. It creates and manipulates instances of
     * Actor and Obstacle objects, and displays their attributes and states to the console.
     * It includes testing of key methods and features as well.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // create Actor instance (warrior)
        Transform warriorModel = new Transform(10, 20, 90.0f, 5);
        Actor warrior = new Actor("Warrior", warriorModel, 1, "Player", 100, 20);

        // create another Actor instance (soldier)
        Transform soldierModel = new Transform(5, 20, 270.0f, 2);
        Actor soldier = new Actor("Soldier", soldierModel, 2, "Player", 75, 15);

        // create Obstacle instance that can be destroyed (tower)
        Transform towerModel = new Transform(50, 75, 0.0f, 0);
        Obstacle tower = new Obstacle("Tower", towerModel, 3, "Building", 150, true, "Stone");

        // create Obstacle instance that can't be destroyed
        Transform treeModel = new Transform(10, 25, 45.0f, 0);
        Obstacle tree = new Obstacle("Tree", treeModel, 4, "Nature", 50, false, "Wood");


        // Print out data of each item
        System.out.println("---------------------------------");
        System.out.println("Entities (Initial States): \n");

        System.out.println(warrior);
        System.out.println("\n" + tower);
        System.out.println("\n" + tree);
        System.out.println("\n" + soldier);

        // test movement/interactions
        System.out.println("\nTesting Movement and Rotations:");
        warrior.move(15,25);
        System.out.println("\n" + warrior.getName() + " has moved to a new position: " + warrior.getTransform());
        soldier.move(20, 25);
        System.out.println(soldier.getName() + " has moved to a new position: " + soldier.getTransform());

        // test rotate
        warrior.rotate(92.0f);
        System.out.println(warrior.getName() + " has rotated to face " + warrior.getTransform().getRotation() + " degrees.");
        soldier.rotate(90.0f);
        System.out.println(soldier.getName() + " has rotated to face " + soldier.getTransform().getRotation() + " degrees.");

        // test attack
        System.out.println("\nTesting Attack Method:");
        warrior.attack(soldier);

        // test destruction of obstacles
        System.out.println("\nTesting Destroy Method:");
        tower.destroy();
        tree.destroy(); // can't be destroyed (it is nature, so you can't destroy it)

        // testing getters and setters
        System.out.println("\nTesting Getters and Setters:");

        System.out.println("Entity Getters and Setters:");
        warrior.setName("Knight");
        System.out.println("Updated Name: Warrior changed name to: " + warrior.getName());

        // Test id and type getters
        System.out.println(warrior.getName() + "'s id is: " + warrior.getId());
        System.out.println(warrior.getName() + "'s type is: " + warrior.getType());

        // Test Transform getters and setters
        System.out.println("\nTesting Transform Getters and Setters:");
        warrior.getTransform().setXPos(50);
        warrior.getTransform().setYPos(55);
        warrior.getTransform().setRotation(180.0f);
        warrior.getTransform().setSpeed(13);
        System.out.println("Updated Transform: (Warrior (now Knight)): " + warrior.getTransform());

        // Testing Actor getters and setters
        System.out.println("\nTesting Actor Setters and Getters:");
        soldier.setHealth(125);
        soldier.setAttackStrength(45);
        System.out.println("Updated Health (Soldier): " + soldier.getHealth());
        System.out.println("Updated Attack Strength (Soldier): " + soldier.getAttackStrength());

        // Test Obstacle getters and setters
        System.out.println("\nTesting Obstacle Getters and Setters:");
        System.out.println(tree.getName() + " is made out of: " + tree.getMatType());
        tree.setSize(200);
        tree.setDestructible(true);
        System.out.println("Updated Size (Tree): " + tree.getSize());
        System.out.println("Updated Durability (Tree can be destroyed?): " + tree.getDestructible());
        // I want to make the tree indestructible. I made it destroyable above for testing purposes
        tree.setDestructible(false);
        System.out.println("Updated Durability (Tree can be destroyed?): " + tree.getDestructible());

        // Test if setTransform works properly
        System.out.println("\nTesting for setTransform: ");
        System.out.println("Original Soldier Transform: ");
        System.out.println(soldier.getTransform());
        // create new Transform and set it on soldier
        Transform newSoldierTransform = new Transform(60, 70, 50.0f, 8);
        soldier.setTransform(newSoldierTransform);
        System.out.println("Updated Soldier Transform using setTransform: ");
        System.out.println( soldier.getName() + ": " + soldier.getTransform());

        System.out.println("\n----------------------------------------");
        System.out.println("Final States of Entities:\n");
        System.out.println(warrior + "\n");
        System.out.println(tower + "\n");
        System.out.println(tree + "\n");
        System.out.println(soldier + "\n");
    }
}