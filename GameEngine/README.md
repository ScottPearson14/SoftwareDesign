# S53_GameEngine_Medium
[Home](Home)

## Problem Statement
For this problem, a class hierarchy must be designed to serve as the base for a new game engine. The first step is designing an Entity class containing a name and a Transform which is used for updating the position and rotation of the Entity class. Entity contains subclasses: Actor and Obstacle. Actor adds a health attribute, and Obstacle adds a size attribute. Instances of this classes are then implemented in a main driver program to test their behaviors and attributes.

## Developer Documentation
My code for this specific problem contains 4 classes along with a main driver class for testing the code. Here is an overview of the classes along with some of their main instance variables/methods:

1. Entity Class: 
This is the base class for all entities within my game engine. It represents objects that can exist in the game (like players, buildings, etc.). It contains 5 instance variables: 
- name: name of the entity (String)
- transform: a Transform object that holds the position, rotation, and speed,
- type: describes type of the entity (like a player or building) (String)
- id: unique identify for each entity (int)
- isActive: boolean value saying if entity is currently active in game (bool)

The key methods of the class consist of:
- move: allows entities to move around in the x and y-axis
- rotate: allows entities to rotate directions in the z axis

2. Transform Class:
This class contains transformation data (position, rotation, and speed) for entity objects. It contains 4 instance variables:
- xPos: holds horizontal (x-axis) position (int)
- yPos: holds vertical (y-axis) position (int)
- rotation: holds rotational direction of entities in degrees (float)
- speed: holds movement speed of entity (int)

The key methods of the class consist of getters and setters for each of the instance variables.

3. Actor Class (subclass of Entity):
This class represents entities that have interactive capabilities and can perform actions (like players in the game). This class extends Entity and adds additional properties. It contains 2 instance variables:
- health: represents health points of actors (int)
- attackStrength: represents attacking strength of actors (int)

The key methods of the class consist of:
- attack: allows actors to attack other actors, bringing down the health of the target in the process
- toString: returns string describing the updated state of the actor, including health and attack strength

4. Obstacle Class (subclass of Entity):
This class represents static objects (like towers) that Actors can interact with. This class extends Entity and adds additional properties. It contains 3 instance variables:
- size: holds size of obstacle (int)
- isDestructible: boolean value saying whether obstacle can be destroyed (bool)
- matType: describes material obstacle is made of (String)

The key methods of the class consist of:
- setSize: ensures the size is not 0 or a negative number
- destroy: method that checks if obstacle is able to be destroyed. If it can, obstacle is destroyed. If not, says that obstacle can't be destroyed
- toString: checks if object can be destroyed. If it can, returns string saying obstacle has been destroyed. If not, returns string describing state of obstacle

Helpful usage notes for developers: 
* Inheritance: the Entity class is the base for the Actor and Obstacle classes. A developer can choose to extend Entity further to create more specific entities for the game engine with more attributes.

* Encapsulation: the Transform class uses encapsulation to contain properties of all entities (position, speed, rotation). This helps simplify managing entity movement (static and active) within the game engine

* Entity Life: The isActive boolean flag is helpful for managing changing states of entities within the game engine when they are destroy/removed from the environment.

## JavaDocs
[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam1/S53_GameEngine_Medium/doc?ref_type=heads)

## UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam1/S53_GameEngine_Medium/doc/GameEngine_UML.png?ref_type=heads)

## User Documentation
Here are the steps to use/run the program:
1. Have Java installed on the machine you are using
2. Go to the Main.java file (the driver program)
3. Observe the unit tests that have already been in place to ensure all classes and implementations are properly working (feel free to add your own for more tests)
4. Compile and run the program. The data will be printed to the screen. You can now observe the printed data to see if all the tests worked properly.

## Source Code
[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam1/S53_GameEngine_Medium/src?ref_type=heads)
