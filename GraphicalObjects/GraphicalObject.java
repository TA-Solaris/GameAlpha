
// Declaring the package
package GraphicalObjects;

// Defining the graphical objects abstract class
public abstract class GraphicalObject {

  // Defining variables
  public int x;
  public int y;
  public int width;
  public int height;
  public boolean hidden = false;

  // Defining the constructor
  public GraphicalObject(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
}
