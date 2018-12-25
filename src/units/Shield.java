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
import support.FixedCollisionBox;
import support.Resources;
import utils.UnitConverter;
import utils.Utils;
import utils.Vector2D;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Shield implements Cargo {
    private BufferedImage[] shieldImageFront;
    private BufferedImage[] shieldImageSides;
    
    private BufferedImage icon;
    private String name;
    
    private int width;
    private int height;
    
    private double middleX;
    private double middleY;
    
    private double rotation;
    private Vector2D direction;
    
    private FixedCollisionBox collisionBox;
    
    private boolean[] drawShield;
    
    private long[] startTime;
    private long viewTime;
    
    Reactor[] reactors;
    Battery[] batteries;
    
    private boolean shiftEnergy;
    private int shiftEnergyTarget;
    
    public Shield(double middleX, double middleY, int width, int height, Reactor reactor, Battery battery) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        //5 pixel abstand vom schiff auf jeder seite (einfach ein random wert)
        width += 10;
        height += 10;
        
        this.width = width;
        this.height = height;
        
        this.rotation = 0;
        this.direction = new Vector2D(0,-1);
        
        //Verhältnis 4:1 von weite:höhe
        height = width / 4;
        
        this.shieldImageFront = MainLoop.resources.getScaledBufferedImageField(Resources.SHIELD_STANDART, width, height,Resources.SCALE_SMOOTH);
        
        width = this.height;
        height = width / 4;
        
        this.shieldImageSides = MainLoop.resources.getScaledBufferedImageField(Resources.SHIELD_STANDART, width, height,Resources.SCALE_SMOOTH);
        
        collisionBox = new FixedCollisionBox(this.middleX,this.middleY,this.width + height,this.height + (this.width / 4));
        
        icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_SHIELD, 50, 50,Resources.SCALE_SMOOTH)[0];
        name = StringSelector.getSring(StringSelector.SHIELD_NAME);
        
        this.drawShield = new boolean[4];
        this.startTime = new long[4];
        
        drawShield[0] = false;  //Top
        drawShield[1] = false;  //Right
        drawShield[2] = false;  //Bottom
        drawShield[3] = false;  //Left
        
        startTime[0] = System.currentTimeMillis();
        startTime[1] = System.currentTimeMillis();
        startTime[2] = System.currentTimeMillis();
        startTime[3] = System.currentTimeMillis();
        
        this.viewTime = 400;
        
        this.reactors = new Reactor[4];
        this.batteries = new Battery[4];
        
        this.reactors[0] = new Reactor(reactor);
        this.reactors[1] = new Reactor(reactor);
        this.reactors[2] = new Reactor(reactor);
        this.reactors[3] = new Reactor(reactor);
        
        this.batteries[0] = new Battery(battery.getCapacity() / 4);
        this.batteries[1] = new Battery(battery.getCapacity() / 4);
        this.batteries[2] = new Battery(battery.getCapacity() / 4);
        this.batteries[3] = new Battery(battery.getCapacity() / 4);
        
        this.shiftEnergy = false;
        this.shiftEnergyTarget = 0;
    }
    
    public void update(double middleX, double middleY, double rotation) {
        this.middleX = middleX;
        this.middleY = middleY;
        this.rotation = rotation;
        this.direction = new Vector2D(0,-1).getRotatedVector(rotation);
        
        this.collisionBox.updateBox(middleX, middleY);
        this.collisionBox.setRotation(rotation);
        
        for (int i = 0;i < batteries.length;i++) {
            boolean wasNull = false;
            if (this.batteries[i].getAvailableEnergy() == 0) {
                wasNull = true;
            }
            
            this.batteries[i].energyCharge(this.reactors[i].getEnergyOuput());
            
            if (wasNull && this.batteries[i].getAvailableEnergy() > 0) {
                this.drawShield[i] = true;
                this.startTime[i] = System.currentTimeMillis();
            }
        }
        
        if (this.shiftEnergy) {
            double amount = (this.batteries[this.shiftEnergyTarget].getCapacity() / 100) / 3; //1% von der kapazität durch 3 (aufgeteilt auf die 3 anderen reaktoren): 1% energie zuwachs pro durchlauf
            double realAmount = 0;
            
            if (this.batteries[this.shiftEnergyTarget].getAvailableEnergy() == this.batteries[this.shiftEnergyTarget].getCapacity()) {
                this.shiftEnergy = false;
            } else {
                for (int i = 0;i < this.batteries.length;i++) {
                    this.reactors[i].reactorDrain();
                    if (i == this.shiftEnergyTarget) {
                        continue;
                    }

                    realAmount += this.batteries[i].energyLoss(amount);
                }

                this.batteries[this.shiftEnergyTarget].energyCharge(realAmount);
            }
        }
    }
    
    public void draw(Graphics2D g2d) {
        draw(g2d,new Vector2D(0,0));
    }
    
    public void draw(Graphics2D g2d, Vector2D force) {
        int offsetX;
        int offsetY;
        
        if (this.drawShield[0]) {
            offsetX = this.width / 2;
            offsetY = (this.height / 2) + (this.width / 4);
            
            g2d.drawImage(this.shieldImageFront[0], (int)(Background.X + this.middleX - offsetX - force.getX()), (int)(Background.Y + this.middleY - offsetY - force.getY()),null);
            
            if (System.currentTimeMillis() - this.startTime[0] >= this.viewTime) {
                this.drawShield[0] = false;
            }
        }
        
        if (this.drawShield[2]) {
            offsetX = this.width / 2;
            offsetY = (this.height / 2) + (this.width / 4);
            
            g2d.rotate(UnitConverter.degreeToRadiant(180),Background.X + this.middleX,Background.Y + this.middleY);
            g2d.drawImage(this.shieldImageFront[0], (int)(Background.X + this.middleX - offsetX  - force.getX()), (int)(Background.Y + this.middleY - offsetY - force.getY()),null);
            g2d.rotate(-UnitConverter.degreeToRadiant(180),Background.X + this.middleX,Background.Y + this.middleY);
            
            if (System.currentTimeMillis() - this.startTime[2] >= this.viewTime) {
                this.drawShield[2] = false;
            }
        }
        
        if (this.drawShield[1]) {
            offsetX = (this.height / 2);
            offsetY = (this.width / 2) + (this.height / 4);
            
            g2d.rotate(UnitConverter.degreeToRadiant(90),Background.X + this.middleX,Background.Y + this.middleY);
            g2d.drawImage(this.shieldImageSides[0], (int)(Background.X + this.middleX - offsetX  - force.getX()), (int)(Background.Y + this.middleY - offsetY - force.getY()),null);
            g2d.rotate(-UnitConverter.degreeToRadiant(90),Background.X + this.middleX,Background.Y + this.middleY);
            
            if (System.currentTimeMillis() - this.startTime[1] >= this.viewTime) {
                this.drawShield[1] = false;
            }
        }
        
        if (this.drawShield[3]) {
            offsetX = (this.height / 2);
            offsetY = (this.width / 2) + (this.height / 4);
            
            g2d.rotate(-UnitConverter.degreeToRadiant(90),Background.X + this.middleX,Background.Y + this.middleY);
            g2d.drawImage(this.shieldImageSides[0], (int)(Background.X + this.middleX - offsetX  - force.getX()), (int)(Background.Y + this.middleY - offsetY - force.getY()),null);
            g2d.rotate(UnitConverter.degreeToRadiant(90),Background.X + this.middleX,Background.Y + this.middleY);
            
            if (System.currentTimeMillis() - this.startTime[3] >= this.viewTime) {
                this.drawShield[3] = false;
            }
        }
    }
    
    public boolean isHit(Bullet bullet,FixedCollisionBox b) {
        if (this.collisionBox.isInRange(b)) {
            if (!this.collisionBox.isOverlapping(b)) {
                return false;
            }
        } else {
            return false;
        }
        
        Vector2D hit = new Vector2D(this.middleX,this.middleY,b.getMiddleX(),b.getMiddleY());
        
        if (bullet.getShootType() == WeaponBase.RAY) {
            hit = hit.getTurnedLeftVektor().getTurnedLeftVektor();
        }
        
        boolean shieldStoppedBullet = true;
        double hitRotation = this.direction.getAngle(hit);
        
        if (hit.isLeftOf(this.direction)) {
            hitRotation = 360 - hitRotation;
        }
        
        if (hitRotation < 315) {
            if (hitRotation < 225) {
                if (hitRotation < 135) {
                    if (hitRotation < 45) {
                        this.batteries[0].energyLoss(bullet.getDamage());
                        this.reactors[0].reactorDrain();
                        
                        if (this.batteries[0].getAvailableEnergy() == 0) {
                            shieldStoppedBullet = false;
                            this.drawShield[0] = false;
                        } else {
                            this.drawShield[0] = true;
                            this.startTime[0] = System.currentTimeMillis();
                        }
                    } else {
                        this.batteries[1].energyLoss(bullet.getDamage());
                        this.reactors[1].reactorDrain();
                        
                        if (this.batteries[1].getAvailableEnergy() == 0) {
                            shieldStoppedBullet = false;
                            this.drawShield[1] = false;
                        } else {
                            this.drawShield[1] = true;
                            this.startTime[1] = System.currentTimeMillis();
                        }
                    }
                } else {
                    this.batteries[2].energyLoss(bullet.getDamage());
                    this.reactors[2].reactorDrain();

                    if (this.batteries[2].getAvailableEnergy() == 0) {
                        shieldStoppedBullet = false;
                        this.drawShield[2] = false;
                    } else {
                        this.drawShield[2] = true;
                        this.startTime[2] = System.currentTimeMillis();
                    }
                }
            } else {
                this.batteries[3].energyLoss(bullet.getDamage());
                this.reactors[3].reactorDrain();

                if (this.batteries[3].getAvailableEnergy() == 0) {
                    shieldStoppedBullet = false;
                    this.drawShield[3] = false;
                } else {
                    this.drawShield[3] = true;
                    this.startTime[3] = System.currentTimeMillis();
                }
            }
        } else {
            this.batteries[0].energyLoss(bullet.getDamage());
            this.reactors[0].reactorDrain();

            if (this.batteries[0].getAvailableEnergy() == 0) {
                shieldStoppedBullet = false;
                this.drawShield[0] = false;
            } else {
                this.drawShield[0] = true;
                this.startTime[0] = System.currentTimeMillis();
            }
        }
        
        return shieldStoppedBullet;
    }
    
    public void shiftEnergyTo(String which) {
        switch (which) {
            case "Left":
                this.shiftEnergy = true;
                this.shiftEnergyTarget = 3;
                break;
            case "Bottom":
                this.shiftEnergy = true;
                this.shiftEnergyTarget = 2;
                break;
            case "Right":
                this.shiftEnergy = true;
                this.shiftEnergyTarget = 1;
                break;
            case "Top":
                this.shiftEnergy = true;
                this.shiftEnergyTarget = 0;
                break;
        }
    }
    
    public FixedCollisionBox getCollisionBox() {
        return this.collisionBox;
    }
    
    public double getCapacity() {
        return this.batteries[0].getCapacity();
    }
    
    public double getEnergy(String which) {
        double energy = 0;
        
        switch (which) {
            case "Left":
                energy = this.batteries[3].getAvailableEnergy();
                break;
            case "Bottom":
                energy = this.batteries[2].getAvailableEnergy();
                break;
            case "Right":
                energy = this.batteries[1].getAvailableEnergy();
                break;
            case "Top":
                energy = this.batteries[0].getAvailableEnergy();
                break;
            case "All":
                energy = this.batteries[0].getAvailableEnergy() + this.batteries[1].getAvailableEnergy() + this.batteries[2].getAvailableEnergy() + this.batteries[3].getAvailableEnergy();
                break;
        }
        
        return energy;
    }
    
    public Reactor getReactor() {
        return new Reactor(this.reactors[0].getEnergyOutputValue() * 4,this.reactors[0].getReactorOutputDelay());
    }
    
    public Battery getBattery() {
        return new Battery(this.batteries[0].getCapacity() * 4);
    }
    
    @Override
    public BufferedImage getIcon() {
        return this.icon;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    public String getTooltip() {
        int leftPartLength = 22;
        String returnValue = "";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.SHIELD_STRENGTH), leftPartLength);
        returnValue += (this.batteries[0].getCapacity()) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBATTERYUNIT) + "\n";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.SHIELD_RELOAD), leftPartLength);
        returnValue += (this.reactors[0].getEnergyOutputValue()) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPREACTORUNIT) + "\n";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.SHIELD_RELOAD_DELAY), leftPartLength);
        returnValue += (this.reactors[0].getReactorOutputDelay()) + " " + StringSelector.getSring(StringSelector.RELOAD_TIME_UNIT) + "\n";
        
        return returnValue;
    }
}
