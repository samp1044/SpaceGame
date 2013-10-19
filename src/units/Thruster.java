/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import main.MainLoop;
import support.Resources;
import support.Settings;
import utils.Logger;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Thruster {
    public final static int STANDART = 1;
            
    private Image[] img;
    private int type;
    
    private int actualImg;
    
    private int width;
    private int height;
    
    private boolean isActive;
    
    private float thrustStrength;
    
    private int shipRealMiddleX;
    private int shipRealMiddleY;
    
    private int shipMiddleX;
    private int shipMiddleY;
    private int xOffset;
    private int yOffset;
    
    private int posX;
    private int posY;
    
    private double rotationDegree;
    
    private Particle particle;
    
    Logger logger = new Logger(Thruster.class);
    
    public Thruster(int type,int width,int height, float thrustStrength,float shipMiddleX,float shipMiddleY,float shipRealMiddleX, float shipRealMiddleY, int xOffset,int yOffset,double rotationDegree) {
        this.type = type;
        this.thrustStrength = thrustStrength;
        this.isActive = false;
        
        this.shipRealMiddleX = (int)shipRealMiddleX;
        this.shipRealMiddleY = (int)shipRealMiddleY;
        
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        
        this.posX = this.shipMiddleX + this.xOffset;
        this.posY = this.shipMiddleY + this.yOffset;
        
        this.rotationDegree = rotationDegree;
        
        this.width = width;
        this.height = height;
        
        this.actualImg = 0;
        
        switch(type) {
            case STANDART:
                img = MainLoop.resources.getScaledImageField(Resources.THRUSTER_STANDART, width, height);
                this.particle = new Particle("particleDummy.png",10,10,1500);
                break;
        }
    }
    
    public void updatePositionData(float shipMiddleX,float shipMiddleY,float shipRealMiddleX,float shipRealMiddleY) {
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        
        this.shipRealMiddleX = (int)shipRealMiddleX;
        this.shipRealMiddleY = (int)shipRealMiddleY;
        
        this.posX = this.shipMiddleX + this.xOffset;
        this.posY = this.shipMiddleY + this.yOffset;
    }
    
    public void draw(Graphics2D g2d) {
        draw(g2d,999); //999 weil winkel auch negativ sein können (der sollte es zwar nicht sein aber trotzdem)
    }
    
    public void draw(Graphics2D g2d,float shipRotation) {
        if (this.isActive) {
            g2d.rotate(Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
            
            if ((this.actualImg + 1) < img.length) {
                this.actualImg += 1;
            } else {
                this.actualImg = 0;
            }
            
            if (shipRotation != 999 && Settings.particlesEnabled) {
                Particle newParticle = new Particle(this.particle); //Der gespeicherte Partikel wird in einen neuen kopiert damit nicht mit verschiedenen Referenzen auf das selbe Objekt gearbeitet wird
                Vector2D offset = new Vector2D(this.xOffset + (this.width/2 ),this.yOffset + (this.height / 2)); //Ein Vektor der von der schiffsmitte auf die Mitte der Triebwerksflamme zeigt
                
                offset = offset.getRotatedVector(shipRotation); //Der ausgerechnete offset Vektor der zur mitte der Triebwerksflamme zeigt wird noch um den gleichen grad wie das schiff gedreht
                
                newParticle.setPosX(this.shipRealMiddleX + (int)offset.getX() - (newParticle.getWidth()/2)); //am ende wird dem partikel die schiffsmittekoordinaten + dem vektor auf die triebwerksmitte - die hälfte der breite des partikels zugewiesen. - die hälfte der breite weil die x des partikels direkt zum bildzeichnen verwendet werden, also die x,y koordinaten die linke obere ecke des bildes sind
                newParticle.setPosY(this.shipRealMiddleY + (int)offset.getY() - (newParticle.getHeight()/2));//gleiches wie bei x
                
                newParticle.renewDuration();    //die lebenszeit des partikels wird wieder aufgefrischt und auf 0 zurückgesetzt
                MainLoop.particles.add(newParticle); //am ende wird der fertigerstellte partikel in das statische partikel array der main methode aufgenommen und ist von dort an sich selbst überlassen
            }
            
            g2d.drawImage(this.img[this.actualImg], this.posX, this.posY,null);
            g2d.rotate(-Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
        }
        
        this.isActive = false;
    }
    
    public void setActive() {
        this.isActive = true;
    }
    
    public float getThrustStrength() {
        return this.thrustStrength;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public int getY() {
        return this.posY;
    }
}
