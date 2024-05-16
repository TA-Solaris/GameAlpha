
// Declaring the package
package Entities;

// Importing packets
import java.awt.Color;

// Importing packets
public class ColonyShip extends Ship {

  public ColonyShip(Player owner, Color colour, int x, int y) {

    super(owner, 20, colour, x, y);
    type = "Colony Ship";
    
  }
  
}
