/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import Listener.OwnComponentListener;
import Listener.OwnMouseMotionListener;
import Listener.OwnKeyListener;
import Listener.OwnMouseWheelListener;
import Listener.WindowClosingListener;
import Listener.OwnMouseListener;
import java.awt.Canvas;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Sami
 */
public class Fenster extends JFrame {
    
    public Fenster(Canvas canvas) {
        setTitle("Space Game");
        
        OwnKeyListener keyListener = new OwnKeyListener();
        OwnMouseMotionListener mouseMotionListener = new OwnMouseMotionListener();
        
        canvas.addKeyListener(keyListener);
        canvas.addMouseMotionListener(mouseMotionListener);
        canvas.addMouseListener(new OwnMouseListener());
        
        addMouseWheelListener(new OwnMouseWheelListener());
        addComponentListener(new OwnComponentListener());
        addKeyListener(keyListener);
        addMouseMotionListener(mouseMotionListener);
        add(canvas);
        pack();
        addWindowListener((WindowListener) new WindowClosingListener());
        
        //setResizable(false);
        setIgnoreRepaint(true);
        setVisible(true);
    }
}
