
// Declaring the package
package GraphicalObjects;

// Importing packets
import java.awt.Color;

// Defining the text
public class Text extends GraphicalObject {

  // Defining variables
  public Color textColour = Color.LIGHT_GRAY;
  public String text;
  public int size;

  public Text(String text, int size, int x, int y) {

    super(x, y, 0, 0);

    this.text = text;
    this.size = size;

  }

}
