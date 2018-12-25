/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

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
        if (e.getKeyCode() == Settings.KeySHOOT) {
            Settings.SHOOT_Key_down = true;
        }
        
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
        
        if (e.getKeyCode() == Settings.KeyIGNORE_MOUSE_MOVEMENT) {
            Settings.IGNORE_MOUSE_MOVEMENT = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Settings.ESCAPE_DOWN = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Settings.SHIFT_DOWN = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == Settings.KeyIGNORE_MOUSE_MOVEMENT) {
            Settings.IGNORE_MOUSE_MOVEMENT = false;
        }
        
        if (e.getKeyCode() == Settings.KeySHOOT) {
            Settings.SHOOT_Key_down = false;
        }
        
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
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Settings.ESCAPE_DOWN = false;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT1) {
            Settings.SLOT_VALUE = 0;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT2) {
            Settings.SLOT_VALUE = 1;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT3) {
            Settings.SLOT_VALUE = 2;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT4) {
            Settings.SLOT_VALUE = 3;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT5) {
            Settings.SLOT_VALUE = 4;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT6) {
            Settings.SLOT_VALUE = 5;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT7) {
            Settings.SLOT_VALUE = 6;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT8) {
            Settings.SLOT_VALUE = 7;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT9) {
            Settings.SLOT_VALUE = 8;
        }
        
        if (e.getKeyCode() == Settings.KeySLOT0) {
            Settings.SLOT_VALUE = 9;
        }
        
        if (e.getKeyCode() == Settings.KeyINVENTORY) {
            Settings.INVENTORY_KEY_DOWN = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Settings.SHIFT_DOWN = false;
        }
        
        if (e.getKeyCode() == Settings.KeyDEBUG_INFO) {
            if (Settings.showDebug) {
                Settings.showDebug = false;
            } else {
                Settings.showDebug = true;
            }
        }
        
        if (e.getKeyCode() == Settings.KeySCREENSHOT) {
            Settings.takeScreenshot = true;
        }
    }
    
}
