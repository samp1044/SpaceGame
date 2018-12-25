/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import units.Battery;
import units.Bullet;
import units.ItemDrop;
import units.Reactor;
import units.Shield;
import units.Ship;
import units.Thruster;
import units.Turret;
import units.Weapon;
import units.WeaponBase;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class ItemBuilder {
    public static final int STRONG = 1;
    public static final int MEDIUM = 2;
    public static final int WEAK = 3;
    
    public static ItemDrop getRandomItemDrop(int Wtype, int Ttype, int ThrusterType, boolean weapon, boolean turret, int attribute) {
        ItemDrop itemDrop = null;
        int positions;
        Class[] alreadyUsed = new Class[5];
        
        positions = 1 + (int)(Math.random() * ((6 - 1) + 1));
        
        itemDrop = new ItemDrop(new Inventory(positions,64),0,0);
        
        for (int i = 0;i < positions;i++) {
            int item;
            boolean positionDone = false;
            
            do {
                item = 0 + (int)(Math.random() * ((900 - 0) + 1));

                if (item <= 300) {
                    boolean usedAlready = false;
                    Object generatedItem = getRandomItem(ThrusterType, attribute);
                    
                    if (!(generatedItem instanceof Thruster)) {
                        for (int j = 0;j < alreadyUsed.length;j++) {
                            if (alreadyUsed[j] != null && alreadyUsed[j].isInstance(generatedItem)) {
                                usedAlready = true;
                                break;
                            }
                        }   
                    }

                    if (!usedAlready) {
                        itemDrop.addItems(i, new Object[]{generatedItem});
                        positionDone = true;
                        
                        if (!(generatedItem instanceof Thruster)) {
                            for (int j = 0;j < alreadyUsed.length;j++) {
                                if (alreadyUsed[j] == null) {
                                    alreadyUsed[j] = generatedItem.getClass();
                                    break;
                                }
                            }
                        }
                    }
                } else if (item > 300 && item <= 600 && weapon == true) {
                    boolean usedAlready = false;
                    
                    for (int j = 0;j < alreadyUsed.length;j++) {
                        if (alreadyUsed[j] != null && alreadyUsed[j] == Weapon.class) {
                            usedAlready = true;
                            break;
                        }
                    }
                    
                    if (!usedAlready) {
                        Object generatedItem = getRandomWeapon(Wtype, attribute);
                        itemDrop.addItems(i, new Object[]{generatedItem});
                        positionDone = true;
                        
                        for (int j = 0;j < alreadyUsed.length;j++) {
                            if (alreadyUsed[j] == null) {
                                alreadyUsed[j] = generatedItem.getClass();
                                break;
                            }
                        }
                    }
                } else if (item > 600 && item <= 900 && turret == true) {
                    boolean usedAlready = false;
                    
                    for (int j = 0;j < alreadyUsed.length;j++) {
                        if (alreadyUsed[j] != null && alreadyUsed[j] == Turret.class) {
                            usedAlready = true;
                            break;
                        }
                    }
                    
                    if (!usedAlready) {
                        Object generatedItem = getRandomTurret(Ttype, attribute);
                        itemDrop.addItems(i, new Object[]{generatedItem});
                        positionDone = true;
                        
                        for (int j = 0;j < alreadyUsed.length;j++) {
                            if (alreadyUsed[j] == null) {
                                alreadyUsed[j] = generatedItem.getClass();
                                break;
                            }
                        }
                    }
                }
            } while (!positionDone);
        }
        
        return itemDrop;
    }
    
    public static Object getRandomWeapon(int Wtype,int Ttype, int attribute) {
        Object weapon = null;
        int which = 0 + (int)(Math.random() * ((400 - 0) + 1));
        
        if (which < 200) {
            weapon = getRandomWeapon(Wtype, attribute);
        } else {
            weapon = getRandomTurret(Ttype, attribute);
        }
        
        return weapon;
    }
    
    public static Object getRandomItem(int thrusterType, int attribute) {
        Object item = null;
        
        int which = 0 + (int)(Math.random() * ((400 - 0) + 1));
        
        if (which <= 100) {
            item = getRandomThruster(thrusterType,attribute);
        } else if (which > 100 && which <= 200) {
            item = getRandomShield(attribute);
        } else if (which > 200 && which <= 300) {
            item = getRandomReactor(attribute);
        } else if (which > 300 && which <= 400) {
            item = getRandomBattery(attribute);
        }
        
        return item;
    }
    
    public static Thruster getRandomThruster(int type,int attribute) {
        Thruster thruster = null;
        double thrustStrength = 0;
        
        switch (attribute) {
            case STRONG:
                thrustStrength = 10000 + (int)(Math.random() * ((50000 - 10000) + 1));
                break;
            case MEDIUM:
                thrustStrength = 50000 + (int)(Math.random() * ((100000 - 50000) + 1));
                break;
            case WEAK:
                thrustStrength = 100000 + (int)(Math.random() * ((200000 - 100000) + 1));
                break;
            default:
                thrustStrength = 10000 + (int)(Math.random() * ((200000 - 10000) + 1));
        }
        
        thruster = new Thruster(type,40,40,40,40,thrustStrength,0,0,0,0,0,false);
        
        return thruster;
    }
    
    public static Shield getRandomShield(int attribute) {
        Shield shield = null;
        Reactor reactor = null;
        Battery battery = null;
        
        reactor = getRandomReactor(attribute);
        battery = getRandomBattery(attribute);
        
        shield = new Shield(0,0,100,100,reactor,battery);
        
        return shield;
    }
    
    public static Reactor getRandomReactor(int attribute) {
        Reactor reactor = null;
        int energyOutput = 0;
        int energyOutputDelay = 0;
        
        switch (attribute) {
            case STRONG:
                energyOutput = 70 + (int)(Math.random() * ((100 - 70) + 1));
                energyOutputDelay = 400 + (int)(Math.random() * ((500 - 400) + 1));
                break;
            case MEDIUM:
                energyOutput = 40 + (int)(Math.random() * ((70 - 40) + 1));
                energyOutputDelay = 200 + (int)(Math.random() * ((400 - 200) + 1));
                break;
            case WEAK:
                energyOutput = 10 + (int)(Math.random() * ((40 - 10) + 1));
                energyOutputDelay = 50 + (int)(Math.random() * ((200 - 50) + 1));
                break;
            default:
                energyOutput = 10 + (int)(Math.random() * ((100 - 10) + 1));
                energyOutputDelay = 50 + (int)(Math.random() * ((500 - 50) + 1));
        }
        
        reactor = new Reactor(energyOutput,energyOutputDelay);
        
        return reactor;
    }
    
    public static Battery getRandomBattery(int attribute) {
        Battery battery = null;
        int capacity = 0;
        
        switch (attribute) {
            case STRONG:
                capacity = 700 + (int)(Math.random() * ((1000 - 700) + 1));
                break;
            case MEDIUM:
                capacity = 350 + (int)(Math.random() * ((700 - 350) + 1));
                break;
            case WEAK:
                capacity = 50 + (int)(Math.random() * ((350 - 50) + 1));
                break;
            default:
                capacity = 50 + (int)(Math.random() * ((1000 - 50) + 1));
        }
        
        battery = new Battery(capacity);
        
        return battery;
    }
    
    public static Weapon getRandomWeapon(int type, int attribute) {
        Weapon weapon = null;
        WeaponBase base = getRandomWeaponBase(type, attribute);
        
        weapon = new Weapon(type,100,100,0,0,0,0,0,base.getCoolDown(),base.getShootType(),base.getEnergyCost(),base.getSide(),false,base.getBullet());
        
        return weapon;
    }
    
    public static Turret getRandomTurret(int type, int attribute) {
        Turret turret = null;
        WeaponBase base = getRandomWeaponBase(type, attribute);
        
        turret = new Turret(type,100,100,0,0,0,0,0,base.getCoolDown(),base.getShootType(),base.getEnergyCost(),base.getSide(),base.getBullet());
        
        return turret;
    }
    
    public static WeaponBase getRandomWeaponBase(int type, int attribute) {
        WeaponBase weapon = null;
        
        int coolDown;
        int shootType;
        int energyCost;
        int damage;
        
        int bulletType = 0;
        int bulletWidth = 0;
        int bulletHeight = 0;
        
        //Min + (int)(Math.random() * ((Min -  Min) + 1))
        
        /*shootType = (int)Math.round(Math.random() * 100);
        
        if (shootType <= 50) {
            shootType = WeaponBase.REPETIER;
            coolDown = 50 + (int)(Math.random() * ((400 - 50) + 1));
        } else {
            shootType = WeaponBase.RAY;
            coolDown = 0;
        }*/
        
        shootType = WeaponBase.REPETIER;
        coolDown = 50 + (int)(Math.random() * ((400 - 50) + 1));
        
        bulletType = (int)Math.round(Math.random() * 100);
        
        if (type == WeaponBase.LASER) {
            if (bulletType <= 50) {
                bulletType = Bullet.LASER_RED;
            } else {
                bulletType = Bullet.LASER_BLUE;
            }
        } else if (type == WeaponBase.PLASMA_LANCE) {
            bulletType = Bullet.PLASMA;
        }
        
        if (shootType == WeaponBase.RAY) {
            bulletHeight = 2000;
        }
        
        switch(attribute) {
            case STRONG:
                damage = 7 + (int)(Math.random() * ((10 - 7) + 1));
                energyCost = 7 + (int)(Math.random() * ((10 - 7) + 1));
                bulletWidth = 6;
                
                if (!(shootType == WeaponBase.RAY)) {
                    bulletHeight = 60;
                }
                break;
            case MEDIUM:
                damage = 4 + (int)(Math.random() * ((7 - 4) + 1));
                energyCost = 3 + (int)(Math.random() * ((7 - 3) + 1));
                bulletWidth = 4;
                
                if (!(shootType == WeaponBase.RAY)) {
                    bulletHeight = 45;
                }
                break;
            case WEAK:
                damage = 1 + (int)(Math.random() * ((4 - 1) + 1));
                energyCost = 1 + (int)(Math.random() * ((3 - 1) + 1));
                bulletWidth = 3;
                
                if (!(shootType == WeaponBase.RAY)) {
                    bulletHeight = 30;
                }
                break;
            default:
                damage = 1 + (int)(Math.random() * ((10 - 1) + 1));
                energyCost = 1 + (int)(Math.random() * ((10 - 1) + 1));
                bulletWidth = 3 + (int)(Math.random() * ((6 - 3) + 1));
                
                if (!(shootType == WeaponBase.RAY)) {
                    bulletHeight = 30 + (int)(Math.random() * ((60 - 30) + 1));
                }
        }
        
        if (type == WeaponBase.PLASMA_LANCE) {
            bulletWidth = 6;
            bulletHeight = 20;
        }
        
        weapon = new WeaponBase(0,100,100,0,0,0,0,0,coolDown,shootType,energyCost,0,new Bullet(bulletType,shootType,bulletWidth,bulletHeight,0,0,new Vector2D(0,-1),damage,Ship.ALLIED));
        
        return weapon;
    }
}
