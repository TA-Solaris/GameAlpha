
// Importing packets
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Polygon;

import java.util.LinkedList;

import Entities.*;
import GraphicalObjects.*;
import Tools.MyFont;

// Making the Renderer class out of (inheriting from) the canvas
public class Renderer extends Canvas {

  // Defining constants
  private final double MAX_SCALE = 2.5;
  private final double MIN_SCALE = 0.2;
  private final double SCALE_CHANGE_RATE = 0.1;
  private final int FONT_SIZE = 10;
  private final double RADIAN_CIRCLE = Math.PI * 2;
  private final int SELECT_GAP = 4;

  // Defining variables
  private BufferStrategy myBufferStrategy;
  private Graphics myGraphics;

  public double scale = 1;

  // Defining the window and game references
  public Window myWindow;
  public Game myGame;

  public Renderer(String title, Game game) {

    this.myGame = game;

    // Creating the window
    this.myWindow = new Window(title, this);

  }

  public void start() {

    // Adding the key listener
    this.addKeyListener(new InputHandler(this));
    MouseHandler myMouseHandler = new MouseHandler(this);
    this.addMouseListener(myMouseHandler);
    this.addMouseWheelListener(myMouseHandler);

    // Starting the game
    myGame.start();

  }

  public void render() {

    this.updateBuffersAndGraphics();

    // Safeguarding
    if (myBufferStrategy == null || myGraphics == null) {
      return;
    }

    this.clearScreen();

  }

  public void render(LinkedList<Entity> myEntities, LinkedList<Entity> mySelectedEntities) {

    render();

    // Safeguarding
    if (myBufferStrategy == null || myGraphics == null) {
      return;
    }
    
    // Drawing entities
    if (myEntities.size() > 0) {
      for (int i = 0; i < myEntities.size(); i++) {
        
        // Drawing the orbit if it has a parent
        if (myEntities.get(i) instanceof Star || myEntities.get(i) instanceof Planet || myEntities.get(i) instanceof Ghost) {
          if (!(myEntities.get(i).myParent == null)) {
            this.drawOrbit(myEntities.get(i));
          }
        
          if (!(myEntities.get(i) instanceof Ghost)) {
            this.drawCircle(myEntities.get(i).getX(), myEntities.get(i).getY(), myEntities.get(i).mySize, myEntities.get(i).myColour);

            // Drawing glow effect
            if (myEntities.get(i) instanceof Star) {
              Color glow = new Color(myEntities.get(i).myColour.getRed(), myEntities.get(i).myColour.getBlue(), myEntities.get(i).myColour.getGreen(), 25);
              for (int j = 1; j < 300; j += 40) {
                this.drawCircle(myEntities.get(i).getX(), myEntities.get(i).getY(), myEntities.get(i).mySize + j, glow);
              }
            }
          }
        } else if (myEntities.get(i) instanceof Ship) {

          Ship myShip = (Ship) myEntities.get(i);

          // Drawing ships
          if (!myShip.atDestination()) {
            this.drawShipTravel(myShip.myColour, myShip.getX(), myShip.getY(), myShip.destinationX, myShip.destinationY);
          }

          this.drawShip(myShip.myColour, myShip.mySize, myShip.getX(), myShip.getY(), myShip.getAngle());

        }
      }
    }

    // Drawing selections
    if (mySelectedEntities.size() > 0) {
      for (int i = 0; i < mySelectedEntities.size(); i++) {
        drawSelection(mySelectedEntities.get(i).getX(), mySelectedEntities.get(i).getY(), mySelectedEntities.get(i).mySize);
      }
    }

    // Drawing Entities Names
    if (myEntities.size() > 0) {
      for (int i = 0; i < myEntities.size(); i++) {

        if (myEntities.get(i).owner == null) {
          myGraphics.setColor(Color.WHITE);
        } else {
          myGraphics.setColor(myEntities.get(i).owner.myColour);
        }
        myGraphics.setFont(new MyFont( (int) (FONT_SIZE * scale)));

        if (!(myEntities.get(i).name == "")) {
          myGraphics.drawString(myEntities.get(i).type + " - " + myEntities.get(i).name, (int) ((myEntities.get(i).getX() + myWindow.xPos() - myEntities.get(i).mySize/2) * scale) + myWindow.width/2, (int) ((myEntities.get(i).getY() + myWindow.yPos() - myEntities.get(i).mySize/2 - FONT_SIZE) * scale) + myWindow.height/2);
        }
      }
    }

  }

  public void renderDebug(int fps, int tps) {

    // Safeguarding
    if (myBufferStrategy == null || myGraphics == null) {
      return;
    }

    // Drawing text
    myGraphics.setColor(Color.WHITE);
    myGraphics.setFont(new MyFont(FONT_SIZE));
    myGraphics.drawString("DEBUG MENU", 100, 80);
    myGraphics.drawString("FPS: " + fps, 100, 100);
    myGraphics.drawString("TPS: " + tps, 100, 120);
    myGraphics.drawString("Window: (" + this.myWindow.xPos() + ", " + this.myWindow.yPos() + ") [x, y]", 100, 140);
    myGraphics.drawString("Window: Scale " + (float) scale, 100, 160);

    // Drawing crosshair
    myGraphics.drawLine(this.myWindow.width / 2 - 30, this.myWindow.height / 2, this.myWindow.width / 2 + 30, this.myWindow.height / 2);
    myGraphics.drawLine(this.myWindow.width / 2, this.myWindow.height / 2 - 30, this.myWindow.width / 2, this.myWindow.height / 2 + 30);

  }

