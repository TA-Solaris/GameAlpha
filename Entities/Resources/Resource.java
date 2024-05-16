
// Declaring the package
package Entities.Resources;

// Importing packets
import java.awt.Color;
import java.awt.Image;

import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;

import javax.imageio.ImageIO;

// Declaring the Resource class
public abstract class Resource {

  // Declaring variables
  public Image myImage;
  public String type = "";
  public int amount = 0;
  public Color myColour;

  public Resource(String path) {
    try {
      File pathToFile = new File(Paths.get("").toAbsolutePath() + path);
      myImage = ImageIO.read(pathToFile);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

}
