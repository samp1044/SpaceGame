/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import main.MainLoop;
import utils.Logger;

/**
 *
 * @author Sami
 */
public class WindowClosingListener implements WindowListener {
    Logger logger = new Logger(WindowClosingListener.class);
            
    public WindowClosingListener() {
        
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        logger.info("Main Window closed");
        logger.info("Exiting Program");
        System.exit(0);
    }
    
    @Override
    public void windowClosed(WindowEvent e) {}
    
    @Override
    public void windowOpened(WindowEvent e) {
        logger.info("Main Window opened");
    }
    
    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {
        logger.info("Main Window activated");
        MainLoop.focus = true;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        logger.info("Main Window not activated");
        MainLoop.focus = false;
    }
}
