
// Importing packets
import java.util.LinkedList;

import GraphicalObjects.*;

// Defining the main menu
public class MainMenu {

  // Creating references
  Game myGame;

  // Creating a list of graphical objects
  LinkedList<GraphicalObject> myObjects = new LinkedList<GraphicalObject>();

  // Defining the constuctor method
  public MainMenu(Game game, int windowWidth, int windowHeight) {

    myGame = game;

    // Title card
    myObjects.add(new Text("Game Alpha", 50, windowWidth/2, windowHeight/4));

    // Base bar
    myObjects.add(new Box(0, windowHeight - 120, windowWidth, 120));

    // Buttons
    myObjects.add(new Button("New Game", 20, (windowWidth)/8 - 100, windowHeight - 100, 200, 40));

    Button loadButton = new Button("Load Game", 20, 3 * (windowWidth)/8 - 100, windowHeight - 100, 200, 40);
    loadButton.disable();
    myObjects.add(loadButton);

    Button settingsButton = new Button("Settings", 20, 5 * (windowWidth)/8 - 100, windowHeight - 100, 200, 40);
    settingsButton.disable();
    myObjects.add(settingsButton);

    myObjects.add(new Button("Quit to Desktop", 20, 7 * (windowWidth)/8 - 100, windowHeight - 100, 200, 40));

  }

  public void action(String buttonText) {

    if (buttonText == "New Game") {
      myGame.setStateInGame();
    }
    if (buttonText == "Quit to Desktop") {
      System.exit(1);
    }

  }
  
}
