
// Importing packets
import java.awt.Color;

import java.util.LinkedList;

import GraphicalObjects.*;
import Entities.*;

// Defining the HUD
public class HUD {

  // Defining constants
  private final int RESOURCE_TOP_BAR_SPACING = 150;
  private int windowWidth;
  private int windowHeight;

  // Creating references
  World myWorld;

  // Creating variables
  private boolean menuOpen = false;
  private boolean planetInterfaceOpen = false;

  // Creating a lists of graphical objects
  public LinkedList<GraphicalObject> myObjects = new LinkedList<GraphicalObject>();
  private LinkedList<GraphicalObject> myMenu = new LinkedList<GraphicalObject>();
  private LinkedList<GraphicalObject> myPlanetInterface = new LinkedList<GraphicalObject>();
  private LinkedList<GraphicalObject> varyingPlanetInterface = new LinkedList<GraphicalObject>();

  // Defining the constuctor method
  public HUD(World world, int windowWidth, int windowHeight) {

    // Initialising references
    myWorld = world;
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;

    // Initialising the top bar
    initialiseTopBar();

    // Initialising the planet interface
    initialisePlanetInterface();

    // Initialising the menu
    InitialiseMenu();

  }

  public void update() {

    if (myWorld.mySelectedEntities.size() > 0) {
      if (myWorld.mySelectedEntities.get(0) instanceof Planet) {
        if (!(planetInterfaceOpen)) {
          togglePlanetInterface((Planet) myWorld.mySelectedEntities.get(0));
        }
      } else {
        if (planetInterfaceOpen) {
          togglePlanetInterface();
        }
      }
    } else {
      if (planetInterfaceOpen) {
        togglePlanetInterface();
      }
    }

  }

  public void action(String buttonText) {

    if (buttonText == "Menu" && !(menuOpen)) {
      toggleMenu();
    }
    if (buttonText == "Back To Game") {
      toggleMenu();
    }
    if (buttonText == "Back To Main Menu") {
      myWorld.myGame.setStateMenu();
    }
    if (buttonText == "Back") {
      myWorld.mySelectedEntities = new LinkedList<Entity>();
    }
  }

  public void toggleMenu() {

    menuOpen = !(menuOpen);

    for (int i = 0; i < myMenu.size(); i++) {
      myMenu.get(i).hidden = !(menuOpen);
    }

  }

  // Getter methods
  public boolean isMenuOpen() {
    return menuOpen;
  }

  public boolean isPlanetInterfaceOpen() {
    return planetInterfaceOpen;
  }

  private void InitialiseMenu() {

    // Menu
    Box myGreyOut = new Box(0, 0, windowWidth, windowHeight);
    myGreyOut.backColour = new Color(0, 0, 0, 150);
    myGreyOut.borderColour = new Color(0, 0, 0, 0);
    myGreyOut.hidden = !(menuOpen);
    myObjects.add(myGreyOut);
    myMenu.add(myGreyOut);

    Box myMenuBox = new Box(windowWidth/2 - 125, windowHeight/2 - 150, 250, 300);
    myMenuBox.hidden = !(menuOpen);
    myObjects.add(myMenuBox);
    myMenu.add(myMenuBox);

    Button myBackToGameButton = new Button("Back To Game", 15, windowWidth/2 - 100, windowHeight/2 + 50, 200, 40);
    myBackToGameButton.hidden = !(menuOpen);
    myObjects.add(myBackToGameButton);
    myMenu.add(myBackToGameButton);

    Button myBackToMenuButton = new Button("Back To Main Menu", 15, windowWidth/2 - 100, windowHeight/2 + 100, 200, 40);
    myBackToMenuButton.hidden = !(menuOpen);
    myObjects.add(myBackToMenuButton);
    myMenu.add(myBackToMenuButton);

    Text myPausedText =  new Text("Paused", 30, windowWidth/2, windowHeight/2 - 100);
    myPausedText.hidden = !(menuOpen);
    myObjects.add(myPausedText);
    myMenu.add(myPausedText);

  }

