/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.MainLoop;

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
            case KeyEvent.VK_W:
                MainLoop.W_pressed = true;
                break;
                
            case KeyEvent.VK_A:
                MainLoop.A_pressed = true;
                break;
                
            case KeyEvent.VK_S:
                MainLoop.S_pressed = true;
                break;
                
            case KeyEvent.VK_D:
                MainLoop.D_pressed = true;
                break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                MainLoop.W_pressed = false;
                break;
                
            case KeyEvent.VK_A:
                MainLoop.A_pressed = false;
                break;
                
            case KeyEvent.VK_S:
                MainLoop.S_pressed = false;
                break;
                
            case KeyEvent.VK_D:
                MainLoop.D_pressed = false;
                break;
        }
    }
    
}
