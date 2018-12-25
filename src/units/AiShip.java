/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.FPSManager;
import support.ItemBuilder;
import support.Resources;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class AiShip extends Ship {
    private long range;
    private Ship currentTarget;
    
    private int preStrengthClassification;
    
    private BufferedImage[] healthbar;
    private String name;
    
    private boolean showHealthBar;
    private boolean showName;
    private long healthBarShowedSince;
    private long healthBarShowDuration;
    
    public AiShip() {
        
    }
    
    public AiShip(int typ,int width,int height,int x,int y,double health,Resources resources,int fraction,int range,Explosion explosion, String name, int preStrengthClassification) {
        super(typ, width, height, x, y, health, resources,explosion,fraction);
        this.range = range;
        this.name = name;
        
        this.preStrengthClassification = preStrengthClassification;
        
        this.healthbar = new BufferedImage[2];
        this.healthbar[0] = MainLoop.resources.getScaledBufferedImageField(Resources.UI_HEALTHBAR,1,5,Resources.SCALE_FAST)[0];
        this.healthbar[1] = MainLoop.resources.getScaledBufferedImageField(Resources.UI_HEALTHBARGREEN,1,5,Resources.SCALE_FAST)[0];
        
        this.showHealthBar = false;
        this.showName = false;
        this.healthBarShowedSince = 0;
        this.healthBarShowDuration = 200;
    }
    
    public boolean update() {
        this.middleX = this.middleX + this.force.multiplyWithNumber(FPSManager.DELTA).getX();
        this.middleY = this.middleY + this.force.multiplyWithNumber(FPSManager.DELTA).getY();
        
        this.posX = Background.X + this.middleX - (this.width / 2.0F);
        this.posY = Background.Y + this.middleY - (this.height / 2.0F);
        
        for (int i = 0;i < this.thrusterForward.length; i++) {
            if (thrusterForward[i] != null) {
                this.thrusterForward[i].updatePositionData(this.middleX, this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterBackward.length; i++) {
            if (thrusterBackward[i] != null) {
                this.thrusterBackward[i].updatePositionData(this.middleX, this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length; i++) {
            if (thrusterFrontLeft[i] != null) {
            this.thrusterFrontLeft[i].updatePositionData(this.middleX, this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length; i++) {
            if (thrusterBackLeft[i] != null) {
                this.thrusterBackLeft[i].updatePositionData(this.middleX, this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length; i++) {
            if (thrusterFrontRight[i] != null) {
                this.thrusterFrontRight[i].updatePositionData(this.middleX, this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length; i++) {
            if (thrusterBackRight[i] != null) {
                this.thrusterBackRight[i].updatePositionData(this.middleX, this.middleY);
            }
        }
        
        for (int i = 0;i < this.weaponSlots.length;i++) {
            for (int j = 0;j < this.weaponSlots[i].length;j++) {
                if (this.weaponSlots[i][j] != null) {
                    this.weaponSlots[i][j].update(this.middleX, this.middleY,0,0,0);
                }
            }
        }
        
        this.boxCollider.updateBox(this.middleX, this.middleY);
        this.boxCollider.setRotation(this.rotationDegree);
        
        for (int i = 0;i < this.turretSlot.length;i++) {
            for (int j = 0;j < this.turretSlot[i].length;j++) {
                if (this.currentTarget != null && this.turretSlot[i][j] != null) {
                    this.turretSlot[i][j].update(this.middleX, this.middleY,(int)(this.currentTarget.getMiddleX()),(int)(this.currentTarget.getMiddleY()),this.rotationDegree);
                } else {
                    this.turretSlot[i][j].update(this.middleX, this.middleY,(int)(this.middleX + this.direction.getX() + this.width),(int)(this.middleY + this.direction.getY() + this.width),this.rotationDegree);
                }
            }
        }
        
        if (this.shield != null) {
            this.shield.update(middleX, middleY, this.rotationDegree);
        }
        
        if (this.showHealthBar) {
            if (System.currentTimeMillis() - this.healthBarShowedSince > this.healthBarShowDuration) {
                this.showHealthBar = false;
            }
        }
        
        if (coordsMostlyInShip(MainLoop.mausX, MainLoop.mausY)) {
            this.showHealthBar = true;
            this.showName = true;
        } else {
            this.showName = false;
        }
        
        if (!isInRange(this.currentTarget)) {
            getTarget();
        }
        
        if (this.currentTarget != null) {
             //Verhaltensweisen: Angriffsflug, Kreisen, Flüchten, Verfolgen, eventuell Formationsflug und Flankieren, zielauswahl nach geringeren übel/bessere beute/aufteilung der allied flieger  und Idle
            
            if (this.getPercentOfHealth() >= 20) {
                if (distanceToTarget() >= 600) {
                    directTargetAttack();
                } else {
                    circleAroundTarget(500);
                }
            } else {
                flee();
            }
        } else {
            reduceSpeed();
        }
        
        calculateRotationChange();
        
        return this.destroyed;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        
        if (this.showHealthBar) {
            int healthBarWidth = this.width;

            for (int i = 0;i < healthBarWidth;i++) {
                g2d.drawImage(this.healthbar[0], (int)(this.posX + i), (int)(this.posY + this.height + 10),null);
            }

            healthBarWidth = (int)(healthBarWidth * (this.getPercentOfHealth() / 100));

            for (int i = 0;i < healthBarWidth;i++) {
                g2d.drawImage(this.healthbar[1], (int)(this.posX + i), (int)(this.posY + this.height + 10),null);
            }   
        }
        
        if (this.showName) {
            Color color = g2d.getColor();

            if (this.fraction == Ship.ALLIED) {
                g2d.setColor(Color.GREEN);
            } else if (this.fraction == Ship.ENEMY) {
                g2d.setColor(Color.RED);
            }

            g2d.drawString(this.name, (int)this.posX, (int)this.posY + this.height + 30);

            g2d.setColor(color);
        }
    }
    
    private void directTargetAttack() {
        manageRotation(this.currentTarget.getMiddleX(),this.currentTarget.getMiddleY());
        manageThrustTo(this.currentTarget.getMiddleX(),this.currentTarget.getMiddleY(),this.currentTarget.getForce());
        
        if (true) {
            shoot();
        }
    }
    
    private void circleAroundTarget(double distance) {
        Vector2D targetVector = new Vector2D(this.middleX,this.middleY,this.currentTarget.getMiddleX(),this.currentTarget.getMiddleY());
        boolean leftOf = this.force.isLeftOf(targetVector);
        
        manageRotation(this.currentTarget.getMiddleX(),this.currentTarget.getMiddleY());
        
        if (true) {
            shoot();
        }
        
        if (leftOf) {
            targetVector = targetVector.getTurnedRightVektor();
        } else {
            targetVector = targetVector.getTurnedLeftVektor();
        }
        
        targetVector = targetVector.getUnitVector().multiplyWithNumber(this.maxSpeed / 2);
        Vector2D nextDistanceToTarget = new Vector2D(this.middleX + targetVector.getX(),this.middleY + targetVector.getY(),this.currentTarget.getMiddleX(),this.currentTarget.getMiddleY());
        
        if (nextDistanceToTarget.getLength() > distance || nextDistanceToTarget.getLength() < distance) {
            nextDistanceToTarget = nextDistanceToTarget.getUnitVector().multiplyWithNumber(nextDistanceToTarget.getLength() - distance);
            targetVector = targetVector.addVector(nextDistanceToTarget);
        }
        
        manageThrustTo(this.middleX + targetVector.getX(),this.middleY + targetVector.getY(),this.currentTarget.getForce());
    }
    
    private void flee() {
        Vector2D targetVector = new Vector2D(this.middleX,this.middleY,this.currentTarget.getMiddleX(),this.currentTarget.getMiddleY()).multiplyWithNumber(-1).getUnitVector().multiplyWithNumber(this.maxSpeed);
        manageRotation(this.middleX + targetVector.getX(),this.middleY + targetVector.getY());
        manageThrustTo(this.middleX + targetVector.getX(),this.middleY + targetVector.getY(),new Vector2D(0,0));
    }
    
    public void shoot() {
        if (targetInLineOfFire()) {
            this.activeSlot = 0;
        } else if (this.turretSlot.length > 0) {
            this.activeSlot = this.weaponSlots.length;
        }
        
        if (this.activeSlot < this.weaponSlots.length) {
            for (int i = 0;i < this.weaponSlots[this.activeSlot].length;i++) {
                if (this.weaponSlots[this.activeSlot][i] != null) {
                    this.weaponSlots[this.activeSlot][i].shoot(100);
                }
            }      
        } else if (this.activeSlot < this.weaponSlots.length + this.turretSlot.length) {
            for (int i = 0;i < this.turretSlot[this.activeSlot - this.weaponSlots.length].length;i++) {
                if (this.turretSlot[this.activeSlot - this.weaponSlots.length][i] != null) {
                    this.turretSlot[this.activeSlot - this.weaponSlots.length][i].shoot(100);
                }
            }
        }
    }
    
    @Override
    public void doDamage(double damage) {
        super.doDamage(damage);
        
        this.showHealthBar = true;
        this.healthBarShowedSince = System.currentTimeMillis();
        
        if (this.health <= 0 && this.destroyed == false) {
            ItemDrop itemDrop = null;
            int Wtype = 0;
            int Ttype = 0;
            int ThrusterType = 0;
            boolean weapon = false;
            boolean turret = false;

            for (int i = 0;i < this.weaponSlots.length;i++) {
                if (this.weaponSlots[i][0] != null) {
                    Wtype = this.weaponSlots[i][0].getWeaponType();
                    weapon = true;

                    int tryNext = 0 + (int)(Math.random() * ((1000 - 0) + 1));

                    if (tryNext < 500) {
                        break;
                    }
                }
            }

            for (int i = 0;i < this.turretSlot.length;i++) {
                if (this.turretSlot[i][0] != null) {
                    Ttype = this.turretSlot[i][0].getWeaponType();
                    turret = true;

                    int tryNext = 0 + (int)(Math.random() * ((1000 - 0) + 1));

                    if (tryNext < 500) {
                        break;
                    }
                }
            }

            ThrusterType = 0 + (int)(Math.random() * ((400 - 0) + 1));

            if (ThrusterType <= 100) {
                if (this.thrusterForward[0] != null) {
                    ThrusterType = this.thrusterForward[0].getType();
                } else {
                    ThrusterType = Thruster.STANDART;
                }
            } else if (ThrusterType > 100 && ThrusterType <= 200) {
                if (this.thrusterFrontLeft[0] != null) {
                    ThrusterType = this.thrusterFrontLeft[0].getType();
                } else {
                    ThrusterType = Thruster.STANDART;
                }
            } else if (ThrusterType > 200 && ThrusterType <= 300) {
                if (this.thrusterFrontRight[0] != null) {
                    ThrusterType = this.thrusterFrontRight[0].getType();
                } else {
                    ThrusterType = Thruster.STANDART;
                }
            } else if (ThrusterType > 300) {
                if (this.thrusterBackward[0] != null) {
                    ThrusterType = this.thrusterBackward[0].getType();
                } else {
                    ThrusterType = Thruster.STANDART;
                }
            }

            itemDrop = ItemBuilder.getRandomItemDrop(Wtype, Ttype, ThrusterType, weapon, turret, this.preStrengthClassification);
            
            MainLoop.explosions.add(this.explosion.getRenewedExplosion(this.width, this.height, this.middleX, this.middleY,new ItemDrop(itemDrop.getInventory(),this.middleX,this.middleY)));
            this.destroyed = true;
        }
    }
    
    public int getPreStrengthClassification() {
        return this.preStrengthClassification;
    }
    
    private void getTarget() {
        AiShip aiShip = null;
        double shortestTargetDistance = -1;
        
        this.currentTarget = null;
        
        for (int i = 0;i < MainLoop.aiShips.size();i++) {
            AiShip dummy = (AiShip)MainLoop.aiShips.getElementAt(i);
            
            if (dummy.fraction != this.fraction) {
                double length = new Vector2D(this.middleX,this.middleY,dummy.middleX,dummy.middleY).getLength();

                if (length < this.range && length < shortestTargetDistance) {
                    aiShip = dummy;
                    shortestTargetDistance = length;
                } else if (length < this.range && shortestTargetDistance == -1) {
                    aiShip = dummy;
                    shortestTargetDistance = length;
                }
            }
        }
        
        this.currentTarget = aiShip;
        
        if (this.currentTarget == null && MainLoop.playerShip.fraction != this.fraction && playerShipInRange()) {
            this.currentTarget = MainLoop.playerShip;
        }
    }
    
    private boolean isInRange(Ship ship) {
        boolean inRange = false;
        
        if (ship != null && !ship.destroyed && new Vector2D(this.middleX,this.middleY,ship.middleX,ship.middleY).getLength() <= this.range) {
            inRange = true;
        }
        
        return inRange;
    }
    
    private boolean playerShipInRange() {
        boolean inRange = false;
        
        if (new Vector2D(this.middleX,this.middleY,MainLoop.playerShip.getMiddleX(),MainLoop.playerShip.getMiddleY()).getLength() <= this.range) {
            inRange = true;
        }
        
        return inRange;
    }
    
    private double distanceToTarget() {
        double distance = 0;
        
        if (this.currentTarget != null) {
            distance = (double)new Vector2D(this.middleX,this.middleY,this.currentTarget.middleX,this.currentTarget.middleY).getLength();
        }
        
        return distance;
    }
    
    private boolean targetInLineOfFire() {
        boolean inLineOfFire = false;
        
        if (this.currentTarget != null) {
            if (new Vector2D(this.middleX,this.middleY,this.currentTarget.middleX,this.currentTarget.middleY).getAngle(this.direction) <= 30) {
                inLineOfFire = true;
            }
        }
        
        return inLineOfFire;
    }
    
    private boolean coordsMostlyInShip(double coordX, double coordY) {
        boolean inShip = false;
        
        if (coordX >= this.posX && coordX <= this.posX + this.width) {
            if (coordY >= this.posY && coordY <= this.posY + this.height) {
                inShip = true;
            }
        }
        
        return inShip;
    }
}
