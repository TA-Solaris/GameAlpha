
// Declaring the package
package Entities;

// Importing packets
import java.awt.Color;

import Entities.Resources.*;

import java.util.LinkedList;

// Declaring the Player class
public class Player {

  // Declaring variables
  public Color myColour = Color.CYAN;

  // Declaring list of resources
  public LinkedList<Resource> myResources = new LinkedList<Resource>();
  public LinkedList<Resource> deltaResources = new LinkedList<Resource>();

  public Player() {
    
    myResources.add(new Mineral());
    myResources.add(new Gas());
    myResources.add(new Plastic());

    deltaResources.add(new Mineral());
    deltaResources.add(new Gas());
    deltaResources.add(new Plastic());
  }

}
