/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Sami
 */
public class OwnKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyList.FORWARD:
                KeyList.FORWARD_Key_down = true;
                break;
                
            case KeyList.LEFT:
                KeyList.LEFT_Key_down = true;
                break;
                
            case KeyList.BACKWARD:
                KeyList.BACKWARD_Key_down = true;
                break;
                
            case KeyList.RIGHT:
                KeyList.RIGHT_Key_down = true;
                break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyList.FORWARD:
                KeyList.FORWARD_Key_down = false;
                break;
                
            case KeyList.LEFT:
                KeyList.LEFT_Key_down = false;
                break;
                
            case KeyList.BACKWARD:
                KeyList.BACKWARD_Key_down = false;
                break;
                
            case KeyList.RIGHT:
                KeyList.RIGHT_Key_down = false;
                break;
        }
    }
    
}
