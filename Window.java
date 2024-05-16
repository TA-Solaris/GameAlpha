
// Importing Packages
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

// Inheriting the Canvas
public class Window extends Canvas {

  // Defining Constants
  private final int MIN_WIDTH = 1280;
  private final int MIN_HEIGHT = 1024;
  private final int MAX_WIDTH = 3840;
  private final int MAX_HEIGHT = 2160;
  private final int PREF_WIDTH = MIN_WIDTH;
  private final int PREF_HEIGHT = MIN_HEIGHT;
  private final int WINDOW_SPEED = 10;

  // Defining Variables
  private double xPos = 0;
  private double yPos = 0;
  private double xVel = 0;
  private double yVel = 0;
  public int width = 0;
  public int height = 0;
  public Renderer myRenderer;
  public boolean windowed;
  private JFrame myFrame;

  public int prevWidth;
  public int prevHeight;
  
  // Defining Constructor Method
  public Window(String title, Renderer renderer) {

    myRenderer = renderer;

    // Creating the frame
    myFrame = new JFrame(title);

    // Setting up frame (misc.)
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myFrame.setResizable(false);
    myFrame.add(myRenderer);

    // Setting the game to be windowed on boot
    windowed = true;
    windowed();

    // Starting the game
    myRenderer.start();
    
  }

  // Setter Methods
  public void xVel(int value) {
    xVel = value * WINDOW_SPEED;
  }

  public void yVel(int value) {
    yVel = value * WINDOW_SPEED;
  }

  public void xPos(int x) {
    xPos = x;
  }

  public void yPos(int y) {
    yPos = y;
  }

  // Getter methods
  public int xPos() {
    return (int) xPos;
  }

  public int yPos() {
    return (int) yPos;
  }

  // Defining Methods
  public void update() {

    // Updating position
    xPos += xVel / myRenderer.scale;
    yPos += yVel / myRenderer.scale;

  }

  public void windowedFullscreen() {

    // Saving windowed state
    prevWidth = getWidth();
    prevHeight = getHeight();

    // Setting windowed to false
    windowed = false;

    // Fullscreen code
    myFrame.dispose();
    myFrame.setUndecorated(true);
    width = getToolkit().getScreenSize().width;
    height = getToolkit().getScreenSize().height;
    myFrame.setBounds(0,0,width,height);

    myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    myFrame.setVisible(true);

    myRenderer.requestFocus();

  }

  public void windowed() {

    // Setting frame sizes
    myFrame.setPreferredSize(new Dimension (PREF_WIDTH, PREF_HEIGHT));
    myFrame.setMinimumSize(new Dimension (MIN_WIDTH, MIN_HEIGHT));
    myFrame.setMaximumSize(new Dimension (MAX_WIDTH, MAX_HEIGHT));

    width = PREF_WIDTH;
    height = PREF_HEIGHT;

    myFrame.setResizable(true);
    myFrame.setSize(new Dimension(width, height));
    myFrame.setResizable(false);
    myFrame.setLocationRelativeTo(null);

    windowed = true;

    myFrame.dispose();
    myFrame.setUndecorated(false);

    myFrame.setVisible(true);

    myRenderer.requestFocus();

  }

}
