/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import main.MainLoop;
import support.Background;
import support.FixedCollisionBox;
import support.Resources;
import support.Settings;
import utils.Vector2D;

public class PlayerShip extends Ship {
    private float realMiddleX;
    private float realMiddleY;
    
    public PlayerShip(int typ,int width,int height,int x,int y,float health, Resources resources) {
        super(typ, width, height, x, y, health, resources);
        
        this.realMiddleX = this.middleX;
        this.realMiddleY = this.middleY;
    }
    
    public void updateShip(int mausX,int mausY,int canvasWidth,int canvasHeight) {
        this.realMiddleX = (-Background.X) + (canvasWidth / 2); //Die Realen X Koordinaten (mit dem Background abgestimmt und POSITIV, werden aktualisiert:
        this.realMiddleY = (-Background.Y) + (canvasHeight / 2);//Background koordinate + hälfte der Fensterbreite bzw Fensterhöhe
        
        Background.move(this.force); //Statt das schiff mit force zu bewegen wird der Background mit Force bewegt
        
        this.posX = this.middleX - (this.width / 2.0F);
        this.posY = this.middleY - (this.height / 2.0F);
        
        for (int i = 0;i < this.thrusterForward.length; i++) {
            this.thrusterForward[i].updatePositionData(this.middleX, this.middleY,this.realMiddleX,this.realMiddleY);
        }
        
        for (int i = 0;i < this.thrusterBackward.length; i++) {
            this.thrusterBackward[i].updatePositionData(this.middleX, this.middleY,this.realMiddleX,this.realMiddleY);
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length; i++) {
            this.thrusterFrontLeft[i].updatePositionData(this.middleX, this.middleY,this.realMiddleX,this.realMiddleY);
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length; i++) {
            this.thrusterBackLeft[i].updatePositionData(this.middleX, this.middleY,this.realMiddleX,this.realMiddleY);
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length; i++) {
            this.thrusterFrontRight[i].updatePositionData(this.middleX, this.middleY,this.realMiddleX,this.realMiddleY);
        }
        
        for (int i = 0;i < this.thrusterBackRight.length; i++) {
            this.thrusterBackRight[i].updatePositionData(this.middleX, this.middleY,this.realMiddleX,this.realMiddleY);
        }
        
        for (int i = 0;i < this.weapons.length;i++) {
            this.weapons[i].updatePositionData(this.middleX, this.middleY);
        }
        
        manageRotation(mausX, mausY);
        
        this.boxCollider.updateBox(this.realMiddleX, this.realMiddleY);
        this.boxCollider.setRotation(this.rotationDegree);
        
        for (int i = 0;i < MainLoop.bullets.size();i++) {
            Bullet bullet = (Bullet)MainLoop.bullets.getElementAt(i);
            
            if (bullet.isHit(boxCollider,this.realMiddleX - this.width / 2,this.realMiddleY - this.height / 2,this.width,this.height,Bullet.NEUTRAL)) {
                //Ship is hit
                MainLoop.bullets.remove(i);
            }
            
            int a = 0;
        }
        
        if (Settings.SHOOT_Key_down) {
            for (int i = 0;i < this.weapons.length;i++) {
                this.weapons[i].shoot();
            }
        }
    }
    
    private void manageRotation(int mausX,int mausY) {
        boolean leftOf = this.direction.isLeftOf(new Vector2D(mausX-this.middleX,mausY-this.middleY));
        Vector2D mouse = new Vector2D(mausX-this.middleX,mausY-this.middleY);
        
        //(float) zum runden (statt 179.9999...° => 180.0°)
        if((float)(this.direction.getAngle(mouse)) > 1) {
            if (leftOf) {
                if((float)(this.direction.getAngle(mouse)) >= this.rotationLeftThrust) {
                    this.direction = this.direction.getRotatedVector(this.rotationLeftThrust);
                } else {
                    this.direction = this.direction.getRotatedVector(this.direction.getAngle(mouse));
                }
            } else {
                if((float)(this.direction.getAngle(mouse)) >= this.rotationRightThrust) {
                    this.direction = this.direction.getRotatedVector(this.rotationRightThrust * -1.0);
                } else {
                    this.direction = this.direction.getRotatedVector(this.direction.getAngle(mouse) * -1.0);
                }
            }
        } else {
            this.direction = mouse.getUnitVector();
        }
        
        calculateRotationDegree();
    }
    
    public float getRealMiddleX() {
        return this.realMiddleX;
    }
    
    public float getRealMiddleY() {
        return this.realMiddleY;
    }
    
    public FixedCollisionBox getBox() {
        return this.boxCollider;
    }
}