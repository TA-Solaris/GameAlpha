
// Declaring the package
package Entities;

// Importing packets
import java.awt.Color;

// Importing packets
public class Ship extends Entity {

  // Declaring variables
  private int vel = 2;
  private float x;
  private float y;
  public int destinationX;
  public int destinationY;

  public Ship(Player owner, int size, Color colour, int x, int y) {

    super(size, colour);
    
    this.owner = owner;
    this.x = x;
    this.y = y;
    destinationX = x;
    destinationY = y;
  }

  public int getX() {
    return (int) x;
  }

  public int getY() {
    return (int) y;
  }

  public double getAngle() {
    return (double) Math.atan2(destinationX - x, destinationY - y);
  }

  public boolean atDestination() {
    boolean result = true;
    
    if (Math.abs(x - destinationX) + Math.abs(y - destinationY) > vel) {
      result = false;
    }

    return result;
  }

  public void tick() {

    if (!atDestination()) {
      x += vel * Math.sin(getAngle());
      y += vel * Math.cos(getAngle());
    }
    
  }
  
}
