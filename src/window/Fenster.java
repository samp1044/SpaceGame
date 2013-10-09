/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Canvas;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Sami
 */
public class Fenster extends JFrame {
    private int height;
    private int width;
    //change has happened
    
    public Fenster(Canvas canvas) {
        setTitle("Space Game");
        
        OwnKeyListener keyListener = new OwnKeyListener();
        OwnMouseMotionListener mouseMotionListener = new OwnMouseMotionListener();
        
        canvas.addKeyListener(keyListener);
        canvas.addMouseMotionListener(mouseMotionListener);
        
        addComponentListener(new OwnComponentListener());
        addKeyListener(keyListener);
        add(canvas);
        pack();
        addWindowListener((WindowListener) new WindowClosingListener());
        setIgnoreRepaint(true);
        setVisible(true);
    }
}
