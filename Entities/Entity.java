
// Adding the class to the Entities Package
package Entities;

// Importing packets
import java.awt.Color;

// Defining the abstract class
public abstract class Entity {
  
  // Declaring variables
  public Player owner;
  public int mySize;
  public Color myColour;
  public Entity myParent = null;
  public int myDistance = 0;
  public String type = "";
  public String name = "";

  // Declaring abstract methods
  public abstract int getX();
  public abstract int getY();
  public abstract void tick();

  // Creating constructor method
  public Entity (int size, Color colour) {
    mySize = size;
    myColour = colour;
  }

}
