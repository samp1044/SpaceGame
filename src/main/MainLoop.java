/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import window.KeyList;
import window.Fenster;
import units.Thruster;
import units.Ship;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import units.Particle;
import utils.ObjectList;

/**
 *
 * @author Sami
 */
public class MainLoop extends Canvas implements Runnable {
    Fenster fenster;
    
    Image dbImage;
    Graphics g;
    
    Ship ship;
    
    int x;
    int y;
    
    int FPSlimiter;
    long FPS;
    long FPSstart;
    long FPScurrent;
    
    public static boolean focus;
    public static boolean resized;
    
    public static boolean particlesEnabled = true;
    
    public static ObjectList particles;
    
    public static int mausX;
    public static int mausY;
    
    public MainLoop() {
        this.setIgnoreRepaint(true);
        this.setSize(800,600);
        
        dbImage = null;
        
        this.fenster = new Fenster(this);
        
        this.x = 50;
        this.y = 50;
        
        this.FPSlimiter = 10;
        
        this.FPS = 60;
        this.FPSstart = System.currentTimeMillis();
        this.FPScurrent = 0;
        
        focus = true;
        resized = false;
        
        particles = new ObjectList();
        
        mausX = 0;
        mausY = 0;
        
        KeyList.FORWARD_Key_down = false;
        KeyList.BACKWARD_Key_down = false;
        KeyList.LEFT_Key_down = false;
        KeyList.RIGHT_Key_down = false;
        
        ship = new Ship("dummy.jpg",50,50,50,50);
        
        Thruster forwardThruster = new Thruster(Thruster.STANDART,10,10,0.02f,ship.getMiddleX(),ship.getMiddleY(),-6,26,180.0);
        forwardThruster.setParticleOutputRotationAngle(0);
        forwardThruster.setParticleOutputMultiplicator(40);
        
        ship.addForwardThruster(forwardThruster);
        ship.addBackwardThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),-17,-30,-50.0));
        ship.addBackwardThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),7,-30,50.0));
        ship.addFrontLeftThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),-22,-13,-90.0));
        ship.addFrontRightThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),12,-13,90.0));
        ship.addBackLeftThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),-22,13,-90.0));
        ship.addBackRightThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),12,13,90.0));
        
        this.run();
    }
    
    public void run() {
        while (true) {
            if(resized) {
                reCreateBuffer();
                resized = false;
            }
            
            if (focus) {
                if(KeyList.FORWARD_Key_down) {
                    ship.throttleForward();
                }

                if (KeyList.LEFT_Key_down) {
                    ship.throttleLeft();
                }

                if (KeyList.BACKWARD_Key_down) {
                    ship.throttleBackward();
                }

                if (KeyList.RIGHT_Key_down) {
                    ship.throttleRight();
                }
            } else {
             KeyList.FORWARD_Key_down = false;
             KeyList.BACKWARD_Key_down = false;
             KeyList.LEFT_Key_down = false;
             KeyList.RIGHT_Key_down = false;
            }
            
            ship.updateShip(mausX,mausY);
            
            try {
                Thread.sleep(this.FPSlimiter);
            } catch (Exception e) {
                System.out.println("Error: "+e);
            }
            
            fpsCount();
            reRender();
        }
    }
    
    public void render() {
        g.drawString("FPS: "+this.FPS, 30, 30);
        
        Graphics2D g2d = (Graphics2D)g.create();
        
        if (particlesEnabled) {
            for (int i = 0;i < particles.size();i++) {
                Particle particle = (Particle)particles.getElementAt(i);

                if (particle.draw(g2d)) {
                    particles.remove(i);
                }
            }
        }
        
        ship.draw(g2d);
    }
    
    private void fpsCount() {
        if (System.currentTimeMillis() - this.FPSstart <= 1000) {
            this.FPScurrent++;
        } else {
            this.FPS = this.FPScurrent;
            this.FPScurrent = 0;
            this.FPSstart = System.currentTimeMillis();
        }
    }
    
    public void reRender() {
        if (dbImage == null) {
            reCreateBuffer();
        }
        
        this.paint(g);
        render();
        
        this.getGraphics().drawImage(dbImage, 0, 0, this);
    }
    
    public void reCreateBuffer() {
        dbImage = this.createImage(this.getSize().width,this.getSize().height);
        g = dbImage.getGraphics();
    }
}
