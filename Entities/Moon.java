
// Adding the class to the Entities Package
package Entities;

// Importing packets
import java.awt.Color;

// Defining planets (and moons)
public class Moon extends Planet {

  // Defining constructor method
  public Moon(int size, Color colour, int distance, double angle, double angularVelocity, Entity parent) {

    // Calling inherited constructor
    super(size, colour, distance, angle, angularVelocity, parent);

    // Initialising variables
    type = "Moon";

  }

}
