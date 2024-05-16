
// Declaring the package
package GraphicalObjects;

// Importing packets
import java.awt.Color;

// Defining the box
public class Box extends GraphicalObject {

  // Defining variables
  public Color backColour = new Color(51, 102, 153);
  public Color borderColour = Color.GRAY;

  public Box(int x, int y, int width, int height) {

    super(x, y, width, height);

  }

}
