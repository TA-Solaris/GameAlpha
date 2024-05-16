
// Adding the class to the Entities Package
package Entities;

// Importing packets
import java.awt.Color;

// Defining the star
public class Star extends Entity {

  // Defining variables for when a star orbits (only when not primary star system)
  public double myAngle = 0;
  public double myAngularVelocity = 0;

  // Constructor method
  public Star(int size, Color colour) {
    // Calling inherited constructor
    super(size, colour);
    type = "Star";
  }

  // Getter methods
  public int getX() {
    if (myParent == null) {
      return 0;
    } else {
      return (int) (myDistance * Math.sin(myAngle)) + myParent.getX();
    }
  }

  public int getY() {
    if (myParent == null) {
      return 0;
    } else {
      return (int) (myDistance * Math.cos(myAngle)) + myParent.getY();
    }
  }

  // Declairing methods
  public void tick() {
    
    if (myParent == null) {} else {
      myAngle -= myAngularVelocity;
      if (myAngle < 0) {
        myAngle += Math.PI * 2;
      }
    }

  }
  
}
