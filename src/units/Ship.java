/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import support.FixedCollisionBox;
import support.Resources;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Ship {
    public static final int DUMMY = 1;
    
    protected Image[] img;
    protected int actual;
    protected float rotationDegree;
    protected int width;
    protected int height;
    
    protected float middleX;
    protected float middleY;
    protected float posX;
    protected float posY;
    
    protected Vector2D direction;
    protected Vector2D force;
    
    protected Thruster[] thrusterForward;
    protected Thruster[] thrusterBackward;
    protected Thruster[] thrusterFrontLeft;
    protected Thruster[] thrusterBackLeft;
    protected Thruster[] thrusterFrontRight;
    protected Thruster[] thrusterBackRight;
    
    float rotationLeftThrust;
    float rotationRightThrust ;
    
    protected Weapon[] weapons;
    
    protected FixedCollisionBox boxCollider;
    protected float health;
    protected float armor;
    
    public Ship(int typ,int width,int height,int x,int y,float health,Resources resources) {
        switch(typ) {
            case DUMMY:
                img = resources.getScaledImageField(Resources.SHIP_DUMMY, width, height);
                break;
        }
        
        this.actual = 0;
        this.rotationDegree = 0.0f;
        
        this.width = width;
        this.height = height;
        
        this.posX = x;
        this.posY = y;
        
        this.middleX = this.posX + (width / 2.0F);
        this.middleY = this.posY + (height / 2.0F);
        
        this.direction = new Vector2D(0,-1);
        this.force = new Vector2D(0,0);
        
        this.thrusterForward = new Thruster[0];
        this.thrusterBackward = new Thruster[0];
        
        this.thrusterFrontLeft = new Thruster[0];
        this.thrusterBackLeft = new Thruster[0];
        this.thrusterFrontRight = new Thruster[0];
        this.thrusterBackRight = new Thruster[0];
        
        this.weapons = new Weapon[0];
        
        this.health = health;
        this.boxCollider = new FixedCollisionBox(this.middleX,this.middleY,this.width,this.height);
    }
    
    public void throttleForward() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            thrustStrength += this.thrusterForward[i].getThrustStrength();
            this.thrusterForward[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void throttleBackward() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            thrustStrength += this.thrusterBackward[i].getThrustStrength();
            this.thrusterBackward[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.multiplyWithNumber(-1).getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void throttleRight() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            thrustStrength += this.thrusterFrontLeft[i].getThrustStrength();
            this.thrusterFrontLeft[i].setActive();
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            thrustStrength += this.thrusterBackLeft[i].getThrustStrength();
            this.thrusterBackLeft[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.getTurnedLeftVektor().getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void throttleLeft() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            thrustStrength += this.thrusterFrontRight[i].getThrustStrength();
            this.thrusterFrontRight[i].setActive();
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            thrustStrength += this.thrusterBackRight[i].getThrustStrength();
            this.thrusterBackRight[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.getTurnedRightVektor().getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void addForce(Vector2D vector) {
        this.force = this.force.addVector(vector);
    }
    
    protected void calculateRotationDegree() {
        boolean isLeft = false;
        float oldRotationDegree = this.rotationDegree;
        Vector2D fixedVector = new Vector2D(0,-1);
        
        if (this.middleX + this.direction.getX() < this.middleX) {
            isLeft = true;
        }
        
        this.rotationDegree = (float)fixedVector.getAngle(this.direction);
        
        if(isLeft) {
            this.rotationDegree = 360 - this.rotationDegree;
        }
        
        if(oldRotationDegree < this.rotationDegree - 0.1) {
            for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
                this.thrusterFrontLeft[i].setActive();
            }

            for (int i = 0;i < this.thrusterBackRight.length;i++) {
                this.thrusterBackRight[i].setActive();
            }
        } else if(oldRotationDegree > this.rotationDegree + 0.1) {
            for (int i = 0;i < this.thrusterFrontRight.length;i++) {
                this.thrusterFrontRight[i].setActive();
            }

            for (int i = 0;i < this.thrusterBackLeft.length;i++) {
                this.thrusterBackLeft[i].setActive();
            }
        }
    }
    
    public void draw(Graphics2D g2d) {
        g2d.rotate(Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
        
        if ((this.actual + 1) < img.length) {
            this.actual += 1;
        } else {
            this.actual = 0;
        }
        
        for (int i = 0;i < this.weapons.length;i++) {
            this.weapons[i].draw(g2d, direction);
        }
        
        g2d.drawImage(this.img[this.actual], (int)this.posX, (int)this.posY, null);
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            this.thrusterForward[i].draw(g2d,this.rotationDegree);
        }
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            this.thrusterBackward[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            this.thrusterFrontLeft[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            this.thrusterFrontRight[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            this.thrusterBackLeft[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            this.thrusterBackRight[i].draw(g2d);
        }
        
        g2d.rotate(-Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
    }
    
    public void addForwardThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterForward.length + 1];
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            dummy[i] = this.thrusterForward[i];
        }
        
        dummy[this.thrusterForward.length] = thruster;
        this.thrusterForward = dummy;
    }
    
    public void addBackwardThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterBackward.length + 1];
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            dummy[i] = this.thrusterBackward[i];
        }
        
        dummy[this.thrusterBackward.length] = thruster;
        this.thrusterBackward = dummy;
    }
    
    public void addFrontLeftThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterFrontLeft.length + 1];
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            dummy[i] = this.thrusterFrontLeft[i];
        }
        
        dummy[this.thrusterFrontLeft.length] = thruster;
        this.thrusterFrontLeft = dummy;
    }
    
    public void addFrontRightThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterFrontRight.length + 1];
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            dummy[i] = this.thrusterFrontRight[i];
        }
        
        dummy[this.thrusterFrontRight.length] = thruster;
        this.thrusterFrontRight = dummy;
    }
    
    public void addBackLeftThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterBackLeft.length + 1];
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            dummy[i] = this.thrusterBackLeft[i];
        }
        
        dummy[this.thrusterBackLeft.length] = thruster;
        this.thrusterBackLeft = dummy;
    }
    
    public void addBackRightThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterBackRight.length + 1];
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            dummy[i] = this.thrusterBackRight[i];
        }
        
        dummy[this.thrusterBackRight.length] = thruster;
        this.thrusterBackRight = dummy;
    }
    
    public void addWeapon(Weapon weapon) {
        Weapon[] dummy = new Weapon[this.weapons.length + 1];
        
        for (int i = 0;i < this.weapons.length;i++) {
            dummy[i] = this.weapons[i];
        }
        
        dummy[this.weapons.length] = weapon;
        this.weapons = dummy;
    }
    
    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }
    
    public Weapon[] getWeapons() {
        return this.weapons;
    }
    
    public void setRotationThrust(float rotationThrust) {
        this.rotationLeftThrust = rotationThrust;
        this.rotationRightThrust = rotationThrust;
    }
    
    public void removeWeapons() {
        this.weapons = new Weapon[0];
    }
    
    public Image[] getImg() {
        return this.img;
    }
    
    public int getDrawX() {
        return (int)this.posX;
    }
    
    public int getDrawY() {
        return (int)this.posY;
    }
    
    public float getMiddleX() {
        return this.middleX;
    }
    
    public float getMiddleY() {
        return this.middleY;
    }
    
    public void setMiddleX(float middleX) {
        this.middleX = middleX;
        this.posX = middleX - (this.width / 2);
    }
    
    public void setMiddleY(float middleY) {
        this.middleY = middleY;
        this.posY = middleY - (this.height / 2);
    }
}
