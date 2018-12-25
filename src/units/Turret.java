/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.TurretObject;
import main.MainLoop;
import support.Background;
import support.Resources;
import utils.Vector2D;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Turret extends WeaponBase implements TurretObject {
    private boolean twinShoot;
    private int twinShootOffset;
    private int initialWidth;
    
    private int targetX;
    private int targetY;
    
    public Turret(int turretType, int width, int height, int xOffset, int yOffset, int shipMiddleX, int shipMiddleY, double rotationDegree, long coolDown,int shootType, double energyCost,int side, Bullet bullet) {
        super(turretType, width, height, xOffset, yOffset, shipMiddleX, shipMiddleY, rotationDegree, coolDown, shootType, energyCost,side, bullet);
        
        switch (turretType) {
            case LASER:
                this.img = MainLoop.resources.getScaledBufferedImageField(Resources.TURRET_DOUBLELASER1, this.width, this.height,Resources.SCALE_SMOOTH);
                this.shootAnim = MainLoop.resources.getScaledBufferedImageField(Resources.TURRET_DOUBLELASER1_SHOOTANI, this.width, this.height,Resources.SCALE_SMOOTH);
                this.icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_TURRETDOUBLELASER, 50, 50,Resources.SCALE_SMOOTH)[0];
                this.animationDelay = 50;
                this.name = StringSelector.getSring(StringSelector.TURRET_TURRETDOUBLELASER);
                
                this.twinShootOffset = 120;
                this.initialWidth = 530;
                this.twinShoot = true;
                break;
        }
        
        this.twinShootOffset = (int)Math.round(((double)this.twinShootOffset / this.initialWidth) * this.width);
        this.below = false;
    }
    
    @Override
    public void update(double shipMiddleX,double shipMiddleY, int targetX, int targetY,double shipRotation) {
        super.update(shipMiddleX, shipMiddleY, targetX, targetY, shipRotation);
        
        this.targetX = targetX;
        this.targetY = targetY;
        
        this.shipRotation = shipRotation;
        calculateRotationDegree(shipRotation);
    }
    
    private void calculateRotationDegree(double shipRotation) {
        boolean isLeft = false;
        Vector2D fixedVector = new Vector2D(0,-1);
        Vector2D direction = new Vector2D(middleX,middleY,targetX,targetY);
        
        if (middleX + direction.getX() < middleX) {
            isLeft = true;
        }
        
        this.rotationDegree = (double)fixedVector.getAngle(direction);
        
        if(isLeft) {
            this.rotationDegree = 360 - this.rotationDegree;
        }
        
        this.rotationDegree -= shipRotation;
    }
    
    @Override
    protected void performShoot(Vector2D direction) {
        //Bestimmen der Rotation da Vector2D.getFullAngle(Vector2D) immer noch nicht funktioniert
        Vector2D helpingVector = new Vector2D(0,-1);
        direction = helpingVector.getRotatedVector(this.rotationDegree + this.shipRotation);
        
        Vector2D offset = new Vector2D(this.xOffset,this.yOffset); //Ein Vektor der von der schiffsmitte auf die Mitte der Waffe zeigt

        offset = offset.getRotatedVector(this.shipRotation); //Der ausgerechnete offset Vektor der zur mitte der Waffe zeigt wird noch um den gleichen grad wie das schiff gedreht
        helpingVector = direction.getUnitVector().multiplyWithNumber(height); //der einheitsvektor des direction vektors wird um die höhe (normaerweise die länge des kanonerohrs) multipliziert damit der schuss nicht innerhalb der waffe angsetzt wird
        //und das ergebis wird in den helpingVector kopiert damit das original direction objekt nicht verändert wird
        
        Vector2D cannonOffset = null;
        Vector2D customOffset = new Vector2D(0,0);
        
        Bullet newBullet = new Bullet(this.bullet); //Das gespeicherte Bullet wird in ein neues kopiert damit nicht mit verschiedenen referenzen auf das selbe objekt gearbeitet wird
        
        if (this.twinShoot) {
            cannonOffset = direction.getTurnedLeftVektor().getUnitVector().multiplyWithNumber(this.twinShootOffset / 2);
        } else {
            cannonOffset = new Vector2D(0,0);
        }
        
        if (this.shootType == RAY) {
            customOffset = direction.getUnitVector().multiplyWithNumber(this.bullet.getHeight() / 2);
        }
        
        newBullet.setDirection(direction); //Die Richtung in die das bullet davon driften soll
        newBullet.setX(this.shipMiddleX + offset.getX() + cannonOffset.getX() + customOffset.getX() - (newBullet.getWidth() / 2) + helpingVector.getX()); //das bullet wird erstellt mit schiffsmittelX koordinaten + dem vektor zur mitte der waffe - der hälfte der weite des schusses. das platziert den schuss genau auf der waffe drauf (deckungsgleich)
        newBullet.setY(this.shipMiddleY + offset.getY() + cannonOffset.getY() + customOffset.getY() - (newBullet.getHeight() / 2) + helpingVector.getY());//um den schuss vor der waffe entstehen zu lassen wird der aus dem direction (die richtung in die die waffe aktuell zeigt) gebildete direction vektor mit der länge der waffe dazugerechnet.
        newBullet.renewLifeTime();
        
        MainLoop.bullets.add(newBullet); //schließlich wird das erstellte bullet in das allgemeine bulletArray aufgenommen und dort sich selbst überlassen
        
        if (this.twinShoot) {
            cannonOffset = cannonOffset.getRotatedVector(180);
            newBullet = new Bullet(this.bullet); //Das gespeicherte Bullet wird in ein neues kopiert damit nicht mit verschiedenen referenzen auf das selbe objekt gearbeitet wird

            newBullet.setDirection(direction); //Die Richtung in die das bullet davon driften soll
            newBullet.setX(this.shipMiddleX + offset.getX() + cannonOffset.getX() + customOffset.getX() - (newBullet.getWidth() / 2) + helpingVector.getX()); //das bullet wird erstellt mit schiffsmittelX koordinaten + dem vektor zur mitte der waffe - der hälfte der weite des schusses. das platziert den schuss genau auf der waffe drauf (deckungsgleich)
            newBullet.setY(this.shipMiddleY + offset.getY() + cannonOffset.getY() + customOffset.getY() - (newBullet.getHeight() / 2) + helpingVector.getY());//um den schuss vor der waffe entstehen zu lassen wird der aus dem direction (die richtung in die die waffe aktuell zeigt) gebildete direction vektor mit der länge der waffe dazugerechnet.
            newBullet.renewLifeTime();

            MainLoop.bullets.add(newBullet); //schließlich wird das erstellte bullet in das allgemeine bulletArray aufgenommen und dort sich selbst überlassen
        }
    }
}
