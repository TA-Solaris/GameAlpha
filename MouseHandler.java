
// Importing packets
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

// Declaring the Mouse Handler
public class MouseHandler extends MouseAdapter {

  // Defining References
  Renderer myRenderer;

  // Constructor method
  public MouseHandler(Renderer renderer) {

    // Making reference to parent
    myRenderer = renderer;

  }

  public void mouseWheelMoved(MouseWheelEvent e) {
    if (myRenderer.myGame.myState == GameState.InGame) {
      if (!(myRenderer.myGame.myWorld.myHUD.isMenuOpen())) {
        myRenderer.scale(-e.getWheelRotation());
      }
    }
  }

  public void mousePressed(MouseEvent e) {

    if (SwingUtilities.isLeftMouseButton(e)) {
      myRenderer.handleLeftMouse(e.getX(), e.getY());
    }
    if (SwingUtilities.isRightMouseButton(e)) {
      myRenderer.handleRightMouse(e.getX(), e.getY());
    }
    
  }

  public void mouseReleased(MouseEvent e) {

    // Just here so I remember about it

  }

}
