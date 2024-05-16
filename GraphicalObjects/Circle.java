
// Declaring the package
package GraphicalObjects;

// Importing packets
import java.awt.Color;

// Defining the circle
public class Circle extends GraphicalObject {

  // Defining variables
  public Color myColour;
  public int mySize;

  public Circle(Color myColour, int x, int y, int mySize) {

    super(x, y, mySize, mySize);

    this.mySize = mySize;
    this.myColour = myColour;

  }

}
