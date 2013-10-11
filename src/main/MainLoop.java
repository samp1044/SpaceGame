/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import support.Background;
import window.Fenster;
import units.Thruster;
import units.Ship;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import support.Settings;
import units.Particle;
import utils.Logger;
import utils.ObjectList;

/**
 *
 * @author Sami
 */
public class MainLoop extends Canvas implements Runnable {
    Fenster fenster;
    
    Image dbImage;
    Graphics g;
    
    Background background;
    
    Ship ship;
    
    int FPSlimiter;
    long FPS;
    long FPSstart;
    long FPScurrent;
    
    public static boolean focus;
    public static boolean resized;
    
    public static ObjectList particles;
    
    public static int mausX;
    public static int mausY;
    
    Logger logger = new Logger(MainLoop.class);
    
    public MainLoop() {
        this.setIgnoreRepaint(true);
        this.setSize(800,600);
        
        dbImage = null;
        
        this.fenster = new Fenster(this);
        
        background = new Background();
        
        this.FPSlimiter = 10;
        
        this.FPS = 60;
        this.FPSstart = System.currentTimeMillis();
        this.FPScurrent = 0;
        
        focus = true;
        resized = false;
        
        particles = new ObjectList();
        
        mausX = 0;
        mausY = 0;
        
        ship = new Ship("dummy.jpg",50,50,(this.getWidth()/2) - 25,(this.getHeight()/2) - 25);
        
        Thruster forwardThruster = new Thruster(Thruster.STANDART,10,10,0.02f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),-6,26,180.0);
        forwardThruster.setParticleOutputRotationAngle(0);
        forwardThruster.setParticleOutputMultiplicator(40);
        
        ship.addForwardThruster(forwardThruster);
        ship.addBackwardThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),-17,-30,-50.0));
        ship.addBackwardThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),7,-30,50.0));
        ship.addFrontLeftThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),-22,-13,-90.0));
        ship.addFrontRightThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),12,-13,90.0));
        ship.addBackLeftThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),-22,13,-90.0));
        ship.addBackRightThruster(new Thruster(Thruster.STANDART,10,10,0.01f,ship.getMiddleX(),ship.getMiddleY(),ship.getRealMiddleX(),ship.getRealMiddleY(),12,13,90.0));
        
        this.run();
    }
    
    public void run() {
        while (true) {
            if(resized) {
                reCreateBuffer();
                resized = false;
            }
            
            if (focus) {
                if(Settings.FORWARD_Key_down) {
                    ship.throttleForward();
                }

                if (Settings.LEFT_Key_down) {
                    ship.throttleLeft();
                }

                if (Settings.BACKWARD_Key_down) {
                    ship.throttleBackward();
                }

                if (Settings.RIGHT_Key_down) {
                    ship.throttleRight();
                }
            } else {
                Settings.FORWARD_Key_down = false;
                Settings.LEFT_Key_down = false;
                Settings.BACKWARD_Key_down = false;
                Settings.RIGHT_Key_down = false;
            }
            
            ship.updateShip(mausX,mausY,this.getWidth(),this.getHeight());
            
            try {
                Thread.sleep(this.FPSlimiter);
            } catch (Exception e) {
                logger.error("Error bei Thread.sleep mit FPSlimiter: "+e);
            }
            
            fpsCount();
            reRender();
        }
    }
    
    public void render() {
        Graphics2D g2d = (Graphics2D)g.create();
        
        background.draw(g2d, this.getWidth(), this.getHeight());
        
        if (Settings.particlesEnabled) {
            for (int i = 0;i < particles.size();i++) {
                Particle particle = (Particle)particles.getElementAt(i);

                if (particle.draw(g2d)) {
                    particles.remove(i);
                }
            }
        }
        
        ship.draw(g2d);
        
        g.drawString("FPS: "+this.FPS, 30, 30);
    }
    
    private void fpsCount() {
        if (System.currentTimeMillis() - this.FPSstart <= 1000) {
            this.FPScurrent++;
        } else {
            this.FPS = this.FPScurrent;
            this.FPScurrent = 0;
            this.FPSstart = System.currentTimeMillis();
            
            if (this.FPS < 100 && this.FPSlimiter > 0) {
                this.FPSlimiter -= 1;
                logger.run("FPSlimiter von "+(this.FPSlimiter+1)+"ms auf "+this.FPSlimiter+"ms geändert");
            } else if (this.FPS > 100) {
                this.FPSlimiter += 1;
                logger.run("FPSlimiter von "+(this.FPSlimiter-1)+"ms auf "+this.FPSlimiter+"ms geändert");
            }
        }
    }
    
    public void reRender() {
        if (dbImage == null) {
            reCreateBuffer();
        }
        
        //Berechnen des aktuellen Abstandes zwischen Schiff und Fensterrand
        float distanceX_Old = this.ship.getMiddleX();
        float distanceY_Old = this.ship.getMiddleY();
        
        //Mittelkoordinaten des Schiffs werden auf die Hälfte der Fensterbreite bzw Fensterhöhe aktualisiert
        this.ship.setMiddleX(this.getWidth() / 2);
        this.ship.setMiddleY(this.getHeight() / 2);
        
        //Die Hintergrund X werden um den Abstand, um den das Schiff versetzt wurde (neuer Abstand - alter Abstand => veränderte Distanz) ebenfalls verschoben
        Background.X = Background.X + (this.ship.getMiddleX() - distanceX_Old);
        Background.Y = Background.Y + (this.ship.getMiddleY() - distanceY_Old);
        
        this.paint(g);
        render();
        
        this.getGraphics().drawImage(dbImage, 0, 0, this);
    }
    
    public void reCreateBuffer() {
        dbImage = this.createImage(this.getSize().width,this.getSize().height);
        g = dbImage.getGraphics();
    }
}
