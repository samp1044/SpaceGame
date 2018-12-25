/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import support.FixedCollisionBox;
import support.Resources;
import units.Bullet;
import utils.Logger;
import utils.Vector2D;
/**
 *
 * @author Sami
 */
public class Main {
    
    static Logger logger = new Logger(Main.class);
    
    public static void main(String[] args) {
        logger.info("Programm gestartet. Calling MainLoop");
        MainLoop main = new MainLoop(); //Starten der Hauptmethode des Spiels
        
        /*MainLoop.resources = new Resources();
        MainLoop.resources.loadResources();*/
        //new Bullet(Bullet.LASER_RED, 1, 6, 60, 0, 0, new Vector2D(), 0, 0);
        //collisionBoxTesting();
        
        /*Inventory inventory = new Inventory(10);
        Inventory inventory2 = new Inventory(1);
        
        for (int i = 0;i < 64;i++) {
            inventory.addItem(0, new Auto());
        }
        
        System.out.println(""+inventory.isEmpty()+" "+inventory2.isEmpty());
        
        inventory2.addItems(0, inventory.getAllItems(0));
        
        System.out.println(""+inventory.isEmpty()+" "+inventory2.isEmpty());
        */
        /*for (int i = 0;i < 64;i++) {
            if (inventory.getAllItems(0)[i] != null) {
                length += 1;
            }
        }*/
        
        //System.out.println(""+length);
    }
    
    private static void collisionBoxTesting() {
        final FixedCollisionBox b = new FixedCollisionBox(50,50,20,20);
        final FixedCollisionBox b2 = new FixedCollisionBox(50,50,20,20);
        
        System.out.println(""+b);
        System.out.println(""+b2);
        
        b2.setRotation(-45);
        
        System.out.println(""+b2);
        System.out.println(""+b2.isOverlapping(b));
        //b2.updateBox(72, 56);
        //System.out.println(""+b2.isOverlapping(b));
        
        class LeFrame extends JFrame {
            public LeFrame() {
                this.setSize(1200, 800);
                this.setVisible(true);
            }
            
            public void paint(Graphics g) {
                g.drawRect((int)b.x, (int)b.y, b.width, b.height);
                
                Graphics2D g2d = (Graphics2D)g.create();
                
                b.draw(g2d);
                b2.draw(g2d);
            }
        };
        
        new LeFrame();
    }
}

class Auto {
    public boolean equals(Object object) {
        return true;
    }
}
