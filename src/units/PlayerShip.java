/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.event.MouseEvent;
import main.MainLoop;
import support.Background;
import support.FPSManager;
import support.FixedCollisionBox;
import support.Resources;
import support.Settings;
import utils.Vector2D;

public class PlayerShip extends Ship {
    public static int[] SLOTCONTENTS = new int[0];
    
    private double drawMiddleX;
    private double drawMiddleY;
    
    private boolean uiElementClicked;
    
    public PlayerShip(int typ,int width,int height,int x,int y,double health, Resources resources,Explosion explosion,int fraction) {
        super(typ, width, height, x, y, health, resources,explosion,fraction);
        
        this.drawMiddleX = x + Background.X;
        this.drawMiddleY = y + Background.Y;
        
        this.uiElementClicked = false;
        
        updateSlotContents();
    }
    
    public void updateShip(int mausX,int mausY,int canvasWidth,int canvasHeight) {
        Background.move(this.force.multiplyWithNumber(FPSManager.DELTA)); //Statt das schiff mit force zu bewegen wird der Background mit Force bewegt
        
        this.middleX = (-Background.X) + (canvasWidth / 2); //Die Realen X Koordinaten (mit dem Background abgestimmt und POSITIV, werden aktualisiert:
        this.middleY = (-Background.Y) + (canvasHeight / 2);//Background koordinate + hälfte der Fensterbreite bzw Fensterhöhe
        
        this.drawMiddleX = canvasWidth / 2;
        this.drawMiddleY = canvasHeight / 2;
        
        this.posX = this.drawMiddleX - (this.width / 2.0F);
        this.posY = this.drawMiddleY - (this.height / 2.0F);
        
        //Slot changing mechanics
        if (this.activeSlot != Settings.SLOT_VALUE) {
            this.setSlot(Settings.SLOT_VALUE);
        }
        
        if (Settings.INCREASE_SLOT) {
            this.increaseSlot();
            Settings.INCREASE_SLOT = false;
        }
        
        if (Settings.DECREASE_SLOT) {
            this.decreaseSlot();
            Settings.DECREASE_SLOT = false;
        }
        
        Settings.SLOT_VALUE = this.activeSlot;
        
        for (int i = 0;i < this.thrusterForward.length; i++) {
            if (thrusterForward[i] != null) {
                this.thrusterForward[i].updatePositionData(this.middleX,this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterBackward.length; i++) {
            if (thrusterBackward[i] != null) {
                this.thrusterBackward[i].updatePositionData(this.middleX,this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length; i++) {
            if (thrusterFrontLeft[i] != null) {
                this.thrusterFrontLeft[i].updatePositionData(this.middleX,this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length; i++) {
            if (thrusterBackLeft[i] != null) {
                this.thrusterBackLeft[i].updatePositionData(this.middleX,this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length; i++) {
            if (thrusterFrontRight[i] != null) {
                this.thrusterFrontRight[i].updatePositionData(this.middleX,this.middleY);
            }
        }
        
        for (int i = 0;i < this.thrusterBackRight.length; i++) {
            if (thrusterBackRight[i] != null) {
                this.thrusterBackRight[i].updatePositionData(this.middleX,this.middleY);
            }
        }
        
        for (int i = 0;i < this.weaponSlots.length;i++) {
            for (int j = 0;j < this.weaponSlots[i].length;j++) {
                if (weaponSlots[i][j] != null) {
                    this.weaponSlots[i][j].update(this.middleX, this.middleY,(int)(-Background.X + mausX),(int)(-Background.Y + mausY),this.rotationDegree);
                }
            }
        }
        
        for (int i = 0;i < this.turretSlot.length;i++) {
            for (int j = 0;j < this.turretSlot[i].length;j++) {
                if (this.turretSlot[i][j] != null) {
                    this.turretSlot[i][j].update(this.middleX, this.middleY,(int)(-Background.X + mausX),(int)(-Background.Y + mausY),this.rotationDegree);
                }
            }
        }
        
        if (!Settings.IGNORE_MOUSE_MOVEMENT) {
            manageRotation(-Background.X + mausX, -Background.Y + mausY);
        }
        
        calculateRotationChange();
        
        this.boxCollider.updateBox(this.middleX, this.middleY);
        this.boxCollider.setRotation(this.rotationDegree);
        
        //true && true
        //true && (true && true)
        
        if (Settings.SHOOT_Key_down && ((Settings.KeySHOOT == MouseEvent.BUTTON1 && MainLoop.ui_click_happened == false) || Settings.KeySHOOT != MouseEvent.BUTTON1)) {
            if (this.activeSlot < this.weaponSlots.length) {
                for (int i = 0;i < this.weaponSlots[this.activeSlot].length;i++) {
                    if (this.weaponBattery != null && this.weaponSlots[this.activeSlot][i] != null) {
                        double energyLoss = this.weaponSlots[this.activeSlot][i].shoot(this.weaponBattery.getAvailableEnergy());
                        this.weaponBattery.energyLoss(energyLoss);
                    }

                    if (this.reactor != null) {
                        reactor.reactorDrain();
                    }
                }
            } else if (this.activeSlot < this.weaponSlots.length + this.turretSlot.length) {
                for (int i = 0;i < this.turretSlot[this.activeSlot - this.weaponSlots.length].length;i++) {
                    if (this.weaponBattery != null && this.turretSlot[this.activeSlot - this.weaponSlots.length][i] != null) {
                        double energyLoss = this.turretSlot[this.activeSlot - this.weaponSlots.length][i].shoot(this.weaponBattery.getAvailableEnergy());
                        this.weaponBattery.energyLoss(energyLoss);
                    }

                    if (this.reactor != null) {
                        reactor.reactorDrain();
                    }
                }
            }
        }
        
        this.uiElementClicked = false;
        
        if (this.shield != null) {
            this.shield.update(this.middleX, this.middleY, this.rotationDegree);
        }
        
        if (this.weaponBattery != null && this.reactor != null) {
            this.weaponBattery.energyCharge(this.reactor.getEnergyOuput());
        }
        
        if (this.slotChanged) {
            updateSlotContents();
            this.slotChanged = false;
        }
    }
    
    private void updateSlotContents() {
        SLOTCONTENTS = new int[this.weaponSlots.length + this.turretSlot.length];
        
        for (int i = 0;i < weaponSlots.length;i++) {
            try {
                SLOTCONTENTS[i] = weaponSlots[i][0].getWeaponType();
            } catch (Exception e) {
                SLOTCONTENTS[i] = 0;
            }
        }
        
        for (int i = this.weaponSlots.length;i < this.weaponSlots.length + this.turretSlot.length;i++) {
            try {
                SLOTCONTENTS[i] = turretSlot[i - this.weaponSlots.length][0].getWeaponType() * (-1);
            } catch (Exception e) {
                SLOTCONTENTS[i] = 0;
            }
        }
    }
    
    @Override
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
    
    public void shieldRingClicked(String which) {
        if (this.shield != null) {
            this.shield.shiftEnergyTo(which);
        }
        this.uiElementClicked = true;
    }
    
    public double getDrawMiddleX() {
        return this.drawMiddleX;
    }
    
    public double getDrawMiddleY() {
        return this.drawMiddleY;
    }
    
    public void setDrawMiddleX(double drawMiddleX) {
        this.middleX = (-Background.X) + drawMiddleX;
    }
    
    public void setDrawMiddleY(double drawMiddleY) {
        this.middleY = (-Background.Y) + drawMiddleY;
    }
    
    public FixedCollisionBox getBox() {
        return this.boxCollider;
    }
    
    public double getEnergy() {
        if (this.weaponBattery != null) {
            return this.weaponBattery.getAvailableEnergy();
        } else {
            return 0;
        }
    }
    
    public double getEnergyCapacity() {
        if (this.weaponBattery != null) {
            return this.weaponBattery.getCapacity();
        } else {
            return 0;
        }
    }
    
    public double getShieldCapacity() {
        if (this.shield != null) {
            return this.shield.getCapacity();
        } else {
            return 0;
        }
    }
    
    public double getShieldCurrentEnergyStatus(String which) {
        double status = 0;
        
        if (this.shield != null) {
            status = this.shield.getEnergy(which);
        }
        
        return status;
    }
}