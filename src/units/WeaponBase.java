/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.Cargo;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import utils.Logger;
import utils.Utils;
import utils.Vector2D;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class WeaponBase implements Cargo {
    public final static int REPETIER = 1;
    public final static int RAY = 2;
    
    public final static int PLACEHOLDER = 3;
    public final static int LASER = 4;
    public final static int PLASMA_GUN = 5;
    public final static int PLASMA_LANCE = 6;
    
    protected int shootType;
    protected int weaponType;
    
    protected boolean below;
    protected int side;
    
    protected String name;
    
    protected BufferedImage[] img;
    protected BufferedImage[] shootAnim;
    protected BufferedImage icon;
    protected int actual;
    
    protected long lastAnimationImage_Time;
    protected long animationDelay;
    
    protected int width;
    protected int height;
    
    protected int posX;
    protected int posY;
    protected int middleX;
    protected int middleY;
    
    protected int shipMiddleX;
    protected int shipMiddleY;
    protected int xOffset;
    protected int yOffset;
    
    protected double rotationDegree;
    protected double shipRotation;
    
    protected double energyCost;
    
    protected boolean shoot;
    protected boolean shotPerformed;
    protected boolean inShooting;
    protected long coolDown;
    protected long lastShot;
    Bullet bullet;
    
    Logger logger = new Logger(WeaponBase.class);
    
    public WeaponBase(int weaponType, int width, int height, int xOffset, int yOffset, int shipMiddleX, int shipMiddleY, double rotationDegree, long coolDown, int shootType, double energyCost, int side, Bullet bullet) {
        this.shootType = shootType;
        this.weaponType = weaponType;
        
        this.side = side;
        
        this.width = width;
        this.height = height;
        
        this.shipMiddleX = shipMiddleX;
        this.shipMiddleY = shipMiddleY;
        
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        
        this.middleX = this.shipMiddleX + this.xOffset;
        this.middleY = this.shipMiddleY + this.yOffset;
        
        this.posX = this.middleX - (this.width / 2);
        this.posY = this.middleY - (this.height / 2);
        
        this.rotationDegree = rotationDegree;
        this.actual = 0;
        
        this.lastAnimationImage_Time = 0;
        
        this.shoot = false;
        this.shotPerformed = false;
        this.inShooting = false;
        this.coolDown = coolDown;
        this.lastShot = 0;
        
        this.energyCost = energyCost;
        this.bullet = bullet;
    }
    
    public void update(double shipMiddleX,double shipMiddleY, int targetX, int targetY,double shipRotation) {
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        
        this.middleX = this.shipMiddleX + this.xOffset;
        this.middleY = this.shipMiddleY + this.yOffset;
        
        this.posX = this.middleX - (this.width / 2);
        this.posY = this.middleY - (this.height / 2);
        
        Vector2D positionVector = new Vector2D(shipMiddleX,shipMiddleY,middleX,middleY);
        positionVector = positionVector.getRotatedVector(shipRotation);
        
        this.middleX = (int)(this.shipMiddleX + positionVector.getX());
        this.middleY = (int)(this.shipMiddleY + positionVector.getY());
    }
    
    public void draw(Graphics2D g2d, Vector2D direction) {
        g2d.rotate(Math.toRadians(this.rotationDegree), (int)(Background.X + this.posX + this.width / 2), (int)(Background.Y + this.posY + this.height / 2));
        
        if (this.shoot) {
            this.inShooting = true;
            this.actual = 0;
            this.shoot = false;
        }
        
        if (this.inShooting) {
            if (this.shotPerformed == false && (this.shootAnim.length - 1) / 2 == this.actual) {
                performShoot(direction);
                this.shotPerformed = true;
            }
        }
         
        if (this.inShooting) {
            if ((this.actual + 1) < this.shootAnim.length && (System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
                this.actual += 1;
                this.lastAnimationImage_Time = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
                this.actual = 0;
                this.inShooting = false;
                this.shotPerformed = false;
                this.lastAnimationImage_Time = System.currentTimeMillis();
            }
        } else {
            if ((this.actual + 1) < img.length && (System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
                this.actual += 1;
                this.lastAnimationImage_Time = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
                this.actual = 0;
                this.lastAnimationImage_Time = System.currentTimeMillis();
            }
        }
        
        if (this.inShooting) {
            g2d.drawImage(this.shootAnim[this.actual], (int)Background.X + this.posX, (int)Background.Y + this.posY,null);
        } else {
            g2d.drawImage(this.img[this.actual], (int)Background.X + this.posX, (int)Background.Y + this.posY,null);
        }
        
        g2d.rotate(-Math.toRadians(this.rotationDegree), (int)(Background.X + this.posX + this.width / 2), (int)(Background.Y + this.posY + this.height / 2));
    }
    
    public double shoot(double energy) {
        long currentTime = System.currentTimeMillis();
        double energyLoss = 0;
                
        if (energy - energyCost > 0 && this.lastShot + this.coolDown < currentTime && this.inShooting == false) {
            this.shoot = true;
            this.lastShot = currentTime;
            energyLoss = energyCost;
        }
        
        return energyLoss;
    }
    
    protected void performShoot(Vector2D direction) {
        //Bestimmen der Rotation da Vector2D.getFullAngle(Vector2D) immer noch nicht funktioniert
        double rotation;
        boolean isLeft = false;
        Vector2D helpingVector = new Vector2D(0,-1);
        
        int middleX = 0;
        
        if (middleX + direction.getX() < middleX) {
            isLeft = true;
        }
        
        rotation = (double)helpingVector.getAngle(direction);
        
        if(isLeft) {
            rotation = 360 - rotation;
        }
       
        Vector2D offset = new Vector2D(this.xOffset,this.yOffset); //Ein Vektor der von der schiffsmitte auf die Mitte der Waffe zeigt

        offset = offset.getRotatedVector(rotation); //Der ausgerechnete offset Vektor der zur mitte der Waffe zeigt wird noch um den gleichen grad wie das schiff gedreht
        helpingVector = direction.getUnitVector().multiplyWithNumber(height); //der einheitsvektor des direction vektors wird um die höhe (normaerweise die länge des kanonerohrs) multipliziert damit der schuss nicht innerhalb der waffe angsetzt wird
        //und das ergebis wird in den helpingVector kopiert damit das original direction objekt nicht verändert wird
        Vector2D customOffset = new Vector2D(0,0);
        
        if (this.shootType == RAY) {
            customOffset = direction.getUnitVector().multiplyWithNumber(this.bullet.getHeight() / 2);
        }
        
        Bullet newBullet = new Bullet(this.bullet); //Das gespeicherte Bullet wird in ein neues kopiert damit nicht mit verschiedenen referenzen auf das selbe objekt gearbeitet wird
        
        newBullet.setDirection(direction); //Die Richtung in die das bullet davon driften soll
        newBullet.setX(this.shipMiddleX + offset.getX() + customOffset.getX() - (newBullet.getWidth() / 2) + helpingVector.getX()); //das bullet wird erstellt mit schiffsmittelX koordinaten + dem vektor zur mitte der waffe - der hälfte der weite des schusses. das platziert den schuss genau auf der waffe drauf (deckungsgleich)
        newBullet.setY(this.shipMiddleY + offset.getY() + customOffset.getY() - (newBullet.getHeight() / 2) + helpingVector.getY());//um den schuss vor der waffe entstehen zu lassen wird der aus dem direction (die richtung in die die waffe aktuell zeigt) gebildete direction vektor mit der länge der waffe dazugerechnet.
        newBullet.renewLifeTime();
        
        MainLoop.bullets.add(newBullet); //schließlich wird das erstellte bullet in das allgemeine bulletArray aufgenommen und dort sich selbst überlassen
    }
    
    public boolean equals(Object object) {
        boolean isEqual = false;
        WeaponBase weapon = null;
        
        if (object == null || !(object instanceof WeaponBase)) {
            return isEqual;
        }
        
        weapon = (WeaponBase)object;
        
        if (this.name.equals(weapon.name) && this.bullet.equals(weapon.bullet) && coolDown == weapon.coolDown && energyCost == weapon.energyCost && shootType == weapon.shootType && this.weaponType == weapon.weaponType) {
            isEqual = true;
        }
        
        return isEqual;
    }
    
    public void setBulletFraction(int fraction) {
        this.bullet.setFraction(fraction);
    }
    
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
    
    public int getWeaponType() {
        return this.weaponType;
    }

    public BufferedImage getIcon() {
        return this.icon;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getActual() {
        return actual;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getShipMiddleX() {
        return shipMiddleX;
    }

    public int getShipMiddleY() {
        return shipMiddleY;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public double getRotationDegree() {
        return rotationDegree;
    }
    
    public int getShootType() {
        return shootType;
    }
    
    public double getEnergyCost() {
        return energyCost;
    }

    public long getCoolDown() {
        return coolDown;
    }

    public Bullet getBullet() {
        return bullet;
    }
    
    public int getSide() {
        return this.side;
    }
    
    public boolean isBelow() {
        return this.below;
    }
    
    @Override
    public String getTooltip() {
        int leftPartLength = 22;
        String returnValue = "";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.WEAPON_SHOOTTYPE), leftPartLength);
        
        switch (this.shootType) {
            case REPETIER:
                returnValue += StringSelector.getSring(StringSelector.WEAPON_REPETIER) + "\n";
                break;
            case RAY:
                returnValue += StringSelector.getSring(StringSelector.WEAPON_RAY) + "\n";
        }
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.WEAPON_DAMAGE), leftPartLength);
        returnValue += (this.bullet.getDamage() * 100) + "\n";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.WEAPON_COOLDOWN), leftPartLength);
        returnValue += (this.coolDown) + " " + StringSelector.getSring(StringSelector.RELOAD_TIME_UNIT) + "\n";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.WEAPON_ENERGYCOST), leftPartLength);
        returnValue += (this.energyCost) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBATTERYUNIT) + "\n";
        
        return returnValue;
    }
}
