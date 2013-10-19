/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import main.MainLoop;
import support.Background;
import support.Resources;
import utils.Logger;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Weapon {
    public final static int LASER = 1;
    
    private Image[] img;
    private int actual;
    
    private int width;
    private int height;
    
    private int posX;
    private int posY;
    
    private int shipMiddleX;
    private int shipMiddleY;
    private int xOffset;
    private int yOffset;
    
    private int muzzleOffsetX;
    private int muzzleOffsetY;
    
    private double rotationDegree;
    
    private boolean muzzleFlash;
    
    private boolean shoot;
    private long coolDown;
    private long lastShot;
    Bullet bullet;
    
    Logger logger = new Logger(Weapon.class);
    
    public Weapon(int type, int width, int height, int xOffset, int yOffset, int shipMiddleX, int shipMiddleY, double rotationDegree, long coolDown) {
        this.width = width;
        this.height = height;
        
        this.shipMiddleX = shipMiddleX;
        this.shipMiddleY = shipMiddleY;
        
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        
        this.posX = this.shipMiddleX + this.xOffset;
        this.posY = this.shipMiddleY + this.yOffset;
        
        this.muzzleOffsetX = this.xOffset + (this.width / 2);
        this.muzzleOffsetY = this.yOffset;
        
        this.rotationDegree = rotationDegree;
        this.actual = 0;
        
        this.shoot = false;
        this.coolDown = coolDown;
        this.lastShot = 0;
        
        img = new Image[0];
        
        switch (type) {
            case LASER:
                img = MainLoop.resources.getScaledImageField(Resources.WEAPON_LASER1, this.width, this.height);
                break;
        }
    }
    
    public void updatePositionData(float shipMiddleX,float shipMiddleY) {
        this.shipMiddleX = (int)shipMiddleX;
        this.shipMiddleY = (int)shipMiddleY;
        
        this.posX = this.shipMiddleX + this.xOffset;
        this.posY = this.shipMiddleY + this.yOffset;
        
        this.muzzleOffsetX = this.xOffset + (this.width / 2);
        this.muzzleOffsetY = this.yOffset;
    }
    
    public void draw(Graphics2D g2d, Vector2D direction) {
        g2d.rotate(Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
        
        if (this.shoot) {
            performShoot(direction); //direction wird in der Methode verändert!
            this.shoot = false;
        }
        
        if ((this.actual + 1) < img.length) {
            this.actual += 1;
        } else {
            this.actual = 0;
        }

        g2d.drawImage(this.img[this.actual], this.posX, this.posY,null);
        g2d.rotate(-Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
    }
    
    public void shoot() {
        long currentTime = System.currentTimeMillis();
        
        if (this.lastShot + this.coolDown < currentTime) {
            this.shoot = true;
            this.lastShot = currentTime;
        }
    }
    
    private void performShoot(Vector2D direction) {
        //Bestimmen der Rotation da Vector2D.getFullAngle(Vector2D) immer noch nicht funktioniert
        double rotation;
        boolean isLeft = false;
        Vector2D helpingVector = new Vector2D(0,-1);
        
        int middleX = 0;
        
        if (middleX + direction.getX() < middleX) {
            isLeft = true;
        }
        
        rotation = (float)helpingVector.getAngle(direction);
        
        if(isLeft) {
            rotation = 360 - rotation;
        }
       
        Vector2D offset = new Vector2D(this.xOffset + (this.width/2 ),this.yOffset + (this.height / 2)); //Ein Vektor der von der schiffsmitte auf die Mitte der Waffe zeigt

        offset = offset.getRotatedVector(rotation); //Der ausgerechnete offset Vektor der zur mitte der Waffe zeigt wird noch um den gleichen grad wie das schiff gedreht
        helpingVector = direction.getUnitVector().multiplyWithNumber(height); //der einheitsvektor des direction vektors wird um die höhe (normaerweise die länge des kanonerohrs) multipliziert damit der schuss nicht innerhalb der waffe angsetzt wird
        //und das ergebis wird in den helpingVector kopiert damit das original direction objekt nicht verändert wird
        
        
        Bullet newBullet = new Bullet(this.bullet); //Das gespeicherte Bullet wird in ein neues kopiert damit nicht mit verschiedenen referenzen auf das selbe objekt gearbeitet wird
        
        newBullet.setX((-Background.X) + this.shipMiddleX + offset.getX() - (newBullet.getWidth() / 2) + helpingVector.getX()); //das bullet wird erstellt mit schiffsmittelX koordinaten + dem vektor zur mitte der waffe - der hälfte der weite des schusses. das platziert den schuss genau auf der waffe drauf (deckungsgleich)
        newBullet.setY((-Background.Y) + this.shipMiddleY + offset.getY() - (newBullet.getHeight() / 2) + helpingVector.getY());//um den schuss vor der waffe entstehen zu lassen wird der aus dem direction (die richtung in die die waffe aktuell zeigt) gebildete direction vektor mit der länge der waffe dazugerechnet.
        newBullet.setDirection(direction); //Die Richtung in die das bullet davon driften soll
        
        MainLoop.bullets.add(newBullet); //schließlich wird das erstellte bullet in das allgemeine bulletArray aufgenommen und dort sich selbst überlassen
    }
    
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
