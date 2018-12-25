/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import main.MainLoop;

/**
 *
 * @author Sami
 */
public class OwnComponentListener implements ComponentListener {

    @Override
    public void componentResized(ComponentEvent e) {
        MainLoop.resized = true;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
    
}
