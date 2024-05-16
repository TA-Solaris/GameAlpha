
// Importing packets
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Declaring the Input Handler
public class InputHandler extends KeyAdapter {

  // Defining reference to game
  protected Renderer myRenderer;

  // Constructor method
  public InputHandler (Renderer renderer) {

    // Getting reference to renderer
    myRenderer = renderer;

  }

  // Handling Keys
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if (myRenderer.myGame.myState == GameState.InGame) {

      if (key == KeyEvent.VK_ESCAPE) {
        myRenderer.myGame.myWorld.myHUD.toggleMenu();
      }
      if (key == KeyEvent.VK_B) {
        myRenderer.myGame.toggleDebug();
      }

      if (!(myRenderer.myGame.myWorld.myHUD.isMenuOpen())) {
        if (key == KeyEvent.VK_W) {
          myRenderer.myWindow.yVel(1);
        }
        if (key == KeyEvent.VK_S) {
          myRenderer.myWindow.yVel(-1);
        }
        if (key == KeyEvent.VK_A) {
          myRenderer.myWindow.xVel(1);
        }
        if (key == KeyEvent.VK_D) {
          myRenderer.myWindow.xVel(-1);
        }
        if (key == KeyEvent.VK_COMMA) {
          myRenderer.scale(-1);
        }
        if (key == KeyEvent.VK_PERIOD) {
          myRenderer.scale(1);
        }
      }
    }

    if (key == KeyEvent.VK_F11) {

      // Toggling fullscreen
      if (myRenderer.myWindow.windowed) {
        myRenderer.myWindow.windowedFullscreen();
      } else {
        myRenderer.myWindow.windowed();
      }

      // Making sure menus scale
      if (myRenderer.myGame.myState == GameState.Menu) {
        myRenderer.myGame.setStateMenu();
      } else if (myRenderer.myGame.myState == GameState.InGame) {
        myRenderer.myGame.myWorld.initialiseHUD(myRenderer.myWindow.width, myRenderer.myWindow.height);
      }
      
    }
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();

    if (myRenderer.myGame.myState == GameState.InGame && !(myRenderer.myGame.myWorld.myHUD.isMenuOpen())) {
      if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
        myRenderer.myWindow.yVel(0);
      }
      if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
        myRenderer.myWindow.xVel(0);
      }
    }
    
  }
  
}
