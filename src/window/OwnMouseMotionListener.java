/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import main.MainLoop;

/**
 *
 * @author Sami
 */
public class OwnMouseMotionListener implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        MainLoop.mausX = e.getX();
        MainLoop.mausY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MainLoop.mausX = e.getX();
        MainLoop.mausY = e.getY();
    }
    
}
