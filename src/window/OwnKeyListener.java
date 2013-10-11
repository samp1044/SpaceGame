/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.MainLoop;
import support.Settings;

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
        if (e.getKeyCode() == Settings.KeyFORWARD) {
            Settings.FORWARD_Key_down = true;
        }
        
        if (e.getKeyCode() == Settings.KeyBACKWARD) {
            Settings.BACKWARD_Key_down = true;
        }
        
        if (e.getKeyCode() == Settings.KeyLEFT) {
            Settings.LEFT_Key_down = true;
        }
        
        if (e.getKeyCode() == Settings.KeyRIGHT) {
            Settings.RIGHT_Key_down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == Settings.KeyFORWARD) {
            Settings.FORWARD_Key_down = false;
        }
        
        if (e.getKeyCode() == Settings.KeyBACKWARD) {
            Settings.BACKWARD_Key_down = false;
        }
        
        if (e.getKeyCode() == Settings.KeyLEFT) {
            Settings.LEFT_Key_down = false;
        }
        
        if (e.getKeyCode() == Settings.KeyRIGHT) {
            Settings.RIGHT_Key_down = false;
        }
    }
    
}
