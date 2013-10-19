/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.Image;
import java.awt.Toolkit;
import utils.Logger;

/**
 *
 * Unused
 */
public class Resources {
    public static final int BACKGROUND_STARS = 0;
    public static final int BACKGROUND_SECTORS = 1;
    
    public static final int SHIP_DUMMY = 2;
    
    public static final int THRUSTER_STANDART = 3;
    
    public static final int PARTICLE_DUMMY = 4;
    
    public static final int BULLET_LASER_RED = 5;
    
    public static final int WEAPON_LASER1 = 6;
    
    public static boolean loadingFinished = false;
    
    Image[] background_stars;
    Image[] background_sectors;
    
    Image[] ship_Dummy;
    
    Image[] thruster_Standart;
    
    Image[] particle_dummy;
    
    Image[] bullet_laserRed;
    
    Image[] weapon_laser1;
    
    Logger logger = new Logger(Resources.class);
    
    public void loadResources() {
        loadBackgrounds();
        loadShips();
        loadThrusters();
        loadParticles();
        loadBullets();
        loadWeapons();
        
        loadingFinished = true;
    }
    
    public Image[] getImageField(int field) {
        Image[] value = null;
        
        switch (field) {
            case BACKGROUND_STARS:
                value = this.background_stars;
                break;
            case BACKGROUND_SECTORS:
                value = this.background_sectors;
                break;
            case SHIP_DUMMY:
                value = this.ship_Dummy;
                break;
            case THRUSTER_STANDART:
                value = this.thruster_Standart;
                break;
            case PARTICLE_DUMMY:
                value = this.particle_dummy;
                break;
            case BULLET_LASER_RED:
                value = this.bullet_laserRed;
                break;
            case WEAPON_LASER1:
                value = this.weapon_laser1;
                break;
        }
        
        return value;
    }
    
    public Image[] getScaledImageField(int field,int width,int height) {
        Image[] value = getImageField(field);
        
        try {
            for (int i = 0;i < value.length;i++) {
                value[i] = value[i].getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
        } catch(Exception e) {
            logger.error("Fehler in getScaledImageField(..): "+e);
        }
        
        return value;
    }
    
    private void loadBackgrounds() {
        this.background_stars = new Image[1];
        this.background_stars[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/backgrounds/stars.png"));
        
        this.background_sectors = new Image[1];
        for (int i = 0;i < this.background_sectors.length;i++) {
            this.background_sectors[i] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/backgrounds/sector"+i+".png"));
        }
    }
    
    private void loadShips() {
        this.ship_Dummy = new Image[1];
        this.ship_Dummy[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/ships/shipDummy.png"));
        this.ship_Dummy[0] = ship_Dummy[0].getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    }
    
    private void loadThrusters() {
        this.thruster_Standart = new Image[4];
        
        for (int i = 0;i < this.thruster_Standart.length;i++) {
            this.thruster_Standart[i] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/thrusters/thrustDummy"+i+".png"));
        }
    }
    
    private void loadParticles() {
        this.particle_dummy = new Image[1];
        this.particle_dummy[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/particles/particleDummy.png"));
    }
    
    private void loadBullets() {
        this.bullet_laserRed = new Image[1];
        this.bullet_laserRed[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/bullets/bulletLaserRed.png"));
    }
    
    private void loadWeapons() {
        this.weapon_laser1 = new Image[1];
        this.weapon_laser1[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/weapons/weaponLaser1.png"));
    }
}
