
// Declaring the package
package Entities.Resources;

// Importing packets
import java.awt.Color;

// Declaring the Mineral class
public class Mineral extends Resource {

  public Mineral() {
    super("/Entities/Resources/Sprites/mineral.png");
    type = "Mineral";
    myColour = Color.RED;
  }

}
