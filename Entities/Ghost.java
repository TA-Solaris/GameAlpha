
// Adding the class to the Entities Package
package Entities;

// Importing packets
//import java.awt.Color;

// Defining the star
public class Ghost extends Entity {

  // Defining variables for when a ghost orbits (only when not primary planet)
  public double myAngle = 0;
  public double myAngularVelocity = 0;

  // Constructor method
  public Ghost () {
    // Calling inherited constructor
    super(1, null);
    type = "Ghost";
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
