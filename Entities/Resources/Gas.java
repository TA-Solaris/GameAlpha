
// Declaring the package
package Entities.Resources;

// Importing packets
import java.awt.Color;

// Declaring the Gas class
public class Gas extends Resource {

  public Gas() {
    super("/Entities/Resources/Sprites/gas.png");
    type = "Gas";
    myColour = Color.GREEN;
  }

}
