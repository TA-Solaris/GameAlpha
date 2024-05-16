
// Declaring the package
package Tools;

// Importing packets
import java.awt.Font;

public class MyFont extends Font {

  public MyFont(int size) {
    super("Monospaced", Font.PLAIN, size);
  }

  // Size setter method
  public void setSize(int newSize) {
    size = newSize;
  }
  
}
