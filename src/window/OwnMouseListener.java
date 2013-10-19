/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import support.Settings;

/**
 *
 * @author Sami
 */
public class OwnMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == Settings.KeySHOOT) {
            Settings.SHOOT_Key_down = true;
        }
        
        if (e.getButton() == Settings.KeyFORWARD) {
            Settings.FORWARD_Key_down = true;
        }
        
        if (e.getButton() == Settings.KeyBACKWARD) {
            Settings.BACKWARD_Key_down = true;
        }
        
        if (e.getButton() == Settings.KeyLEFT) {
            Settings.LEFT_Key_down = true;
        }
        
        if (e.getButton() == Settings.KeyRIGHT) {
            Settings.RIGHT_Key_down = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == Settings.KeySHOOT) {
            Settings.SHOOT_Key_down = false;
        }
        
        if (e.getButton() == Settings.KeyFORWARD) {
            Settings.FORWARD_Key_down = false;
        }
        
        if (e.getButton() == Settings.KeyBACKWARD) {
            Settings.BACKWARD_Key_down = false;
        }
        
        if (e.getButton() == Settings.KeyLEFT) {
            Settings.LEFT_Key_down = false;
        }
        
        if (e.getButton() == Settings.KeyRIGHT) {
            Settings.RIGHT_Key_down = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
