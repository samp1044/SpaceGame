/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import main.MainLoop;

/**
 *
 * @author Sami
 */
public class WindowClosingListener implements WindowListener {
    
    public WindowClosingListener() {
        
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Fenster geschlossen");
        System.exit(0);
    }
    
    @Override
    public void windowClosed(WindowEvent e) {}
    
    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("fenster offen");
    }
    
    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("activated");
        MainLoop.focus = true;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("not activated");
        MainLoop.focus = false;
    }
}
