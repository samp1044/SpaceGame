/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import support.Settings;

/**
 *
 * @author Sami
 */
public class OwnMouseWheelListener implements MouseWheelListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            Settings.INCREASE_SLOT = true;
        } else if (e.getWheelRotation() > 0) {
            Settings.DECREASE_SLOT = true;
        }
    }
    
}
