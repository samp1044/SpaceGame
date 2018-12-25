/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.Cargo;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.Resources;
import utils.Logger;
import utils.Utils;
import utils.Vector2D;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Thruster implements Cargo {
    public final static int STANDART = 1;
    public final static int ALIEN = 2;
    
    private BufferedImage[] imgThruster;
    private BufferedImage[] imgFlame;
    private BufferedImage[] imgLighting;
    private int type;
    
    private BufferedImage icon;
    private String name;
    
    private int actualImgThruster;
    private int actualImgFlame;
    
    private long lastThrusterAnimationImage_Time;
    private long thrusterAnimationDelay;
    
    private long lastFlameAnimationImage_Time;
    private long flameAnimationDelay; 
    
    private int thrusterWidth;
    private int thrusterHeight;
    
    private int initialFlameWidth;
    private int initialFlameHeight;
    
    private int flameWidth;
    private int flameHeight;
    
    private boolean isActive;
    
    private double initialThrustStrength;
    private double thrustStrength;
    private double rotationDiffer;
    
    private Vector2D thrustDirection;
    
    private int shipMiddleX;
    private int shipMiddleY;
    private int xOffset;
    private int yOffset;
    
    private int posX;
    private int posY;
    private int middleX;
    private int middleY;
    
    private double rotationDegree;
    
    private boolean below;
    
    private Particle particle;
    
    Logger logger = new Logger(Thruster.class);
    
    public Thruster(int type, int thrusterWidth, int thrusterHeight,int flameWidth,int flameHeight, double thrustStrength, double shipMiddleX, double shipMiddleY, int xOffset,int yOffset,double rotationDegree, boolean below) {
        this.type = type;
        this.isActive = false;
        
        this.rotationDegree = rotationDegree;
        
        this.thrusterWidth = thrusterWidth;
        this.thrusterHeight = thrusterHeight;
        
        this.initialFlameWidth = flameWidth;
        this.initialFlameHeight = flameHeight;
        
        this.flameWidth = this.thrusterWidth;
        this.flameHeight = (int)Math.round(((double)this.initialFlameHeight / this.initialFlameWidth) * this.flameWidth);
        
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        
        this.middleX = this.shipMiddleX + this.xOffset;
        this.middleY = this.shipMiddleY + this.yOffset;
        
        this.posX = this.middleX - this.thrusterWidth / 2;
        this.posY = this.middleY - this.thrusterHeight / 2;
        
        this.actualImgThruster = 0;
        this.actualImgFlame = 0;
        
        this.lastThrusterAnimationImage_Time = 0;
        this.lastFlameAnimationImage_Time = 0;
        
        this.below = below;
        
        Vector2D thrustDirection = new Vector2D(this.middleX,this.middleY,shipMiddleX,shipMiddleY);
        thrustDirection = thrustDirection.getUnitVector().multiplyWithNumber(thrustStrength);
        
        
        if (rotationDegree == 180) {
            thrustDirection = thrustDirection.getTurnedLeftVektor().getTurnedLeftVektor();
        } else if (rotationDegree == 90) {
            thrustDirection = thrustDirection.getTurnedRightVektor();
        } else if (rotationDegree == -90 || rotationDegree == 270) {
            thrustDirection = thrustDirection.getTurnedLeftVektor();
        } else if (rotationDegree != 0) {
            thrustDirection = thrustDirection.getRotatedVector(-rotationDegree);
        }
        
        this.initialThrustStrength = thrustStrength;
        this.rotationDiffer = (thrustDirection.getX() * -1) / 100;
        this.thrustStrength = thrustStrength;
        
        if (thrustDirection.getY() < 0) {
            this.thrustStrength += (thrustDirection.getY() * -1);
        } else {
            this.thrustStrength += thrustDirection.getY();
        }
        
        this.thrustDirection = new Vector2D(0,1).getRotatedVector(this.rotationDegree).getUnitVector().multiplyWithNumber(this.thrustStrength);
        
        switch(type) {
            case STANDART:
                this.imgThruster = MainLoop.resources.getScaledBufferedImageField(Resources.THRUSTER_STANDART, thrusterWidth, thrusterHeight,Resources.SCALE_SMOOTH);
                this.imgFlame = MainLoop.resources.getScaledBufferedImageField(Resources.THRUSTER_FLAME_YELLOW, this.flameWidth, this.flameHeight,Resources.SCALE_SMOOTH);
                this.imgLighting = MainLoop.resources.getScaledBufferedImageField(Resources.LIGHTING_YELLOW,this.flameWidth + 10,this.flameHeight + 10,Resources.SCALE_SMOOTH);
                
                this.icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_THRUSTER_STANDART, 50, 50,Resources.SCALE_SMOOTH)[0];
                this.name = StringSelector.getSring(StringSelector.THRUSTER_NAME_STANDART);
                
                this.particle = new Particle("particleYellow.png",10,10,1500);
                
                this.thrusterAnimationDelay = 10;
                this.flameAnimationDelay = 10;
                break;
            case ALIEN:
                this.imgThruster = MainLoop.resources.getScaledBufferedImageField(Resources.THRUSTER_ALIEN, thrusterWidth, thrusterHeight,Resources.SCALE_SMOOTH);
                this.imgFlame = MainLoop.resources.getScaledBufferedImageField(Resources.THRUSTER_FLAME_GREEN, this.flameWidth, this.flameHeight,Resources.SCALE_SMOOTH);
                this.imgLighting = MainLoop.resources.getScaledBufferedImageField(Resources.LIGHTING_GREEN,this.flameWidth + 10,this.flameHeight + 10,Resources.SCALE_SMOOTH);
                
                this.icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_THRUSTER_STANDART, 50, 50,Resources.SCALE_SMOOTH)[0];
                this.name = StringSelector.getSring(StringSelector.THRUSTER_NAME_STANDART);
                
                this.particle = new Particle("particleGreen.png",10,10,1500);
                
                this.thrusterAnimationDelay = 10;
                this.flameAnimationDelay = 10;
                break;
        }
    }
    
    
    public void updatePositionData(double shipMiddleX,double shipMiddleY) {
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        
        this.middleX = this.shipMiddleX + this.xOffset;
        this.middleY = this.shipMiddleY + this.yOffset;
        
        this.posX = this.middleX - this.thrusterWidth / 2;
        this.posY = this.middleY - this.thrusterHeight / 2; 
    }
    
    public void draw(Graphics2D g2d) {
        draw(g2d,999); //999 weil winkel auch negativ sein können (der sollte es zwar nicht sein aber trotzdem)
    }
    
    public void draw(Graphics2D g2d,double shipRotation) {
        g2d.rotate(Math.toRadians(this.rotationDegree), (int)(Background.X + this.middleX), (int)(Background.Y + this.middleY));
        
        if ((this.actualImgThruster + 1) < imgThruster.length && (System.currentTimeMillis() - this.lastThrusterAnimationImage_Time) >= this.thrusterAnimationDelay) {
            this.actualImgThruster += 1;
            this.lastThrusterAnimationImage_Time = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - this.lastThrusterAnimationImage_Time) >= this.thrusterAnimationDelay) {
            this.actualImgThruster = 0;
            this.lastThrusterAnimationImage_Time = System.currentTimeMillis();
        }
        
        g2d.drawImage(this.imgThruster[this.actualImgThruster], (int)(Background.X + this.posX), (int)(Background.Y + this.posY), null);
        
        if (this.isActive) {
            if ((this.actualImgFlame + 1) < imgFlame.length && (System.currentTimeMillis() - this.lastFlameAnimationImage_Time) >= this.flameAnimationDelay) {
                this.actualImgFlame += 1;
                this.lastFlameAnimationImage_Time = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - this.lastFlameAnimationImage_Time) >= this.flameAnimationDelay) {
                this.actualImgFlame = 0;
                this.lastFlameAnimationImage_Time = System.currentTimeMillis();
            }
            
            /*if (shipRotation != 999 && Settings.particlesEnabled) {
                Particle newParticle = new Particle(this.particle); //Der gespeicherte Partikel wird in einen neuen kopiert damit nicht mit verschiedenen Referenzen auf das selbe Objekt gearbeitet wird
                Vector2D offset = new Vector2D(this.xOffset + (this.width/2 ),this.yOffset + (this.height / 2)); //Ein Vektor der von der schiffsmitte auf die Mitte der Triebwerksflamme zeigt
                
                offset = offset.getRotatedVector(shipRotation); //Der ausgerechnete offset Vektor der zur mitte der Triebwerksflamme zeigt wird noch um den gleichen grad wie das schiff gedreht
                
                newParticle.setPosX(this.shipRealMiddleX + (int)offset.getX() - (newParticle.getWidth()/2)); //am ende wird dem partikel die schiffsmittekoordinaten + dem vektor auf die triebwerksmitte - die hälfte der breite des partikels zugewiesen. - die hälfte der breite weil die x des partikels direkt zum bildzeichnen verwendet werden, also die x,y koordinaten die linke obere ecke des bildes sind
                newParticle.setPosY(this.shipRealMiddleY + (int)offset.getY() - (newParticle.getHeight()/2));//gleiches wie bei x
                
                newParticle.renewDuration();    //die lebenszeit des partikels wird wieder aufgefrischt und auf 0 zurückgesetzt
                MainLoop.particles.add(newParticle); //am ende wird der fertigerstellte partikel in das statische partikel array der main methode aufgenommen und ist von dort an sich selbst überlassen
            }*/
            
            g2d.drawImage(this.imgFlame[this.actualImgFlame], (int)(Background.X + this.posX), (int)(Background.Y + this.posY - this.flameHeight - 2),null);
            g2d.drawImage(this.imgLighting[0], (int)(Background.X + this.posX - 5), (int)(Background.Y + this.posY - this.flameHeight - 2 - 5),null);
        }
        
        g2d.rotate(-Math.toRadians(this.rotationDegree), (int)(Background.X + this.middleX), (int)(Background.Y + this.middleY));
        this.isActive = false;
    }
    
    public void setActive() {
        this.isActive = true;
    }
    
    public double getInitialThrustStrength() {
        return this.initialThrustStrength;
    }
    
    public double getThrustStrength() {
        return this.thrustStrength;
    }
    
    public double getRotationDiffer() {
        return this.rotationDiffer;
    }
    
    public Vector2D getThrustDirection() {
        return this.thrustDirection;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public int getY() {
        return this.posY;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getFlameWidth() {
        return this.flameWidth;
    }
    
    public int getFlameHeight() {
        return this.flameHeight;
    }
    
    public int getInitialFlameWidth() {
        return this.initialFlameWidth;
    }
    
    public int getInitialFlameHeight() {
        return this.initialFlameHeight;
    }
    
    public int getThrusterWidth() {
        return this.thrusterWidth;
    }
    
    public int getThrusterHeight() {
        return this.thrusterHeight;
    }
    
    public int getOffsetX() {
        return this.xOffset;
    }
    
    public int getOffsetY() {
        return this.yOffset;
    }
    
    public double getRotationDegree() {
        return this.rotationDegree;
    }
    
    public boolean isBelow() {
        return this.below;
    }

    @Override
    public BufferedImage getIcon() {
        return this.icon;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getTooltip() {
        int leftPartLength = 14;
        String returnValue = "";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.THRUSTER_TYPE), leftPartLength);
        
        switch (this.type) {
            case STANDART:
                returnValue += StringSelector.getSring(StringSelector.THRUSTER_STANDART) +"\n";
                break;
            case ALIEN:
                returnValue += StringSelector.getSring(StringSelector.THRUSTER_ALIEN) +"\n";
                break;
        }
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.THRUSTER_STRENGTH), leftPartLength);
        returnValue += (int)(this.initialThrustStrength / 1000) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPFRONTTHRUSTUNIT) + "\n";
        
        return returnValue;
    }
}
