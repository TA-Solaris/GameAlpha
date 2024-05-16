package GraphicalObjects;

// Importing packets
import java.awt.Color;

// Defining the button
public class Button extends GraphicalObject {

  // Defining variables
  public Color textColour = Color.LIGHT_GRAY;
  public Color textDisabledColour = Color.RED;
  public Color backColour = new Color(0, 153, 204);
  public Color borderColour = Color.GRAY;
  public String text;
  public int textSize;
  private boolean enabled = true;

  // Constructor method
  public Button(String text, int size, int x, int y, int width, int height) {

    super(x, y, width, height);

    this.text = text;
    textSize = size;

  }

  // Setter methods
  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

  // Getter methods
  public boolean enabled() {
    return enabled;
  }

  // Method for working out if x and y will press the button
  public boolean pressed(int pressX, int pressY) {

    boolean result = false;

    if (enabled && !(hidden)) {
      if (x < pressX && x + width > pressX && y < pressY && y + height > pressY) {
        result = true;
      }
    }

    return result;

  }

}
