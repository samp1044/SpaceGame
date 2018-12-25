/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import static main.MainLoop.resources;
import units.AiShip;
import units.Battery;
import units.Bullet;
import units.Explosion;
import units.PlayerShip;
import units.Reactor;
import units.Shield;
import units.Ship;
import units.Thruster;
import units.Turret;
import units.Weapon;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class ShipBuilder {
    public static final int STRONG = 1;
    public static final int MEDIUM = 2;
    public static final int WEAK = 3;
    
    //PlayerShips
    public static PlayerShip DUMMYSHIP_BASE = new PlayerShip(Ship.DUMMY,50,50,(Settings.solutionWidth/2) - 25,(Settings.solutionHeight/2) - 25,100,resources,new Explosion(Explosion.STANDART_SMOOTH), Ship.ALLIED);
    public static PlayerShip DUMMYSHIP_CORVETTE = new PlayerShip(Ship.CORVETTE,100,180,(Settings.solutionWidth/2) - 50,(Settings.solutionHeight/2) - 90,100,resources,new Explosion(Explosion.STANDART_SMOOTH),Ship.ALLIED);
    public static PlayerShip ALIEN_HUNTER = new PlayerShip(Ship.ALIEN_HUNTER,300,150,(Settings.solutionWidth/2) - 50,(Settings.solutionHeight/2) - 90,100,resources,new Explosion(Explosion.STANDART_SMOOTH),Ship.ALLIED);
    public static PlayerShip ALIEN_HUNTERSMALL = new PlayerShip(Ship.ALIEN_HUNTER_SMALL,200,120,(int)(-Background.X) + (Settings.solutionWidth/2) - 50,(int)(-Background.Y) + (Settings.solutionHeight/2) - 90,100,resources,new Explosion(Explosion.STANDART_SMOOTH),Ship.ALLIED);
    
    //AiShips
    public static AiShip AI_DUMMYSHIP_BASE = new AiShip(Ship.DUMMY,50,50,100,100,100,resources,Ship.ENEMY,5000,new Explosion(Explosion.STANDART_SMOOTH),"Enemy AI",Ship.WEAK);
    public static AiShip AI_DUMMYSHIP_BASE2 = new AiShip(Ship.DUMMY,50,50,200,400,100,resources,Ship.ALLIED,1000,new Explosion(Explosion.STANDART_SMOOTH),"Allied AI",Ship.WEAK);
    public static AiShip AI_DUMMYSHIP_CORVETTE = new AiShip(Ship.CORVETTE,100,180,200,600,100,resources,Ship.ENEMY,5000,new Explosion(Explosion.STANDART_SMOOTH),"Enemy Corvette AI",Ship.STRONG);
    
    //Thrusters
    public static Thruster THRUSTER_STANDART_FAST = new Thruster(Thruster.STANDART,20,20,20,20,50000,0,0,0,0,0,false);
    public static Thruster THRUSTER_STANDART = new Thruster(Thruster.STANDART,20,20,20,20,10000,0,0,0,0,0,false);
    public static Thruster THRUSTER_STANDART_CORVETTE_FAST = new Thruster(Thruster.STANDART,20,20,20,20,200000,0,0,0,0,0,false);
    public static Thruster THRUSTER_STANDART_CORVETTE = new Thruster(Thruster.STANDART,20,20,20,20,100000,0,0,0,0,0,false);
    public static Thruster THRUSTER_ALIEN = new Thruster(Thruster.ALIEN,20,20,20,20,100000,0,0,0,0,0,false);
    
    //Weapons
    public static Weapon WEAPON_LASER1 = new Weapon(Weapon.LASER,100,100,0,0,0,0,0,200,Weapon.REPETIER,1,0,false,new Bullet(Bullet.LASER_RED,Weapon.REPETIER,3,30,0,0,new Vector2D(0,-1),1,Ship.ALLIED));
    public static Weapon WEAPON_LASER1_BIG = new Weapon(Weapon.LASER,100,100,0,0,0,0,0,200,Weapon.REPETIER,3,0,false,new Bullet(Bullet.LASER_RED,Weapon.REPETIER,6,60,0,0,new Vector2D(0,-1),5,Ship.ALLIED));
    public static Weapon WEAPON_PLASMA_LANCE = new Weapon(Weapon.PLASMA_LANCE,100,100,0,0,0,0,0,200,Weapon.REPETIER,1,0,false,new Bullet(Bullet.PLASMA,Weapon.REPETIER,6,20,0,0,new Vector2D(0,-1),100,Ship.ALLIED));
    
    public static Turret TURRET_DOUBLE_LASER = new Turret(Turret.LASER,100,100,0,0,0,0,0,50,Turret.RAY,2,0,new Bullet(Bullet.LASER_RED,Weapon.RAY,3,2000,0,0,new Vector2D(0,-1),3,Ship.ALLIED));
    
    //Reactors
    public static Reactor REACTOR_SMALL = new Reactor(100,500);
    public static Reactor REACTOR_TESTING = new Reactor(50,0);
    
    //Batteries
    public static Battery BATTERY_STANDART = new Battery(400);
    public static Battery BATTERY_BIG = new Battery(700);
    
    //Shields
    public static Shield SHIELD_BASIC = new Shield(0,0,100,100,REACTOR_SMALL,BATTERY_BIG);
    
    public static void initBaseShips() {
        //DummyShip:
        DUMMYSHIP_BASE.setForwardThruster(THRUSTER_STANDART_FAST);
        DUMMYSHIP_BASE.setBackwardThruster(THRUSTER_STANDART);
        DUMMYSHIP_BASE.setLeftThruster(THRUSTER_STANDART);
        DUMMYSHIP_BASE.setRightThruster(THRUSTER_STANDART);
        
        DUMMYSHIP_BASE.setRotationThrust(500); //Setzen der maximalen Rotiergeschwindigkeit des Schiffes
        
        DUMMYSHIP_BASE.setWeapon(WEAPON_LASER1, 0);
        
        DUMMYSHIP_BASE.setReactor(REACTOR_SMALL);
        DUMMYSHIP_BASE.setBattery(BATTERY_STANDART);
        DUMMYSHIP_BASE.setShield(SHIELD_BASIC);
        
        //Corvette
        DUMMYSHIP_CORVETTE.setForwardThruster(THRUSTER_STANDART_CORVETTE_FAST);
        DUMMYSHIP_CORVETTE.setBackwardThruster(THRUSTER_STANDART_CORVETTE);
        DUMMYSHIP_CORVETTE.setLeftThruster(THRUSTER_STANDART_CORVETTE);
        DUMMYSHIP_CORVETTE.setRightThruster(THRUSTER_STANDART_CORVETTE);
        
        DUMMYSHIP_CORVETTE.setRotationThrust(50);
        
        DUMMYSHIP_CORVETTE.setWeapon(WEAPON_LASER1_BIG,0);
        DUMMYSHIP_CORVETTE.setWeapon(TURRET_DOUBLE_LASER, 1);
        
        DUMMYSHIP_CORVETTE.setReactor(REACTOR_SMALL);
        DUMMYSHIP_CORVETTE.setBattery(BATTERY_STANDART);
        DUMMYSHIP_CORVETTE.setShield(SHIELD_BASIC);
        
        //Alien Hunter
        ALIEN_HUNTER.setRotationThrust(200);
        
        ALIEN_HUNTER.setForwardThruster(THRUSTER_ALIEN);
        ALIEN_HUNTER.setLeftThruster(THRUSTER_ALIEN);
        ALIEN_HUNTER.setRightThruster(THRUSTER_ALIEN);
        ALIEN_HUNTER.setBackwardThruster(THRUSTER_ALIEN);
        
        ALIEN_HUNTER.setWeapon(WEAPON_PLASMA_LANCE, 0);
        
        ALIEN_HUNTER.setBattery(BATTERY_STANDART);
        ALIEN_HUNTER.setReactor(REACTOR_TESTING);
        
        //Alien Hunter Small
        ALIEN_HUNTERSMALL.setRotationThrust(400);
        
        ALIEN_HUNTERSMALL.setForwardThruster(THRUSTER_ALIEN);
        ALIEN_HUNTERSMALL.setLeftThruster(THRUSTER_ALIEN);
        ALIEN_HUNTERSMALL.setRightThruster(THRUSTER_ALIEN);
        ALIEN_HUNTERSMALL.setBackwardThruster(THRUSTER_ALIEN);
        
        ALIEN_HUNTERSMALL.setWeapon(WEAPON_PLASMA_LANCE, 0);
        
        ALIEN_HUNTERSMALL.setBattery(BATTERY_STANDART);
        ALIEN_HUNTERSMALL.setReactor(REACTOR_TESTING);
        
        //AI
        AI_DUMMYSHIP_BASE.setForwardThruster(THRUSTER_STANDART_FAST);
        AI_DUMMYSHIP_BASE.setBackwardThruster(THRUSTER_STANDART_CORVETTE);
        AI_DUMMYSHIP_BASE.setLeftThruster(THRUSTER_STANDART_CORVETTE);
        AI_DUMMYSHIP_BASE.setRightThruster(THRUSTER_STANDART_CORVETTE);
        
        AI_DUMMYSHIP_BASE.setRotationThrust(100);
        
        AI_DUMMYSHIP_BASE.setWeapon(WEAPON_LASER1, 0);
        
        
        AI_DUMMYSHIP_BASE2.setForwardThruster(THRUSTER_STANDART_FAST);
        AI_DUMMYSHIP_BASE2.setBackwardThruster(THRUSTER_STANDART_CORVETTE);
        AI_DUMMYSHIP_BASE2.setLeftThruster(THRUSTER_STANDART_CORVETTE);
        AI_DUMMYSHIP_BASE2.setRightThruster(THRUSTER_STANDART_CORVETTE);
        
        AI_DUMMYSHIP_BASE2.setRotationThrust(100);
        
        AI_DUMMYSHIP_BASE2.setWeapon(WEAPON_LASER1, 0);
        
        
        AI_DUMMYSHIP_CORVETTE.setForwardThruster(THRUSTER_STANDART_FAST);
        AI_DUMMYSHIP_CORVETTE.setBackwardThruster(THRUSTER_STANDART_CORVETTE);
        AI_DUMMYSHIP_CORVETTE.setLeftThruster(THRUSTER_STANDART_CORVETTE);
        AI_DUMMYSHIP_CORVETTE.setRightThruster(THRUSTER_STANDART_CORVETTE);
        
        AI_DUMMYSHIP_CORVETTE.setRotationThrust(50);
        
        AI_DUMMYSHIP_CORVETTE.setWeapon(WEAPON_LASER1_BIG, 0);
        AI_DUMMYSHIP_CORVETTE.setWeapon(TURRET_DOUBLE_LASER, 1);
    }
    
    public static AiShip getRandomAiShip(int posX,int posY,int type,int fraction,int attribute) {
        AiShip aiShip = null;
        int width = 0;
        int height = 0;
        int health = 0;
        int range = 0;
        String name = "";
        Explosion explosion = null;
        int preStrengthClassification = 0;
        int itemBuildAttribute = 0;
        int thrusterType = 0;
        double rotationThrust = 100;
        
        switch(type) {
            case Ship.DUMMY:
                width = 50;
                height = 50;
                explosion = new Explosion(Explosion.STANDART_SMOOTH);
                rotationThrust = 500;
                
                thrusterType = Thruster.STANDART;
                break;
            case Ship.CORVETTE:
                width = 100;
                height = 180;
                explosion = new Explosion(Explosion.STANDART_SMOOTH);
                rotationThrust = 500;
                
                thrusterType = Thruster.STANDART;
                break;
            case Ship.ALIEN_HUNTER:
                width = 300;
                height = 150;
                explosion = new Explosion(Explosion.STANDART_SMOOTH);
                rotationThrust = 500;
                
                thrusterType = Thruster.ALIEN;
                break;
            case Ship.ALIEN_HUNTER_SMALL:
                width = 200;
                height = 120;
                explosion = new Explosion(Explosion.STANDART_SMOOTH);
                rotationThrust = 500;
                
                thrusterType = Thruster.ALIEN;
                break;
        }
        
        switch(attribute) {
            case STRONG:
                preStrengthClassification = Ship.STRONG;
                itemBuildAttribute = ItemBuilder.STRONG;
                
                health = 300 + (int)(Math.random() * ((500 - 300) + 1));
                break;
            case MEDIUM:
                preStrengthClassification = Ship.MEDIUM;
                itemBuildAttribute = ItemBuilder.MEDIUM;
                
                health = 150 + (int)(Math.random() * ((300 - 150) + 1));
                break;
            case WEAK:
                preStrengthClassification = Ship.WEAK;
                itemBuildAttribute = ItemBuilder.WEAK;
                
                health = 50 + (int)(Math.random() * ((150 - 50) + 1));
                break;
        }
        
        range = 800 + (int)(Math.random() * ((1200 - 800) + 1));
        
        aiShip = new AiShip(type,width,height,posX,posY,health,resources,fraction,range,explosion,name,preStrengthClassification);
        
        aiShip.setForwardThruster(ItemBuilder.getRandomThruster(thrusterType, itemBuildAttribute));
        aiShip.setBackwardThruster(ItemBuilder.getRandomThruster(thrusterType, itemBuildAttribute));
        aiShip.setLeftThruster(ItemBuilder.getRandomThruster(thrusterType, itemBuildAttribute));
        aiShip.setRightThruster(ItemBuilder.getRandomThruster(thrusterType, itemBuildAttribute));
        
        aiShip.setRotationThrust(rotationThrust);
        
        switch(type) {
            case Ship.DUMMY:
                aiShip.setWeapon(ItemBuilder.getRandomWeapon(Weapon.LASER, itemBuildAttribute), 0);
                break;
            case Ship.CORVETTE:
                aiShip.setWeapon(ItemBuilder.getRandomWeapon(Weapon.LASER, itemBuildAttribute), 0);
                aiShip.setWeapon(ItemBuilder.getRandomTurret(Turret.LASER, itemBuildAttribute), 1);
                break;
            case Ship.ALIEN_HUNTER:
                aiShip.setWeapon(ItemBuilder.getRandomWeapon(Weapon.PLASMA_LANCE, itemBuildAttribute), 0);
                break;
            case Ship.ALIEN_HUNTER_SMALL:
                aiShip.setWeapon(ItemBuilder.getRandomWeapon(Weapon.PLASMA_LANCE, itemBuildAttribute), 0);
                break;
        }
        
        aiShip.setShield(ItemBuilder.getRandomShield(itemBuildAttribute));
        
        return aiShip;
    }
}
