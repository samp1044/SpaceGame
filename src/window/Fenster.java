/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
        
        addComponentListener(new OwnComponentListener());
        addKeyListener(keyListener);
        add(canvas);
        pack();
        addWindowListener((WindowListener) new WindowClosingListener());
        setIgnoreRepaint(true);
        setVisible(true);
    }
}
