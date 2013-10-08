/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import main.MainLoop;
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
    
    private int shipMiddleX;
    private int shipMiddleY;
    private int xOffset;
    private int yOffset;
    
    private int posX;
    private int posY;
    
    private double rotationDegree;
    
    private Particle particle;
    private double ParticleOutputMultiplicator;
    private double ParticleOutputRotationAngle;
    
    public Thruster(int type,int width,int height, float thrustStrength,float shipMiddleX,float shipMiddleY,int xOffset,int yOffset,double rotationDegree) {
        this.type = type;
        this.thrustStrength = thrustStrength;
        this.isActive = false;
        
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
                img = new Image[4];
                img[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/ships/thrustDummy.jpg"));
                this.img[0] = img[0].getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
                
                img[1] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/ships/thrustDummy1.jpg"));
                this.img[1] = img[1].getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
                
                img[2] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/ships/thrustDummy2.jpg"));
                this.img[2] = img[2].getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
                
                img[3] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/ships/thrustDummy1.jpg"));
                this.img[3] = img[3].getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
                
                this.particle = new Particle("dummyParticle.jpg",10,10,1500);
                
                break;
        }
        
        this.ParticleOutputRotationAngle = 0;
        this.ParticleOutputMultiplicator = 1;
    }
    
    public void updatePositionData(float shipMiddleX,float shipMiddleY) {
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        
        this.posX = this.shipMiddleX + this.xOffset;
        this.posY = this.shipMiddleY + this.yOffset;
    }
    
    public void draw(Graphics2D g2d) {
        draw(g2d,null);
    }
    
    public void draw(Graphics2D g2d,Vector2D direction) {
        if (this.isActive) {
            g2d.rotate(Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
            
            if ((this.actualImg + 1) < img.length) {
                this.actualImg += 1;
            } else {
                this.actualImg = 0;
            }
            
            if (direction != null && MainLoop.particlesEnabled) {
                direction = direction.getUnitVector().multiplyWithNumber(-1);
                direction = direction.getRotatedVector(this.ParticleOutputRotationAngle).getUnitVector();
                direction = direction.multiplyWithNumber(this.ParticleOutputMultiplicator);
                
                Particle particle = new Particle(this.particle);
                particle.setPosX((int)(this.shipMiddleX + direction.getX()));
                particle.setPosY((int)(this.shipMiddleY + direction.getY()));
                particle.renewDuration();
                MainLoop.particles.add(particle);
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
    
    public void setParticleOutputMultiplicator(double multiplicator) {
        this.ParticleOutputMultiplicator = multiplicator;
    }
    
    public void setParticleOutputRotationAngle(double angle) {
        this.ParticleOutputRotationAngle = angle;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public int getY() {
        return this.posY;
    }
}
