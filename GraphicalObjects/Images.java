
// Declaring the package
package GraphicalObjects;

// Importing packets
import java.awt.Image;

// Defining the Images
public class Images extends GraphicalObject {

  // Defining variables
  public Image myImage;

  public Images(Image myImage, int x, int y, int width, int height) {

    super(x, y, width, height);

    this.myImage = myImage;

  }

}
