/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.GameUnit;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.FPSManager;
import support.FixedCollisionBox;
import support.Inventory;
import support.Resources;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Ship implements GameUnit {
    public static final int DUMMY = 1;
    public static final int CORVETTE = 2;
    public static final int ALIEN_HUNTER = 3;
    public static final int ALIEN_HUNTER_SMALL = 4;
    
    public static final int ENEMY = 1;
    public static final int ALLIED = 2;
    
    public static final int STRONG = 1;
    public static final int MEDIUM = 2;
    public static final int WEAK = 3;
    
    protected int fraction;
    
    protected boolean destroyed;
    
    protected int typ;
    protected BufferedImage icon;
    
    protected BufferedImage[] img;
    protected int actual;
    protected long animationDelay;
    protected long lastAnimationImage_Time;
    
    protected double rotationDegree;
    protected double rotationSpeed;
    
    protected int width;
    protected int height;
    
    protected double middleX;
    protected double middleY;
    protected double posX;
    protected double posY;
    
    protected Vector2D direction;
    protected Vector2D force;
    
    protected Thruster[] thrusterForward;
    protected Thruster[] thrusterBackward;
    protected Thruster[] thrusterFrontLeft;
    protected Thruster[] thrusterFrontRight;
    protected Thruster[] thrusterBackLeft;
    protected Thruster[] thrusterBackRight;
    
    double rotationLeftThrust;
    double rotationRightThrust;
    
    protected Weapon[][] weaponSlots;
    protected Turret[][] turretSlot;
    
    protected WeaponBase[][] weaponBuilder;
    protected Thruster[][] thrusterBuilder;
    
    protected int activeSlot;
    protected boolean slotChanged;
    
    protected FixedCollisionBox boxCollider;
    protected double health;
    protected double maxHealth;
    protected double armor;
    protected Battery weaponBattery;
    
    protected double reactorOutput;
    protected Reactor reactor;
    
    protected Shield shield;
    protected BufferedImage shieldRingIcon;
    
    protected Explosion explosion;
    
    double maxSpeed = 700;
    double mass = 1000;
    
    protected Inventory cargo;
    protected Inventory additionalSlots;
    
    public Ship() {
        
    }
    
    public Ship(int typ,int width,int height,int x,int y,double health,Resources resources, Explosion explosion, int fraction) {
        this.typ = typ;
        
        switch(typ) {
            case DUMMY:
                img = resources.getScaledBufferedImageField(Resources.SHIP_DUMMY, width, height,Resources.SCALE_SMOOTH);
                icon = resources.getScaledBufferedImageField(Resources.ICON_SHIP_DUMMY,420,420,Resources.SCALE_SMOOTH)[0];
                weaponSlots = new Weapon[1][2];
                turretSlot = new Turret[0][0];
                
                weaponBuilder = new WeaponBase[weaponSlots.length + turretSlot.length][];
                weaponBuilder[0] = new WeaponBase[2];
                weaponBuilder[0][0] = new Weapon(Weapon.LASER,30,30,-20,10,(int)this.middleX,(int)this.middleX,0,0,Weapon.REPETIER,0,1,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                weaponBuilder[0][1] = new Weapon(Weapon.LASER,30,30,20,10,(int)this.middleX,(int)this.middleX,0,0,Weapon.REPETIER,0,2,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                
                thrusterForward = new Thruster[1];
                thrusterBackward = new Thruster[2];
                thrusterFrontLeft = new Thruster[1];
                thrusterFrontRight = new Thruster[1];
                thrusterBackLeft = new Thruster[1];
                thrusterBackRight = new Thruster[1];
                
                thrusterBuilder = new Thruster[6][1];
                thrusterBuilder[1] = new Thruster[2];
                
                thrusterBuilder[0][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,0,25,180.0,true);
                thrusterBuilder[1][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-6,-20,-50.0,true);
                thrusterBuilder[1][1] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,6,-20,50.0,true);
                thrusterBuilder[2][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-10,-13,-90.0,true);
                thrusterBuilder[3][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,10,-13,90.0,true);
                thrusterBuilder[4][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-10,13,-90.0,true);
                thrusterBuilder[5][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,10,13,90.0,true);
                
                this.armor = 1;
                this.mass = 100;
                this.cargo = new Inventory(10,64);
                
                this.animationDelay = 50;
                this.shieldRingIcon = MainLoop.resources.getImageField(Resources.UI_SHIELDRINGSHIPICONS)[0];
                break;
            case CORVETTE:
                img = resources.getScaledBufferedImageField(Resources.SHIP_CORVETTE, width, height,Resources.SCALE_SMOOTH);
                icon = resources.getScaledBufferedImageField(Resources.ICON_SHIP_CORVETTE,530,530,Resources.SCALE_SMOOTH)[0];
                weaponSlots = new Weapon[1][2];
                turretSlot = new Turret[1][1];
                
                weaponBuilder = new WeaponBase[weaponSlots.length + turretSlot.length][];
                weaponBuilder[0] = new WeaponBase[2];
                weaponBuilder[0][0] = new Weapon(Weapon.LASER,60,60,-38,-70,(int)this.middleX,(int)this.middleY,0,0,Weapon.REPETIER,1,1,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                weaponBuilder[0][1] = new Weapon(Weapon.LASER,60,60,38,-70,(int)this.middleX,(int)this.middleY,0,0,Weapon.REPETIER,1,2,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                
                weaponBuilder[1] = new WeaponBase[1];
                weaponBuilder[1][0] = new Turret(Turret.LASER,50,50,0,60,(int)this.middleX,(int)this.middleY,0,0,Turret.REPETIER,1,2,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                
                thrusterForward = new Thruster[2];
                thrusterBackward = new Thruster[2];
                thrusterFrontLeft = new Thruster[1];
                thrusterFrontRight = new Thruster[1];
                thrusterBackLeft = new Thruster[1];
                thrusterBackRight = new Thruster[1];
                
                thrusterBuilder = new Thruster[6][1];
                thrusterBuilder[0] = new Thruster[2];
                thrusterBuilder[1] = new Thruster[2];
                
                thrusterBuilder[0][0] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-40,86,180.0,true);
                thrusterBuilder[0][1] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,40,86,180.0,true);
                
                thrusterBuilder[1][0] = new Thruster(Thruster.STANDART,10,10,15,10,0,this.middleX,this.middleY,-25,-74,-60.0,true);
                thrusterBuilder[1][1] = new Thruster(Thruster.STANDART,10,10,15,10,0,this.middleX,this.middleY,25,-74,60.0,true);
                
                thrusterBuilder[2][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-30,-49,-90.0,true);
                thrusterBuilder[3][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,30,-49,90.0,true);
                thrusterBuilder[4][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-46,72,-90.0,true);
                thrusterBuilder[5][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,46,72,90.0,true);
                
                this.armor = 5;
                this.mass = 5000;
                this.cargo = new Inventory(30,64);
                
                this.animationDelay = 50;
                this.shieldRingIcon = MainLoop.resources.getImageField(Resources.UI_SHIELDRINGSHIPICONS)[1];
                break;
            case ALIEN_HUNTER:
                img = resources.getScaledBufferedImageField(Resources.SHIP_ALIEN_HUNTER, width, height,Resources.SCALE_SMOOTH);
                icon = resources.getScaledBufferedImageField(Resources.ICON_SHIP_ALIENHUNTER,458,458,Resources.SCALE_SMOOTH)[0];
                weaponSlots = new Weapon[1][2];
                turretSlot = new Turret[0][0];
                
                weaponBuilder = new WeaponBase[weaponSlots.length + turretSlot.length][];
                weaponBuilder[0] = new WeaponBase[2];
                weaponBuilder[0][0] = new Weapon(Weapon.LASER,60,60,-70,-40,(int)this.middleX,(int)this.middleY,0,0,Weapon.REPETIER,1,1,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                weaponBuilder[0][1] = new Weapon(Weapon.LASER,60,60,70,-40,(int)this.middleX,(int)this.middleY,0,0,Weapon.REPETIER,1,2,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                
                thrusterForward = new Thruster[8];
                thrusterBackward = new Thruster[2];
                thrusterFrontLeft = new Thruster[1];
                thrusterFrontRight = new Thruster[1];
                thrusterBackLeft = new Thruster[1];
                thrusterBackRight = new Thruster[1];
                
                thrusterBuilder = new Thruster[6][1];
                thrusterBuilder[0] = new Thruster[8];
                thrusterBuilder[1] = new Thruster[2];
                
                thrusterBuilder[0][0] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-34,68,180.0,true);
                thrusterBuilder[0][1] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,34,68,180.0,true);
                thrusterBuilder[0][2] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-14,66,180.0,true);
                thrusterBuilder[0][3] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,14,66,180.0,true);
                thrusterBuilder[0][4] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-63,44,180.0,true);
                thrusterBuilder[0][5] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,63,44,180.0,true);
                thrusterBuilder[0][6] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-95,38,180.0,true);
                thrusterBuilder[0][7] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,95,38,180.0,true);
                
                thrusterBuilder[1][0] = new Thruster(Thruster.STANDART,10,10,15,10,0,this.middleX,this.middleY,-105,-14,0.0,true);
                thrusterBuilder[1][1] = new Thruster(Thruster.STANDART,10,10,15,10,0,this.middleX,this.middleY,105,-14,0.0,true);
                
                thrusterBuilder[2][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-16,-44,-90.0,true);
                thrusterBuilder[3][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,16,-44,90.0,true);
                thrusterBuilder[4][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-28,44,-90.0,true);
                thrusterBuilder[5][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,28,44,90.0,true);
                
                this.armor = 5;
                this.mass = 5000;
                this.cargo = new Inventory(30,64);
                
                this.animationDelay = 50;
                this.shieldRingIcon = MainLoop.resources.getImageField(Resources.UI_SHIELDRINGSHIPICONS)[2];
                break;
            case ALIEN_HUNTER_SMALL:
                img = resources.getScaledBufferedImageField(Resources.SHIP_ALIEN_HUNTER_SMALL, width, height,Resources.SCALE_SMOOTH);
                icon = resources.getScaledBufferedImageField(Resources.ICON_SHIP_ALIENHUNTER_SMALL,390,390,Resources.SCALE_SMOOTH)[0];
                weaponSlots = new Weapon[1][1];
                turretSlot = new Turret[0][0];
                
                weaponBuilder = new WeaponBase[weaponSlots.length + turretSlot.length][];
                weaponBuilder[0] = new WeaponBase[2];
                weaponBuilder[0][0] = new Weapon(Weapon.LASER,60,60,0,-74,(int)this.middleX,(int)this.middleY,0,0,Weapon.REPETIER,1,1,true,new Bullet(Bullet.LASER_RED,0,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
                
                thrusterForward = new Thruster[8];
                thrusterBackward = new Thruster[2];
                thrusterFrontLeft = new Thruster[1];
                thrusterFrontRight = new Thruster[1];
                thrusterBackLeft = new Thruster[1];
                thrusterBackRight = new Thruster[1];
                
                thrusterBuilder = new Thruster[6][1];
                thrusterBuilder[0] = new Thruster[8];
                thrusterBuilder[1] = new Thruster[2];
                
                thrusterBuilder[0][0] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-27,54,180.0,true);
                thrusterBuilder[0][1] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,27,54,180.0,true);
                thrusterBuilder[0][2] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-9,52,180.0,true);
                thrusterBuilder[0][3] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,9,52,180.0,true);
                thrusterBuilder[0][4] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-51,18,180.0,true);
                thrusterBuilder[0][5] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,51,18,180.0,true);
                thrusterBuilder[0][6] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,-65,12,180.0,true);
                thrusterBuilder[0][7] = new Thruster(Thruster.STANDART,10,10,10,20,0,this.middleX,this.middleY,65,12,180.0,true);
                
                thrusterBuilder[1][0] = new Thruster(Thruster.STANDART,10,10,15,10,0,this.middleX,this.middleY,-63,-18,0.0,true);
                thrusterBuilder[1][1] = new Thruster(Thruster.STANDART,10,10,15,10,0,this.middleX,this.middleY,63,-18,0.0,true);
                
                thrusterBuilder[2][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-12,-42,-90.0,true);
                thrusterBuilder[3][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,12,-42,90.0,true);
                thrusterBuilder[4][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,-20,32,-90.0,true);
                thrusterBuilder[5][0] = new Thruster(Thruster.STANDART,10,10,10,10,0,this.middleX,this.middleY,20,32,90.0,true);
                
                this.armor = 5;
                this.mass = 500;
                this.cargo = new Inventory(30,64);
                
                this.animationDelay = 50;
                this.shieldRingIcon = MainLoop.resources.getImageField(Resources.UI_SHIELDRINGSHIPICONS)[3];
                break;
        }
        
        this.activeSlot = 0;
        this.slotChanged = false;
        
        this.actual = 0;
        this.lastAnimationImage_Time = 0;
        this.rotationDegree = 0.0;
        
        this.width = width;
        this.height = height;
        
        this.posX = x;
        this.posY = y;
        
        this.middleX = this.posX + (width / 2.0F);
        this.middleY = this.posY + (height / 2.0F);
        
        this.direction = new Vector2D(0,-1);
        this.force = new Vector2D(0,0);
        
        this.health = health;
        this.maxHealth = health;
        this.boxCollider = new FixedCollisionBox(this.middleX,this.middleY,this.width,this.height);
        
        //this.shield = new Shield(this.middleX,this.middleY,this.width,this.height, new Reactor(10,500), new Battery(400));
        this.explosion = explosion;
        
        this.fraction = fraction;
        
        this.additionalSlots = new Inventory(3,64);
    }
    
    public void throttleForward() {
        double rotationChange = 0.0;
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            if (thrusterForward[i] != null) {
                this.thrusterForward[i].setActive();
                
                rotationChange += this.thrusterForward[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterForward[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        this.rotationSpeed += rotationChange;
    }
    
    public void throttleBackward() {
        double rotationChange = 0.0;
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            if (thrusterBackward[i] != null) {
                this.thrusterBackward[i].setActive();
                
                rotationChange += this.thrusterBackward[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterBackward[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        this.rotationSpeed += rotationChange;
    }
    
    public void throttleRight() {
        double rotationChange = 0.0;
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            if (thrusterFrontLeft[i] != null) {
                this.thrusterFrontLeft[i].setActive();
                
                rotationChange += this.thrusterFrontLeft[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterFrontLeft[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            if (thrusterBackLeft[i] != null) {
                this.thrusterBackLeft[i].setActive();
                
                rotationChange += this.thrusterBackLeft[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterBackLeft[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        this.rotationSpeed += rotationChange;
    }
    
    public void throttleLeft() {
        double rotationChange = 0.0;
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            if (thrusterFrontRight[i] != null) {
                this.thrusterFrontRight[i].setActive();
                
                rotationChange += this.thrusterFrontRight[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterFrontRight[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            if (thrusterBackRight[i] != null) {
                this.thrusterBackRight[i].setActive();
                
                rotationChange += this.thrusterBackRight[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterBackRight[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        this.rotationSpeed += rotationChange;
    }
    
    protected void manageThrustTo(double toX, double toY, Vector2D additionalTargetSpeed) {
        Vector2D destination = new Vector2D(this.middleX,this.middleY,toX,toY);
        double destinationAngle = 0;
        
        destination = destination.addVector(additionalTargetSpeed);
        
        destination = destination.subtractVector(this.force);
        destination = destination.multiplyWithNumber(-1);
        
        if (destination.getLength() > 0) {
            destinationAngle = (double)this.direction.getAngle(destination);

            if(destination.isLeftOf(this.direction)) {  //isLeft
                destinationAngle = 360 - destinationAngle;
            }
        
            if (destinationAngle >= 0 && destinationAngle < 45) {
                this.throttleBackward();
            } else if (destinationAngle >= 45 && destinationAngle < 135) {
                this.throttleLeft();
            } else if (destinationAngle >= 135 && destinationAngle < 225) {
                this.throttleForward();
            } else if (destinationAngle >= 225 && destinationAngle < 315) {
                this.throttleRight();
            } else {
                this.throttleBackward();
            }
        }
    }
    
    protected void thrustRotationToLeft() {
        double rotationChange = 0.0;
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            if (thrusterFrontRight[i] != null) {
                this.thrusterFrontRight[i].setActive();
                
                rotationChange += this.thrusterFrontRight[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterFrontRight[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            if (thrusterBackLeft[i] != null) {
                this.thrusterBackLeft[i].setActive();
                
                rotationChange += this.thrusterBackLeft[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterBackLeft[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        this.rotationSpeed += rotationChange;
    }
    
    protected void thrustRotationToRight() {
        double rotationChange = 0.0;
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            if (thrusterFrontLeft[i] != null) {
                this.thrusterFrontLeft[i].setActive();
                
                rotationChange += this.thrusterFrontLeft[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterFrontLeft[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            if (thrusterBackRight[i] != null) {
                this.thrusterBackRight[i].setActive();
                
                rotationChange += this.thrusterBackRight[i].getRotationDiffer();
                this.force = this.force.addVector(this.thrusterBackRight[i].getThrustDirection().getRotatedVector(this.rotationDegree).multiplyWithNumber(FPSManager.DELTA).divideByNumber(this.mass));
                
                if (this.force.getLength() > this.maxSpeed) {
                    this.force = this.force.getUnitVector().multiplyWithNumber(maxSpeed);
                }
            }
        }
        
        this.rotationSpeed += rotationChange;
    }
    
    protected void reduceSpeed() {
        manageThrustTo(this.middleX,this.middleY,new Vector2D(0,0));
        
        if (this.getSpeed() <= 1) {
            this.stop();
        }
    }
    
    public void addForce(Vector2D vector) {
        this.force = this.force.addVector(vector);
    }
    
    protected void manageRotation(double toX,double toY) {
        Vector2D targetVector = new Vector2D(this.middleX,this.middleY,toX,toY);
        boolean leftOf = this.direction.isLeftOf(targetVector);
        double angle = this.direction.getAngle(targetVector);
        
        if(angle > 0.1 || (this.rotationSpeed / this.mass) > 0.5) {
            if (leftOf) {
                if (angle - (this.rotationSpeed / this.mass) < 0) {
                    thrustRotationToLeft();
                } else {
                    thrustRotationToRight();
                }
            } else {
                if (angle + (this.rotationSpeed / this.mass) < 0) {
                    thrustRotationToRight();
                } else {
                    thrustRotationToLeft();
                }
            }
        } else {
            this.direction = targetVector.getUnitVector();
        }
    }
    
    protected void calculateRotationChange() {
        Vector2D fixedVector = new Vector2D(0,-1);
        
        this.rotationDegree += (this.rotationSpeed * FPSManager.DELTA / this.mass);
        
        if (this.rotationDegree > 360) {
            this.rotationDegree -= 360;
        }
        
        if (this.rotationDegree < 0) {
            this.rotationDegree += 360;
        }
        
        this.direction = fixedVector.getRotatedVector(this.rotationDegree);
    }
    
    public void draw(Graphics2D g2d) {
        g2d.rotate(Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
        
        if ((this.actual + 1) < img.length && (System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual += 1;
            this.lastAnimationImage_Time = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual = 0;
            this.lastAnimationImage_Time = System.currentTimeMillis();
        }
        
        for (int i = 0;i < this.weaponSlots.length;i++) {
            for (int j = 0;j < this.weaponSlots[i].length;j++) {
                if (this.weaponSlots[i][j] != null && this.weaponSlots[i][j].isBelow()) {
                    this.weaponSlots[i][j].draw(g2d, direction);
                }
            }
        }
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            if (thrusterForward[i] != null && thrusterForward[i].isBelow()) {
                this.thrusterForward[i].draw(g2d,this.rotationDegree);
            }
        }
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            if (thrusterBackward[i] != null && thrusterBackward[i].isBelow()) {
                this.thrusterBackward[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            if (thrusterFrontLeft[i] != null && thrusterFrontLeft[i].isBelow()) {
                this.thrusterFrontLeft[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            if (thrusterFrontRight[i] != null && thrusterFrontRight[i].isBelow()) {
                this.thrusterFrontRight[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            if (thrusterBackLeft[i] != null && thrusterBackLeft[i].isBelow()) {
                this.thrusterBackLeft[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            if (thrusterBackRight[i] != null && thrusterBackRight[i].isBelow()) {
                this.thrusterBackRight[i].draw(g2d);
            }
        }
        
        g2d.drawImage(this.img[this.actual], (int)this.posX, (int)this.posY, null);
        
        for (int i = 0;i < this.weaponSlots.length;i++) {
            for (int j = 0;j < this.weaponSlots[i].length;j++) {
                if (this.weaponSlots[i][j] != null && !this.weaponSlots[i][j].isBelow()) {
                    this.weaponSlots[i][j].draw(g2d, direction);
                }
            }
        }
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            if (thrusterForward[i] != null && !thrusterForward[i].isBelow()) {
                this.thrusterForward[i].draw(g2d,this.rotationDegree);
            }
        }
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            if (thrusterBackward[i] != null && !thrusterBackward[i].isBelow()) {
                this.thrusterBackward[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            if (thrusterFrontLeft[i] != null && !thrusterFrontLeft[i].isBelow()) {
                this.thrusterFrontLeft[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            if (thrusterFrontRight[i] != null && !thrusterFrontRight[i].isBelow()) {
                this.thrusterFrontRight[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            if (thrusterBackLeft[i] != null && !thrusterBackLeft[i].isBelow()) {
                this.thrusterBackLeft[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            if (thrusterBackRight[i] != null && !thrusterBackRight[i].isBelow()) {
                this.thrusterBackRight[i].draw(g2d);
            }
        }
        
        for (int i = 0;i < this.turretSlot.length;i++) {
            for (int j = 0;j < this.turretSlot[i].length;j++) {
                if (this.turretSlot[i][j] != null) {
                    this.turretSlot[i][j].draw(g2d, direction);
                }
            }
        }
        
        if (this.shield != null) {
            this.shield.draw(g2d);
        }
        
        g2d.rotate(-Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
    }
    
    public Thruster setForwardThruster(Thruster thruster) {
        Thruster oldThruster = this.thrusterForward[0];
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            if (thruster != null) {
                this.thrusterForward[i] = new Thruster(thruster.getType(),thrusterBuilder[0][i].getThrusterWidth(),thrusterBuilder[0][i].getThrusterHeight(),thruster.getInitialFlameWidth(),thruster.getInitialFlameHeight(),thruster.getInitialThrustStrength(),this.middleX,this.middleY,thrusterBuilder[0][i].getOffsetX(),thrusterBuilder[0][i].getOffsetY(),thrusterBuilder[0][i].getRotationDegree(),thrusterBuilder[0][i].isBelow());
            } else {
                this.thrusterForward[i] = null;
            }
        }
        
        return oldThruster;
    }
    
    public Thruster setBackwardThruster(Thruster thruster) {
        Thruster oldThruster = this.thrusterBackward[0];
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            if (thruster != null) {
                this.thrusterBackward[i] = new Thruster(thruster.getType(),thrusterBuilder[1][i].getThrusterWidth(),thrusterBuilder[1][i].getThrusterHeight(),thruster.getInitialFlameWidth(),thruster.getInitialFlameHeight(),thruster.getInitialThrustStrength(),this.middleX,this.middleY,thrusterBuilder[1][i].getOffsetX(),thrusterBuilder[1][i].getOffsetY(),thrusterBuilder[1][i].getRotationDegree(),thrusterBuilder[1][i].isBelow());
            } else {
                this.thrusterBackward[i] = null;
            }
        }
        
        return oldThruster;
    }
    
    public Thruster setLeftThruster(Thruster thruster) {
        Thruster oldThruster = this.thrusterFrontLeft[0];
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            if (thruster != null) {
                this.thrusterFrontLeft[i] = new Thruster(thruster.getType(),thrusterBuilder[2][i].getThrusterWidth(),thrusterBuilder[2][i].getThrusterHeight(),thruster.getInitialFlameWidth(),thruster.getInitialFlameHeight(),thruster.getInitialThrustStrength(),this.middleX,this.middleY,thrusterBuilder[2][i].getOffsetX(),thrusterBuilder[2][i].getOffsetY(),thrusterBuilder[2][i].getRotationDegree(),thrusterBuilder[2][i].isBelow());
            } else {
                this.thrusterFrontLeft[i] = null;
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            if (thruster != null) {
                this.thrusterBackLeft[i] = new Thruster(thruster.getType(),thrusterBuilder[4][i].getThrusterWidth(),thrusterBuilder[4][i].getThrusterHeight(),thruster.getInitialFlameWidth(),thruster.getInitialFlameHeight(),thruster.getInitialThrustStrength(),this.middleX,this.middleY,thrusterBuilder[4][i].getOffsetX(),thrusterBuilder[4][i].getOffsetY(),thrusterBuilder[4][i].getRotationDegree(),thrusterBuilder[4][i].isBelow());
            } else {
                this.thrusterBackLeft[i] = null;
            }
        }
        
        return oldThruster;
    }
    
    public Thruster setRightThruster(Thruster thruster) {
        Thruster oldThruster = this.thrusterFrontRight[0];
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            if (thruster != null) {
                this.thrusterFrontRight[i] = new Thruster(thruster.getType(),thrusterBuilder[3][i].getThrusterWidth(),thrusterBuilder[3][i].getThrusterHeight(),thruster.getInitialFlameWidth(),thruster.getInitialFlameHeight(),thruster.getInitialThrustStrength(),this.middleX,this.middleY,thrusterBuilder[3][i].getOffsetX(),thrusterBuilder[3][i].getOffsetY(),thrusterBuilder[3][i].getRotationDegree(),thrusterBuilder[3][i].isBelow());
            } else {
                this.thrusterFrontRight[i] = null;
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            if (thruster != null) {
                this.thrusterBackRight[i] = new Thruster(thruster.getType(),thrusterBuilder[5][i].getThrusterWidth(),thrusterBuilder[5][i].getThrusterHeight(),thruster.getInitialFlameWidth(),thruster.getInitialFlameHeight(),thruster.getInitialThrustStrength(),this.middleX,this.middleY,thrusterBuilder[5][i].getOffsetX(),thrusterBuilder[5][i].getOffsetY(),thrusterBuilder[5][i].getRotationDegree(),thrusterBuilder[5][i].isBelow());
            } else {
                this.thrusterBackRight[i] = null;
            }
        }
        
        return oldThruster;
    }
    
    public WeaponBase setWeapon(WeaponBase weapon,int slot) {
        WeaponBase oldWeapon = weapon;
        
        if ((weapon instanceof Weapon || weapon == null) && slot >= 0 && slot < this.weaponSlots.length) {
            oldWeapon = changeWeaponInSlot(slot,weapon,1);
        } else if ((weapon instanceof Turret || weapon == null) && slot >= this.weaponSlots.length && slot < this.weaponSlots.length + this.turretSlot.length) {
            oldWeapon = changeWeaponInSlot(slot,weapon,2);
        }
        
        return oldWeapon;
    }
    
    public boolean isHit(Bullet bullet,FixedCollisionBox b, int flag, double damage) {
        boolean hit = false;
        
        if (this.fraction != flag) {
            if (this.shield != null && this.shield.isHit(bullet,b)) {
                hit = true;
            } else if (this.boxCollider.isInRange(b) && this.boxCollider.isOverlapping(b)) {
                hit = true;
                doDamage(damage);
                bullet.hitExplosion();
            }
        }
        
        return hit;
    }
    
    public void doDamage(double damage) {
        if (health - (damage / this.armor) >= 0) {
            this.health -= (damage / this.armor);
        } else {
            health = 0;
        }
    }
    
    protected WeaponBase changeWeaponInSlot(int slot,WeaponBase weapon, int which) {
        WeaponBase oldWeapon = null;
        
        if (which == 1) {
            oldWeapon = weaponSlots[slot][0];
            for (int i = 0;i < this.weaponSlots[slot].length;i++) {
                if (weapon != null) {
                    this.weaponSlots[slot][i] = new Weapon(weapon.getWeaponType(),this.weaponBuilder[slot][i].getWidth(),this.weaponBuilder[slot][i].getHeight(),this.weaponBuilder[slot][i].getxOffset(),this.weaponBuilder[slot][i].getyOffset(),(int)this.middleX,(int)this.middleY,this.weaponBuilder[slot][i].getRotationDegree(),weapon.getCoolDown(),weapon.getShootType(),weapon.getEnergyCost(),weaponBuilder[slot][i].getSide(),weaponBuilder[slot][i].isBelow(),new Bullet(weapon.getBullet()));
                    this.weaponSlots[slot][i].setBulletFraction(this.fraction);
                } else {
                    this.weaponSlots[slot][i] = null;
                }
            }
        } else {
            oldWeapon = turretSlot[slot - this.weaponSlots.length][0];
            for (int i = 0;i < this.turretSlot[slot - this.weaponSlots.length].length;i++) {
                if (weapon != null) {
                    this.turretSlot[slot - this.weaponSlots.length][i] = new Turret(weapon.getWeaponType(),this.weaponBuilder[slot][i].getWidth(),this.weaponBuilder[slot][i].getHeight(),this.weaponBuilder[slot][i].getxOffset(),this.weaponBuilder[slot][i].getyOffset(),(int)this.middleX,(int)this.middleY,this.weaponBuilder[slot][i].getRotationDegree(),weapon.getCoolDown(),weapon.getShootType(),weapon.getEnergyCost(),weaponBuilder[slot][i].getSide(),new Bullet(weapon.getBullet()));
                    this.turretSlot[slot - this.weaponSlots.length][i].setBulletFraction(this.fraction);
                } else {
                    this.turretSlot[slot - this.weaponSlots.length][i] = null;
                }
            }
        }
        
        this.slotChanged = true;
        return oldWeapon;
    }
    
    public Reactor setReactor(Reactor reactor) {
        Reactor oldReactor = this.reactor;
        
        if (reactor != null) {
            this.reactor = new Reactor(reactor.getEnergyOutputValue(),reactor.getReactorOutputDelay());
        } else {
            this.reactor = null;
        }
        
        return oldReactor;
    }
    
    public Battery setBattery(Battery battery) {
        Battery oldBattery = this.weaponBattery;
        
        if (battery != null) {
            this.weaponBattery = new Battery(battery.getCapacity());
        } else {
            this.weaponBattery = null;
        }
        
        return oldBattery;
    }
    
    public Shield setShield(Shield shield) {
        Shield oldShield = this.shield;
        
        if (shield != null) {
            this.shield = new Shield(this.middleX,this.middleY,this.width,this.height,shield.getReactor(),shield.getBattery());
        } else {
            this.shield = null;
        }
        
        return oldShield;
    }
    
    public void setWeapons(Weapon[][] weapons) {
        this.weaponSlots = weapons;
        this.slotChanged = true;
    }
    
    public Weapon[][] getWeapons() {
        return this.weaponSlots;
    }
    
    public void setRotationThrust(double rotationThrust) {
        this.rotationLeftThrust = rotationThrust;
        this.rotationRightThrust = rotationThrust;
    }
    
    public void removeWeapons() {
        this.weaponSlots = new Weapon[0][0];
        this.slotChanged = true;
    }
    
    public BufferedImage[] getImg() {
        return this.img;
    }
    
    public int getDrawX() {
        return (int)this.posX;
    }
    
    public int getDrawY() {
        return (int)this.posY;
    }
    
    @Override
    public double getMiddleX() {
        return this.middleX;
    }
    
    @Override
    public double getMiddleY() {
        return this.middleY;
    }
    
    public void setMiddleX(double middleX) {
        this.middleX = middleX;
        this.posX = middleX - (this.width / 2);
    }
    
    public void setMiddleY(double middleY) {
        this.middleY = middleY;
        this.posY = middleY - (this.height / 2);
    }
    
    public void setSlot(int slot) {
        if(slot < this.weaponSlots.length + this.turretSlot.length) {
            this.activeSlot = slot;
        }
    }
    
    public void increaseSlot() {
        if((this.activeSlot + 1) < this.weaponSlots.length + this.turretSlot.length) {
            this.activeSlot++;
        } else {
            this.activeSlot = 0;
        }
    }
    
    public void decreaseSlot() {
        if (this.activeSlot - 1 >= 0) {
            this.activeSlot--;
        } else {
            if (this.weaponSlots.length + this.turretSlot.length - 1 >= 0) {
                this.activeSlot = this.weaponSlots.length + this.turretSlot.length - 1;
            } else {
                this.activeSlot = 0;
            }
        }
    }
    
    public void stop() {
        this.force = new Vector2D(0,0);
    }
    
    public BufferedImage getShieldRingIcon() {
        return this.shieldRingIcon;
    }
    
    public double getSpeed() {
        return this.force.getLength();
    }
    
    public Vector2D getForce() {
        return this.force;
    }
    
    public double getPercentOfHealth() {
        return (this.health * 100f) / this.maxHealth;
    }
    
    public double getHealth() {
        return this.health;
    }
    
    public double getMaxHealth() {
        return this.maxHealth;
    }
    
    public double getArmor() {
        return this.armor;
    }
    
    public double getMass() {
        return this.mass;
    }
    
    public Inventory getCargoSpace() {
        return this.cargo;
    }
    
    public Inventory getAdditionalSlots() {
        return this.additionalSlots;
    }
    
    public BufferedImage getIcon() {
        return this.icon;
    }
    
    public double getReactorOutput() {
        if (this.reactor != null) {
            return this.reactor.getEnergyOutputValue();
        } else {
            return 0;
        }
    }
    
    public double getFrontThrust() {
        double thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            if (thrusterForward[i] != null) {
                thrustStrength += this.thrusterForward[i].getInitialThrustStrength();
            }
        }
        
        return thrustStrength;
    }
    
    public double getBackThrust() {
        double thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            if (thrusterBackward[i] != null) {
                thrustStrength += this.thrusterBackward[i].getInitialThrustStrength();
            }
        }
        
        return thrustStrength;
    }
    
    public double getLeftThrust() {
        double thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            if (thrusterFrontLeft[i] != null) {
                thrustStrength += this.thrusterFrontLeft[i].getInitialThrustStrength();
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            if (thrusterBackLeft[i] != null) {
                thrustStrength += this.thrusterBackLeft[i].getInitialThrustStrength();
            }
        }
        
        return thrustStrength;
    }
    
    public double getRightThrust() {
        double thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            if (thrusterFrontRight[i] != null) {
                thrustStrength += this.thrusterFrontRight[i].getInitialThrustStrength();
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            if (thrusterBackRight[i] != null) {
                thrustStrength += this.thrusterBackRight[i].getInitialThrustStrength();
            }
        }
        
        return thrustStrength;
    }
    
    public int getWeaponSlots() {
        return this.weaponSlots.length;
    }
    
    public int getTurretSlots() {
        return this.turretSlot.length;
    }
    
    public Object getWeaponFromSlot(int slot) {
        Object object = null;
        
        if (slot < this.weaponSlots.length && this.weaponSlots[slot].length > 0) {
            object = this.weaponSlots[slot][0];
        } else if (slot >= this.weaponSlots.length && slot < this.weaponSlots.length + this.turretSlot.length && this.turretSlot[slot - this.weaponSlots.length].length > 0) {
            object = this.turretSlot[slot - this.weaponSlots.length][0];
        }
        
        return object;
    }
    
    public Reactor getReactor() {
        return this.reactor;
    }
    
    public Shield getShield() {
        return this.shield;
    }
    
    public Battery getBattery() {
        return this.weaponBattery;
    }
    
    public Thruster getForwardThruster() {
        return this.thrusterForward[0];
    }
    
    public Thruster getBackwardThruster() {
        return this.thrusterBackward[0];
    }
    
    public Thruster getLeftThruster() {
        return this.thrusterFrontLeft[0];
    }
    
    public Thruster getRightThruster() {
        return this.thrusterFrontRight[0];
    }
    
    public void addInventory(Inventory inventory) {
        for (int i = 0;i < inventory.getAvailableSlots();i++) {
            inventory.addItems(i,this.cargo.addItems(inventory.removeAllItems(i)));
        }
    }
    
    public double getDamage() {
        double damage = 0;
        
        for (int i = 0;i < this.weaponSlots.length;i++) {
            for (int j = 0;j < this.weaponSlots[i].length;j++) {
                if (this.weaponSlots[i][j] != null) {
                    damage += this.weaponSlots[i][j].getBullet().getDamage();
                }
            }
        }
        
        for (int i = 0;i < this.turretSlot.length;i++) {
            for (int j = 0;j < this.turretSlot[i].length;j++) {
                if (this.turretSlot[i][j] != null) {
                    damage += this.turretSlot[i][j].getBullet().getDamage();
                }
            }
        }
        
        return damage;
    }
    
    public double getDefense() {
        double defense = 0;
        
        defense = this.health * this.armor;
        
        if (this.shield != null) {
            defense += (this.shield.getCapacity() * (this.shield.getReactor().getEnergyOutputValue() / this.shield.getReactor().getReactorOutputDelay()));
        }
        
        return defense;
    }
    
    public double getStrength() {
        double strength = 0;
        
        strength = getDamage() + getDefense();
        
        return strength;
    }
    
    public FixedCollisionBox getCollisionBox() {
        return this.boxCollider;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
