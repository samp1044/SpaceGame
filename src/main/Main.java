/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import utils.Logger;
/**
 *
 * @author Sami
 */
public class Main {
    
    static Logger logger = new Logger(Main.class);
    
    public static void main(String[] args) {
        logger.info("Programm gestartet. Calling MainLoop");
        MainLoop main = new MainLoop(); //Starten der Hauptmethode des Spiels
        
        //collisionBoxTesting();
    }
    
    /*private static void collisionBoxTesting() {
        final CollisionBox b = new CollisionBox(50,50,20,20);
        final FixedCollisionBox b2 = new FixedCollisionBox(70,50,20,20);
        b2.setRotation(45);
        System.out.println(""+b2.isOverlapping(b));
        b2.updateBox(72, 56);
        System.out.println(""+b2.isOverlapping(b));
        
        class LeFrame extends JFrame {
            public LeFrame() {
                this.setSize(1200, 800);
                this.setVisible(true);
            }
            
            public void paint(Graphics g) {
                g.drawRect((int)b.x, (int)b.y, b.width, b.height);
                
                g.drawLine((int)b2.coord[0][0].getX(), (int)b2.coord[0][0].getY(), (int)b2.coord[b2.coord.length - 1][0].getX(), (int)b2.coord[b2.coord.length - 1][0].getY());
                g.drawLine((int)b2.coord[b2.coord.length - 1][0].getX(), (int)b2.coord[b2.coord.length - 1][0].getY(), (int)b2.coord[b2.coord.length - 1][b2.coord[0].length - 1].getX(), (int)b2.coord[b2.coord.length - 1][b2.coord[0].length - 1].getY());
                g.drawLine((int)b2.coord[b2.coord.length - 1][b2.coord[0].length - 1].getX(), (int)b2.coord[b2.coord.length - 1][b2.coord[0].length - 1].getY(), (int)b2.coord[0][b2.coord[0].length - 1].getX(), (int)b2.coord[0][b2.coord[0].length - 1].getY());
                g.drawLine((int)b2.coord[0][b2.coord[0].length - 1].getX(), (int)b2.coord[0][b2.coord[0].length - 1].getY(), (int)b2.coord[0][0].getX(), (int)b2.coord[0][0].getY());
            }
        };
        
        new LeFrame();
    }*/
}
