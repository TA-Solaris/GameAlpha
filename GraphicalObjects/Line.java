
// Declaring the package
package GraphicalObjects;

// Importing packets
import java.awt.Color;

// Defining the line
public class Line extends GraphicalObject {

  // Defining variables
  public Color myColour;
  public int endx;
  public int endy;

  public Line(Color myColour, int x, int y, int endx, int endy) {

    super(x, y, 0, 0);

    this.endx = endx;
    this.endy = endy;
    this.myColour = myColour;

  }

}