  public void renderDebug(LinkedList<Entity> myEntities, int fps, int tps) {
    
    renderDebug(fps, tps);

    // Safeguarding
    if (myBufferStrategy == null || myGraphics == null) {
      return;
    }

    // Drawing entities debug
    if (myEntities.size() > 0) {
      for (int i = 0; i < myEntities.size(); i++) {

        this.drawRect(myEntities.get(i).getX(), myEntities.get(i).getY(), myEntities.get(i).mySize);

        myGraphics.setColor(Color.WHITE);
        myGraphics.setFont(new MyFont( (int) (FONT_SIZE * scale)));
        if (myEntities.get(i).name == "") {
          myGraphics.drawString(myEntities.get(i).type, (int) ((myEntities.get(i).getX() + myWindow.xPos() - myEntities.get(i).mySize/2) * scale) + myWindow.width/2, (int) ((myEntities.get(i).getY() + myWindow.yPos() - myEntities.get(i).mySize/2 - FONT_SIZE) * scale) + myWindow.height/2);
        }
        myGraphics.drawString("(" + myEntities.get(i).getX() + ", " + myEntities.get(i).getY() + ") [x, y]", (int) ((myEntities.get(i).getX() + myWindow.xPos() - myEntities.get(i).mySize/2) * scale) + myWindow.width/2, (int) ((myEntities.get(i).getY() + myWindow.yPos() + myEntities.get(i).mySize/2 + FONT_SIZE) * scale) + myWindow.height/2);

      }
    }

  }

  public void renderObjects(LinkedList<GraphicalObject> myObjects) {

    // Safeguarding
    if (myBufferStrategy == null || myGraphics == null) {
      return;
    }

    // Drawing menu objects
    if (myObjects.size() > 0) {
      for (int i = 0; i < myObjects.size(); i++) {
        if (!(myObjects.get(i).hidden)) {

          if (myObjects.get(i) instanceof Text) {
            Text myText = (Text) myObjects.get(i);

            myGraphics.setColor(myText.textColour);
            myGraphics.setFont(new MyFont(myText.size));
            myGraphics.drawString(myText.text, myText.x - myText.text.length() * myText.size / 3, myText.y - myText.size / 2);
          }
          if (myObjects.get(i) instanceof Button) {
            Button myButton = (Button) myObjects.get(i);

            myGraphics.setColor(myButton.borderColour);
            myGraphics.fillRect(myButton.x - 2, myButton.y - 2, myButton.width + 4, myButton.height + 4);

            myGraphics.setColor(myButton.backColour);
            myGraphics.fillRect(myButton.x, myButton.y, myButton.width, myButton.height);

            if (myButton.enabled()) {
              myGraphics.setColor(myButton.textColour);
            } else {
              myGraphics.setColor(myButton.textDisabledColour);
            }
            myGraphics.setFont(new MyFont(myButton.textSize));
            myGraphics.drawString(myButton.text, myButton.x + myButton.width / 2 - (int) (myButton.text.length() * myButton.textSize / 3.3), myButton.y + myButton.height / 2 + (int) (myButton.textSize / 2.2));
          }
          if (myObjects.get(i) instanceof Box) {
            Box myBox = (Box) myObjects.get(i);

            myGraphics.setColor(myBox.borderColour);
            myGraphics.fillRect(myBox.x - 2, myBox.y - 2, myBox.width + 4, myBox.height + 4);

            myGraphics.setColor(myBox.backColour);
            myGraphics.fillRect(myBox.x, myBox.y, myBox.width, myBox.height);
          
          }
          if (myObjects.get(i) instanceof Images) {
            Images myImage = (Images) myObjects.get(i);

            myGraphics.drawImage(myImage.myImage, myImage.x, myImage.y, myImage.width, myImage.height, null);
          }
          if (myObjects.get(i) instanceof Line) {
            Line myLine = (Line) myObjects.get(i);

            myGraphics.setColor(myLine.myColour);
            myGraphics.drawLine(myLine.x, myLine.y, myLine.endx, myLine.endy);
          }
          if (myObjects.get(i) instanceof Circle) {
            Circle myCircle = (Circle) myObjects.get(i);

            myGraphics.setColor(myCircle.myColour);
            myGraphics.fillRoundRect(myCircle.x - myCircle.mySize/2, myCircle.y - myCircle.mySize/2, myCircle.mySize, myCircle.mySize, myCircle.mySize, myCircle.mySize);
          }

        }
      }
    }

  }

