
// Importing packets
import java.util.LinkedList;

import Entities.Planet;
import Entities.Ship;
import Entities.Entity;
import GraphicalObjects.Button;

// Making the game class out of (inheriting from) the canvas
public class Game implements Runnable {

  // Defining variables
  private Thread gameThread;
  private boolean running = false;
  private boolean debug = false;

  public GameState myState;

  // Making the window and world
  public Renderer myRenderer = new Renderer("GameAlpha", this);
  public MainMenu myMainMenu;
  public World myWorld;

  public synchronized void start() {

    // Setting the games state to be in the menu
    setStateMenu();
    
    // Starting the game thread
    gameThread = new Thread(this);
    gameThread.setName("Game");
    gameThread.start();

    // Setting running to true
    running = true;

  }

  public synchronized void stop() {

    // Error handling (just printing it to terminal)
    try {

      gameThread.join();
      running = false;

    } catch (Exception e) {

      e.printStackTrace();

    }

  }

  public void run() {
    
    // Standard running code with ticks
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double amountOfFrames = 120.0;
    double nsTicks = 1000000000 / amountOfTicks;
    double nsFrames = 1000000000 / amountOfFrames;
    double deltaTicks = 0;
    double deltaFrames = 0;
    long timer = System.currentTimeMillis();
    int fps = 0;
    int tps = 0;
    int frames = 0;
    int ticks = 0;
    while (running) {
      long now = System.nanoTime();
      if (!(myWorld == null)) {
        if (!(myWorld.myHUD == null)) {
          if (!(myWorld.myHUD.isMenuOpen())) {
            deltaTicks += (now - lastTime) / nsTicks;
          }
        }
      }
      deltaFrames += (now - lastTime) / nsFrames;
      lastTime = now;

      while (deltaTicks >= 1) {

        // Ticking
        ticks++;
        tick();

        // Updating the window
        this.myRenderer.myWindow.update();

        deltaTicks--;

      }
      // Frame Capping
      while (deltaFrames >= 1) {
        if (running) {

          frames++;
          render();

          if (myState == GameState.InGame) {
            if (this.debug) {
              myRenderer.renderDebug(myWorld.myEntities, fps, tps);
            }
            if (!(myWorld.myHUD == null)) {
              myRenderer.renderObjects(myWorld.myHUD.myObjects);
              myWorld.myHUD.update();
            }
          }
          if (myState == GameState.Menu) {
            myRenderer.renderObjects(myMainMenu.myObjects);
          }

          myRenderer.displayGraphics();

          deltaFrames--;

        }
      }
      

      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;

        // Clearing the terminal
        //System.out.print("\033[H\033[2J");
        //System.out.flush();

        tps = ticks;
        ticks = 0;
        fps = frames;
        frames = 0;
      }
    }
    stop();

  }

  private void tick() {

    // Ticking the world
    if (!(this.myWorld == null)) {
      myWorld.tick();
    }

  }

  public void handleLeftMouse(int x, int y) {
    
    // For menu buttons
    if (myState == GameState.Menu) {
      for (int i = 0; i < myMainMenu.myObjects.size(); i++) {
        if (myMainMenu.myObjects.get(i) instanceof Button) {
          Button myButton = (Button) myMainMenu.myObjects.get(i);
          if (myButton.pressed(x, y)) {
            myMainMenu.action(myButton.text);
          }
        }
      }
    } else if (myState == GameState.InGame) {
      if (!(myWorld.myHUD == null)) {
        for (int i = 0; i < myWorld.myHUD.myObjects.size(); i++) {
          if (myWorld.myHUD.myObjects.get(i) instanceof Button) {
            Button myButton = (Button) myWorld.myHUD.myObjects.get(i);
            if (myButton.pressed(x, y)) {
              myWorld.myHUD.action(myButton.text);
            }
          }
        }
      }
    }

    // Changing X and Y values to be relative to the world
    x = (int) ((x - myRenderer.myWindow.width/2) / myRenderer.scale) - myRenderer.myWindow.xPos();
    y = (int) ((y - myRenderer.myWindow.height/2) / myRenderer.scale) - myRenderer.myWindow.yPos();

    // Selecting code
    if (myState == GameState.InGame && !(myWorld.myHUD.isMenuOpen()) && !(myWorld.myHUD.isPlanetInterfaceOpen())) {
      myWorld.mySelectedEntities = new LinkedList<Entity>();

      if (myWorld.myEntities.size() > 0) {
        for (int i = 0; i < myWorld.myEntities.size(); i++) {
          if (myWorld.myEntities.get(i) instanceof Planet || myWorld.myEntities.get(i) instanceof Ship) {
            if (myWorld.myEntities.get(i).getX() - myWorld.myEntities.get(i).mySize/2 < x && myWorld.myEntities.get(i).getX() + myWorld.myEntities.get(i).mySize/2 > x && myWorld.myEntities.get(i).getY() - myWorld.myEntities.get(i).mySize/2 < y && myWorld.myEntities.get(i).getY() + myWorld.myEntities.get(i).mySize/2 > y) {
              myWorld.mySelectedEntities.add(myWorld.myEntities.get(i));
            }
          }
        }
      }
    }

  }

  public void handleRightMouse(int x, int y) {

    // Changing X and Y values to be relative to the world
    x = (int) ((x - myRenderer.myWindow.width/2) / myRenderer.scale) - myRenderer.myWindow.xPos();
    y = (int) ((y - myRenderer.myWindow.height/2) / myRenderer.scale) - myRenderer.myWindow.yPos();
    
    if (myState == GameState.InGame && !(myWorld.myHUD.isMenuOpen())) {
      if (myWorld.mySelectedEntities.size() > 0) {
        for (int i = 0; i < myWorld.mySelectedEntities.size(); i++) {
          if (myWorld.mySelectedEntities.get(i) instanceof Ship) {
            Ship myShip = (Ship) myWorld.mySelectedEntities.get(i);

            myShip.destinationX = x;
            myShip.destinationY = y;

          }
        }
      }
    }

  }

  private void render() {

    // Running renderer
    if (this.myWorld == null || !(myState == GameState.InGame)) {
      myRenderer.render();
    } else {
      myRenderer.render(this.myWorld.myEntities, this.myWorld.mySelectedEntities);
    }

  }

  // Little print method for the start
  public void printStart() {
    System.out.println("Game Alpha Started");
  }

  public void toggleDebug() {
    debug = !debug;
  }

  // Methods for setting the games state
  public void setStateInGame() {

    myWorld = new World(this);

    myState = GameState.InGame;

  }

  public void setStateMenu() {

    if (!(myRenderer == null)) {
      myRenderer.scale = 1;

      if (!(myRenderer.myWindow == null)) {

        myRenderer.myWindow.xPos(0);
        myRenderer.myWindow.yPos(0);
        myRenderer.myWindow.xVel(0);
        myRenderer.myWindow.yVel(0);

        myMainMenu = new MainMenu(this, myRenderer.myWindow.width, myRenderer.myWindow.height);

      } else {
        myMainMenu = new MainMenu(this, 1280, 1024);
      }
    } else {
      myMainMenu = new MainMenu(this, 1280, 1024);
    }
    
    debug = false;

    myState = GameState.Menu;

  }
  
}
