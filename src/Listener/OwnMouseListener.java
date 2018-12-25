/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

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
        
        if (e.getButton() == Settings.KeyIGNORE_MOUSE_MOVEMENT) {
            Settings.IGNORE_MOUSE_MOVEMENT = true;
        }
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            Settings.MOUSE_LEFT_DOWN = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            Settings.MOUSE_RIGHT_DOWN = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == Settings.KeyIGNORE_MOUSE_MOVEMENT) {
            Settings.IGNORE_MOUSE_MOVEMENT = false;
        }
        
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
        
        if (e.getButton() == Settings.KeySLOT1) {
            Settings.SLOT_VALUE = 0;
        }
        
        if (e.getButton() == Settings.KeySLOT2) {
            Settings.SLOT_VALUE = 1;
        }
        
        if (e.getButton() == Settings.KeySLOT3) {
            Settings.SLOT_VALUE = 2;
        }
        
        if (e.getButton() == Settings.KeySLOT4) {
            Settings.SLOT_VALUE = 3;
        }
        
        if (e.getButton() == Settings.KeySLOT5) {
            Settings.SLOT_VALUE = 4;
        }
        
        if (e.getButton() == Settings.KeySLOT6) {
            Settings.SLOT_VALUE = 5;
        }
        
        if (e.getButton() == Settings.KeySLOT7) {
            Settings.SLOT_VALUE = 6;
        }
        
        if (e.getButton() == Settings.KeySLOT8) {
            Settings.SLOT_VALUE = 7;
        }
        
        if (e.getButton() == Settings.KeySLOT9) {
            Settings.SLOT_VALUE = 8;
        }
        
        if (e.getButton() == Settings.KeySLOT0) {
            Settings.SLOT_VALUE = 9;
        }
        
        if (e.getButton() == Settings.KeyDEBUG_INFO) {
            if (Settings.showDebug) {
                Settings.showDebug = false;
            } else {
                Settings.showDebug = true;
            }
        }
        
        if (e.getButton() == Settings.KeySCREENSHOT) {
            Settings.takeScreenshot = true;
        }
        
        if (e.getButton() == Settings.KeyINVENTORY) {
            Settings.INVENTORY_KEY_DOWN = true;
        }
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            Settings.MOUSE_LEFT_DOWN = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            Settings.MOUSE_RIGHT_DOWN = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