  public void displayGraphics() {

    // Safeguarding
    if (myBufferStrategy == null || myGraphics == null) {
      return;
    }

    // Showing the graphics to screen
    myGraphics.dispose();
    myBufferStrategy.show();

  }

  private void updateBuffersAndGraphics() {

    // Getting the buffer strategy
    myBufferStrategy = this.getBufferStrategy();

    // Making sure there are buffers
    if (myBufferStrategy == null) {
      this.createBufferStrategy(3);
      return;
    }

    // Getting the graphics
    myGraphics = myBufferStrategy.getDrawGraphics();

  }

  private void clearScreen() {
    // Setting the background
    myGraphics.setColor(Color.BLACK);
    myGraphics.fillRect(0, 0, myWindow.width, myWindow.height);
  }

  // Method for drawing a circle on a graphic taking it colour, size and center position
  private void drawCircle(int xPos, int yPos, int size, Color myColour) {
    myGraphics.setColor(myColour);
    myGraphics.fillRoundRect((int) ((xPos + myWindow.xPos() - size/2) * scale) + myWindow.width/2, (int) ((yPos + myWindow.yPos() - size/2) * scale) + myWindow.height/2, (int) (size * scale), (int) (size * scale), (int) (size * scale), (int) (size * scale));
  }

  // Method for drawing an orbit
  private void drawOrbit(Entity myEntity) {
    myGraphics.setColor(Color.WHITE);
    myGraphics.drawRoundRect((int) ((myEntity.myParent.getX() + myWindow.xPos() - myEntity.myDistance) * scale) + myWindow.width/2 , (int) ((myEntity.myParent.getY() + myWindow.yPos() - myEntity.myDistance) * scale) + myWindow.height/2, (int) (myEntity.myDistance * 2 * scale), (int) (myEntity.myDistance * 2 * scale), (int) (myEntity.myDistance * 2 * scale), (int) (myEntity.myDistance * 2 * scale));
  }

  private void drawRect(int xPos, int yPos, int size) {
    myGraphics.setColor(Color.WHITE);
    myGraphics.drawRect((int) ((xPos + myWindow.xPos() - size/2) * scale) + myWindow.width/2, (int) ((yPos + myWindow.yPos() - size/2) * scale) + myWindow.height/2, (int) (size * scale), (int) (size * scale));
  }

  private void drawShip(Color colour, int size, int x, int y, double angle) {
    myGraphics.setColor(colour);

    Polygon myPolygon = new Polygon();

    myPolygon.addPoint((int) ((x + (size * Math.sin(angle) / 2) + myWindow.xPos()) * scale) + myWindow.width/2, (int) ((y + (size * Math.cos(angle) / 2) + myWindow.yPos()) * scale)+ myWindow.height/2);
    myPolygon.addPoint((int) ((x + (size * Math.sin(angle + RADIAN_CIRCLE * 4/11) / 2) + myWindow.xPos()) * scale) + myWindow.width/2, (int) ((y + (size * Math.cos(angle + RADIAN_CIRCLE * 4/11) / 2) + myWindow.yPos()) * scale) + myWindow.height/2);
    myPolygon.addPoint((int) ((x + (size * Math.sin(angle + RADIAN_CIRCLE * 7/11) / 2) + myWindow.xPos()) * scale) + myWindow.width/2, (int) ((y + (size * Math.cos(angle + RADIAN_CIRCLE * 7/11) / 2) + myWindow.yPos()) * scale) + myWindow.height/2);

    myGraphics.fillPolygon(myPolygon);
  }

  private void drawShipTravel(Color colour, int x, int y, int desX, int desY) {
    myGraphics.setColor(colour);

    myGraphics.drawLine((int) ((x + myWindow.xPos()) * scale) + myWindow.width/2, (int) ((y + myWindow.yPos()) * scale) + myWindow.height/2, (int) ((desX + myWindow.xPos()) * scale) + myWindow.width/2, (int) ((desY + myWindow.yPos()) * scale) + myWindow.height/2);
  }

  private void drawSelection(int x, int y, int size) {
    myGraphics.setColor(myGame.myWorld.myPlayer.myColour);
    myGraphics.drawRoundRect((int) ((x - SELECT_GAP/2 + myWindow.xPos() - size/2) * scale) + myWindow.width/2 , (int) ((y - SELECT_GAP/2 + myWindow.yPos() - size/2) * scale) + myWindow.height/2, (int) ((size + SELECT_GAP) * scale), (int) ((size + SELECT_GAP) * scale), (int) ((size + SELECT_GAP) * scale), (int) ((size + SELECT_GAP) * scale));
  }

  public void scale (int direction) {

    if (direction == -1 && !(scale <= MIN_SCALE)) {
      scale -= SCALE_CHANGE_RATE;
    }
    if (direction == 1 && !(scale >= MAX_SCALE)) {
      scale += SCALE_CHANGE_RATE;
    }

  }

  // Passing mouse event up
  public void handleLeftMouse(int x, int y) {
    myGame.handleLeftMouse(x, y);
  }

  public void handleRightMouse(int x, int y) {
    myGame.handleRightMouse(x, y);
  }
  
}
