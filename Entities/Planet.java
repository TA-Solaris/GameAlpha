
// Adding the class to the Entities Package
package Entities;

// Importing packets
import java.awt.Color;

// Defining planets (and moons)
public class Planet extends Entity {

  // Defining variables
  public double myAngle;
  public double myAngularVelocity;

  // Defining constructor method
  public Planet (int size, Color colour, int distance, double angle, double angularVelocity, Entity parent) {

    // Calling inherited constructor
    super(size, colour);

    // Initialising variables
    myAngle = angle;
    myAngularVelocity = angularVelocity;
    myDistance = distance;
    myParent = parent;
    type = "Planet";

  }

  // Getter methods
  public int getX () {
    return (int) (myDistance * Math.sin(myAngle)) + myParent.getX();
  }

  public int getY () {
    return (int) (myDistance * Math.cos(myAngle)) + myParent.getY();
  }

  // Declairing methods
  public void tick() {
    myAngle -= myAngularVelocity;
    if (myAngle < 0) {
      myAngle += Math.PI * 2;
    }
  }

}