  private void initialiseTopBar() {

    // Top bar
    myObjects.add(new Box(0, 0, windowWidth, 30));
    myObjects.add(new Button("Menu", 15, windowWidth - 100, 5, 80, 20));

    // Amounts of resources
    if (!(myWorld.myPlayer == null)) {
      for (int i = 0; i < myWorld.myPlayer.myResources.size(); i++) {

        // For the resource image
        myObjects.add(new Images(myWorld.myPlayer.myResources.get(i).myImage, 5 + RESOURCE_TOP_BAR_SPACING * i, 5, 20, 20));

        // For the resource amount
        int delta = 0;
        for (int j = 0; j < myWorld.myPlayer.deltaResources.size(); j++) {
          if (myWorld.myPlayer.deltaResources.get(j).type == myWorld.myPlayer.myResources.get(i).type) {
            delta = myWorld.myPlayer.deltaResources.get(j).amount;
          }
        }
        String deltaString = "";
        if (delta >= 0) {
          deltaString = "+" + delta;
        } else {
          deltaString = "-" + delta;
        }
        Text myResourceText = new Text("(" + deltaString + ") " + myWorld.myPlayer.myResources.get(i).amount, 15, RESOURCE_TOP_BAR_SPACING/2 + RESOURCE_TOP_BAR_SPACING * i, 30);
        myResourceText.textColour = myWorld.myPlayer.myResources.get(i).myColour;
        myObjects.add(myResourceText);

        // For the line splitter
        myObjects.add(new Line(myWorld.myPlayer.myResources.get(i).myColour, RESOURCE_TOP_BAR_SPACING * (i + 1), 5, RESOURCE_TOP_BAR_SPACING * (i + 1), 25));
        
      }
    }

  }

  private void initialisePlanetInterface() {

    // Planet interface
    Box myGreyOut = new Box(0, 0, windowWidth, windowHeight);
    myGreyOut.backColour = new Color(0, 0, 0, 150);
    myGreyOut.borderColour = new Color(0, 0, 0, 0);
    myGreyOut.hidden = !(planetInterfaceOpen);
    myObjects.add(myGreyOut);
    myPlanetInterface.add(myGreyOut);

    Box myPlanetBox = new Box(windowWidth/2 - 400, windowHeight/2 - 300, 800, 600);
    myPlanetBox.hidden = !(planetInterfaceOpen);
    myObjects.add(myPlanetBox);
    myPlanetInterface.add(myPlanetBox);

    Box myPlanetMiniBox = new Box(windowWidth/2 - 370, windowHeight/2 - 270, 300, 360);
    myPlanetMiniBox.hidden = !(planetInterfaceOpen);
    myPlanetMiniBox.backColour = Color.BLACK;
    myObjects.add(myPlanetMiniBox);
    myPlanetInterface.add(myPlanetMiniBox);

    Button myBackButton = new Button("Back", 15, windowWidth/2 + 300, windowHeight/2 - 270, 70, 30);
    myBackButton.hidden = !(planetInterfaceOpen);
    myObjects.add(myBackButton);
    myPlanetInterface.add(myBackButton);

  }

  private void togglePlanetInterface() {

    planetInterfaceOpen = !(planetInterfaceOpen);

    for (int i = 0; i < myPlanetInterface.size(); i++) {
      myPlanetInterface.get(i).hidden = !(planetInterfaceOpen);
    }

    if (varyingPlanetInterface.size() > 0) {
      for (int i = 0; i < varyingPlanetInterface.size(); i++) {
        varyingPlanetInterface.get(i).hidden = !(planetInterfaceOpen);
      }
    }

  }

  private void togglePlanetInterface(Planet myPlanet) {

    togglePlanetInterface();

    // Clearing entities which vary from the objects
    if (varyingPlanetInterface.size() > 0) {
      for (int i = 0; i < varyingPlanetInterface.size(); i++) {
        myObjects.remove(varyingPlanetInterface.get(i));
      }
    }

    varyingPlanetInterface = new LinkedList<GraphicalObject>();

    Circle myPlanetDrawn = new Circle(myPlanet.myColour, windowWidth/2 - 220, windowHeight/2 - 90, myPlanet.mySize);
    myPlanetDrawn.hidden = !(planetInterfaceOpen);
    myObjects.add(myPlanetDrawn);
    varyingPlanetInterface.add(myPlanetDrawn);

    Text myPlanetName = new Text(myPlanet.name, 15, windowWidth/2 - 220, windowHeight/2 - 120);
    myPlanetName.hidden = !(planetInterfaceOpen);
    myObjects.add(myPlanetName);
    varyingPlanetInterface.add(myPlanetName);

  }
  
}
