/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import utils.Logger;

/**
 *
 * Unused
 */
public class Resources {
    public static final int UI_TRANSFERBUTTON_ACTIVE = -20;
    public static final int UI_TRANSFERBUTTON_LINE = -19;
    public static final int UI_TRANSFERWINDOW = -18;
    public static final int UI_TOOLTIP_BACKGROUND = -17;
    public static final int UI_INVENTORY_BUTTONLINE_SELECTED = -16;
    public static final int UI_INVENTORY_BUTTONLINE = -15;
    public static final int UI_INVENTORY_SELECTIONBAR = -14;
    public static final int UI_INVENTORYSLOT_ACTIVE = -13;
    public static final int UI_INVENTORYSLOT = -12;
    public static final int UI_INVENTORYWINDOW = -11;
    public static final int UI_SHIELDRINGSHIPICONS = -10;
    public static final int UI_SHIELDRING = -9;
    public static final int UI_SLOT = -8;
    public static final int UI_SLOTACTIVE = -7;
    public static final int UI_WARNINGBAR = -6;
    public static final int UI_LEFTBAR = -5;
    public static final int UI_BARBACKGROUND = -4;
    public static final int UI_HEALTHBARGREEN = -3;
    public static final int UI_HEALTHBAR = -2;
    public static final int UI_ENERGYBAR = -1;
    
    public static final int BACKGROUND_STARS = 0;
    public static final int BACKGROUND_SECTORS = 1;
    
    public static final int SHIP_DUMMY = 2;
    public static final int SHIP_CORVETTE = 3;
    public static final int SHIP_ALIEN_HUNTER = 31;
    public static final int SHIP_ALIEN_HUNTER_SMALL = 32;
    
    public static final int THRUSTER_FLAME_YELLOW = 4;
    public static final int THRUSTER_FLAME_GREEN = 43;
    public static final int THRUSTER_STANDART = 45;
    public static final int THRUSTER_ALIEN = 46;
    
    public static final int PARTICLE_DUMMY = 5;
    public static final int PARTICLE_YELLOWTRANSPARENT = 6;
    
    public static final int BULLET_LASER_RED = 7;
    public static final int BULLET_LASER_RAY_BLUE = 8;
    public static final int BULLET_PLASMA = 39;
    
    public static final int WEAPON_LASER1 = 9;
    public static final int WEAPON_LASER1_SHOOTANI = 10;
    public static final int WEAPON_PLASMA_GUN = 33;
    public static final int WEAPON_PLASMA_GUN_SHOOTANI = 34;
    public static final int WEAPON_PLASMA_GUN_RIGHT = 35;
    public static final int WEAPON_PLASMA_GUN_SHOOTANI_RIGHT = 36;
    public static final int WEAPON_PLASMA_LANCE = 37;
    public static final int WEAPON_PLASMA_LANCE_SHOOTANI = 38;
    
    public static final int TURRET_DOUBLELASER1 = 11;
    public static final int TURRET_DOUBLELASER1_SHOOTANI = 12;
    
    public static final int SHIELD_STANDART = 13;
    
    public static final int LIGHTING_RED = 14;
    public static final int LIGHTING_YELLOW = 15;
    public static final int LIGHTING_BLUE = 16;
    public static final int LIGHTING_GREEN = 40;
    
    public static final int EXPLOSION_DUMMY = 17;
    public static final int EXPLOSION_STANDART = 18;
    public static final int EXPLOSION_STANDART_SMOOTH = 19;
    
    public static final int ICON_ORE = 30;
    public static final int ICON_WEAPONLASER1 = 20;
    public static final int ICON_WEAPONPLASMALANCE = 44;
    public static final int ICON_TURRETDOUBLELASER = 22;
    public static final int ICON_SHIP_DUMMY = 23;
    public static final int ICON_SHIP_CORVETTE = 24;
    public static final int ICON_SHIP_ALIENHUNTER = 41;
    public static final int ICON_SHIP_ALIENHUNTER_SMALL = 42;
    public static final int ICON_BATTERY = 25;
    public static final int ICON_REACTOR = 26;
    public static final int ICON_SHIELD = 27;
    public static final int ICON_THRUSTER_STANDART = 28;
    
    public static final int DROP = 29;
    
    public static final int SCALE_FAST = 2000;
    public static final int SCALE_SMOOTH = 2001;
    
    public static boolean loadingFinished = false;
    
    BufferedImage[] ui_transferButtonActive;
    BufferedImage[] ui_transferButtonLine;
    BufferedImage[] ui_transferWindow;
    BufferedImage[] ui_tooltip_background;
    BufferedImage[] ui_inventoryButtonLineSelected;
    BufferedImage[] ui_inventoryButtonLine;
    BufferedImage[] ui_inventorySelectionBar;
    BufferedImage[] ui_inventorySlotActive;
    BufferedImage[] ui_inventorySlot;
    BufferedImage[] ui_inventoryWindow;
    BufferedImage[] ui_shieldRingShipIcons;
    BufferedImage[] ui_shieldRing;
    BufferedImage[] ui_slot;
    BufferedImage[] ui_slotActive;
    BufferedImage[] ui_warningBar;
    BufferedImage[] ui_leftBar;
    BufferedImage[] ui_barbackground;
    BufferedImage[] ui_healthBarGreen;
    BufferedImage[] ui_healthBar;
    BufferedImage[] ui_energyBar;
    
    BufferedImage[] background_stars;
    BufferedImage[] background_sectors;
    
    BufferedImage[] ship_Dummy;
    BufferedImage[] ship_Corvette;
    BufferedImage[] ship_AlienHunter;
    BufferedImage[] ship_AlienHunterSmall;
    
    BufferedImage[] thruster_Flame_Yellow;
    BufferedImage[] thruster_Flame_Green;
    BufferedImage[] thruster_Standart;
    BufferedImage[] thruster_Alien;
    
    BufferedImage[] particle_dummy;
    BufferedImage[] particle_yellowTransparent;
    
    BufferedImage[] bullet_laserRed;
    BufferedImage[] bullet_laserRayBlue;
    BufferedImage[] bullet_plasma;
    
    BufferedImage[] weapon_laser1;
    BufferedImage[] weapon_laser1_shootAni;
    BufferedImage[] weapon_plasmaGun;
    BufferedImage[] weapon_plasmaGun_shootAni;
    BufferedImage[] weapon_plasmaGunRight;
    BufferedImage[] weapon_plasmaGun_shootAniRight;
    BufferedImage[] weapon_plasmaLance;
    BufferedImage[] weapon_plasmaLance_shootAni;
    
    BufferedImage[] turret_doublelaser1;
    BufferedImage[] turret_doublelaser1_shootAni;
    
    BufferedImage[] shield_standart;
    
    BufferedImage[] lighting_red;
    BufferedImage[] lighting_yellow;
    BufferedImage[] lighting_blue;
    BufferedImage[] lighting_green;
    
    BufferedImage[] explosion_dummy;
    BufferedImage[] explosion_standart_smooth;
    
    BufferedImage[] icon_ore;
    BufferedImage[] icon_turretDoubleLaser;
    BufferedImage[] icon_weaponLaser1;
    BufferedImage[] icon_weaponPlasmaLance;
    BufferedImage[] icon_ShipDummy;
    BufferedImage[] icon_ShipCorvette;
    BufferedImage[] icon_ShipAlienHunter;
    BufferedImage[] icon_ShipAlienHunterSmall;
    BufferedImage[] icon_Battery;
    BufferedImage[] icon_Reactor;
    BufferedImage[] icon_Shield;
    BufferedImage[] icon_Thruster_Standart;
    
    BufferedImage[] drop;
    
    Logger logger = new Logger(Resources.class);
    
    public void loadResources() {
        loadUiImages();
        loadBackgrounds();
        loadShips();
        loadThrusters();
        loadParticles();
        loadBullets();
        loadWeapons();
        loadTurrets();
        loadShields();
        loadLighting();
        loadExplosions();
        loadIcons();
        loadDrop();
        
        loadingFinished = true;
    }
    
    public BufferedImage[] getImageField(int field) {
        BufferedImage[] value = null;
        
        switch (field) {
            case UI_TRANSFERBUTTON_ACTIVE:
                value = this.ui_transferButtonActive;
                break;
            case UI_TRANSFERBUTTON_LINE:
                value = this.ui_transferButtonLine;
                break;
            case UI_TRANSFERWINDOW:
                value = this.ui_transferWindow;
                break;
            case UI_TOOLTIP_BACKGROUND:
                value = this.ui_tooltip_background;
                break;
            case UI_INVENTORY_BUTTONLINE_SELECTED:
                value = this.ui_inventoryButtonLineSelected;
                break;
            case UI_INVENTORY_BUTTONLINE:
                value = this.ui_inventoryButtonLine;
                break;
            case UI_INVENTORY_SELECTIONBAR:
                value = this.ui_inventorySelectionBar;
                break;
            case UI_INVENTORYSLOT_ACTIVE:
                value = this.ui_inventorySlotActive;
                break;
            case UI_INVENTORYSLOT:
                value = this.ui_inventorySlot;
                break;
            case UI_INVENTORYWINDOW:
                value = this.ui_inventoryWindow;
                break;
            case UI_SHIELDRINGSHIPICONS:
                value = this.ui_shieldRingShipIcons;
                break;
            case UI_SHIELDRING:
                value = this.ui_shieldRing;
                break;
            case UI_SLOT:
                value = this.ui_slot;
                break;
            case UI_SLOTACTIVE:
                value = this.ui_slotActive;
                break;
            case UI_WARNINGBAR:
                value = this.ui_warningBar;
                break;
            case UI_LEFTBAR:
                value = this.ui_leftBar;
                break;
            case UI_BARBACKGROUND:
                value = this.ui_barbackground;
                break;
            case UI_HEALTHBARGREEN:
                value = this.ui_healthBarGreen;
                break;
            case UI_HEALTHBAR:
                value = this.ui_healthBar;
                break;
            case UI_ENERGYBAR:
                value = this.ui_energyBar;
                break;
            case BACKGROUND_STARS:
                value = this.background_stars;
                break;
            case BACKGROUND_SECTORS:
                value = this.background_sectors;
                break;
            case SHIP_DUMMY:
                value = this.ship_Dummy;
                break;
            case SHIP_CORVETTE:
                value = this.ship_Corvette;
                break;
            case SHIP_ALIEN_HUNTER:
                value = this.ship_AlienHunter;
                break;
            case SHIP_ALIEN_HUNTER_SMALL:
                value = this.ship_AlienHunterSmall;
                break;
            case THRUSTER_FLAME_YELLOW:
                value = this.thruster_Flame_Yellow;
                break;
            case THRUSTER_FLAME_GREEN:
                value = this.thruster_Flame_Green;
                break;
            case THRUSTER_STANDART:
                value = this.thruster_Standart;
                break;
            case THRUSTER_ALIEN:
                value = this.thruster_Alien;
                break;
            case PARTICLE_DUMMY:
                value = this.particle_dummy;
                break;
            case PARTICLE_YELLOWTRANSPARENT:
                value = this.particle_yellowTransparent;
                break;
            case BULLET_LASER_RED:
                value = this.bullet_laserRed;
                break;
            case BULLET_LASER_RAY_BLUE:
                value = this.bullet_laserRayBlue;
                break;
            case BULLET_PLASMA:
                value = this.bullet_plasma;
                break;
            case WEAPON_LASER1:
                value = this.weapon_laser1;
                break;
            case WEAPON_LASER1_SHOOTANI:
                value = this.weapon_laser1_shootAni;
                break;
            case WEAPON_PLASMA_GUN:
                value = this.weapon_plasmaGun;
                break;
            case WEAPON_PLASMA_GUN_SHOOTANI:
                value = this.weapon_plasmaGun_shootAni;
                break;
            case WEAPON_PLASMA_GUN_RIGHT:
                value = this.weapon_plasmaGunRight;
                break;
            case WEAPON_PLASMA_GUN_SHOOTANI_RIGHT:
                value = this.weapon_plasmaGun_shootAniRight;
                break;
            case WEAPON_PLASMA_LANCE:
                value = this.weapon_plasmaLance;
                break;
            case WEAPON_PLASMA_LANCE_SHOOTANI:
                value = this.weapon_plasmaLance_shootAni;
                break;
            case TURRET_DOUBLELASER1:
                value = this.turret_doublelaser1;
                break;
            case TURRET_DOUBLELASER1_SHOOTANI:
                value = this.turret_doublelaser1_shootAni;
                break;
            case SHIELD_STANDART:
                value = this.shield_standart;
                break;
            case LIGHTING_RED:
                value = this.lighting_red;
                break;
            case LIGHTING_YELLOW:
                value = this.lighting_yellow;
                break;
            case LIGHTING_BLUE:
                value = this.lighting_blue;
                break;
            case LIGHTING_GREEN:
                value = this.lighting_green;
                break;
            case EXPLOSION_DUMMY:
                value = this.explosion_dummy;
                break;
            case EXPLOSION_STANDART_SMOOTH:
                value = this.explosion_standart_smooth;
                break;
            case ICON_ORE:
                value = this.icon_ore;
                break;
            case ICON_TURRETDOUBLELASER:
                value = this.icon_turretDoubleLaser;
                break;
            case ICON_WEAPONLASER1:
                value = this.icon_weaponLaser1;
                break;
            case ICON_WEAPONPLASMALANCE:
                value = this.icon_weaponPlasmaLance;
                break;
            case ICON_SHIP_DUMMY:
                value = this.icon_ShipDummy;
                break;
            case ICON_SHIP_CORVETTE:
                value = this.icon_ShipCorvette;
                break;
            case ICON_SHIP_ALIENHUNTER:
                value = this.icon_ShipAlienHunter;
                break;
            case ICON_SHIP_ALIENHUNTER_SMALL:
                value = this.icon_ShipAlienHunterSmall;
                break;
            case ICON_BATTERY:
                value = this.icon_Battery;
                break;
            case ICON_REACTOR:
                value = this.icon_Reactor;
                break;
            case ICON_SHIELD:
                value = this.icon_Shield;
                break;
            case ICON_THRUSTER_STANDART:
                value = this.icon_Thruster_Standart;
                break;
            case DROP:
                value = this.drop;
        }
        
        return value;
    }
    
    /**
     * Old and very slow -> Dont use!
     * @param field
     * @param width
     * @param height
     * @return 
     */
    public Image[] getScaledImageField(int field,int width,int height) {
        Image[] value = null;
        
        try {
            value = new Image[getImageField(field).length];
            
            for (int i = 0;i < value.length;i++) {
                value[i] = getImageField(field)[i].getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
        } catch(Exception e) {
            logger.error("Fehler in getScaledImageField("+field+","+width+","+height+"): "+e);
        }
        
        return value;
    }
    
    /**
     * working quite well, differs between fancy and fast scaling, might stay this way
     * @param field
     * @param width
     * @param height
     * @return 
     */
    public BufferedImage[] getScaledBufferedImageField(int field,int width,int height, int qualityHint) {
        BufferedImage[] images = null;
        BufferedImage[] scaledBufferedImages = null;
        
        try {
            images = getImageField(field);
            scaledBufferedImages = new BufferedImage[images.length];
            
            for (int i = 0;i < scaledBufferedImages.length;i++) {
                scaledBufferedImages[i] = getScaledBufferedImage(images[i],width,height,qualityHint);
            }
        } catch(Exception e) {
            logger.error("Fehler in getScaledBufferedImageField("+field+","+width+","+height+"): "+e);
        }
        
        return scaledBufferedImages;
    }
    
    public BufferedImage getScaledBufferedImage(BufferedImage image,int width,int height, int qualityHint) {
        BufferedImage scaledImg = null;
        
        if ((width < image.getWidth() || height < image.getHeight()) && qualityHint == SCALE_SMOOTH) {
            int targetWidth;
            int targetHeight;
            
            do {
                if (width <= image.getWidth() / 2) {
                    targetWidth = image.getWidth() / 2;
                } else {
                    targetWidth = width;
                }

                if (height <= image.getHeight() / 2) {
                    targetHeight = image.getHeight() / 2;
                } else {
                    targetHeight = height;
                }
            
                scaledImg = new BufferedImage( targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D)scaledImg.getGraphics();

                g.setComposite(AlphaComposite.Src);
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g.drawImage(image, 0, 0, targetWidth, targetHeight, null);
                g.dispose();
                
                image = scaledImg;
            } while (image.getWidth() != width || image.getHeight() != height);
        } else {
            scaledImg = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D)scaledImg.getGraphics();

            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
        }
        
        return scaledImg;
    }
    
    /**
     * <b>Fast, but complete black (eq not working), maybe taking another look at it, may be the better solution</b>
     * @param field
     * @param width
     * @param height
     * @return 
     */
    public BufferedImage[] getScaledBufferedImageField_fastTesting(int field,int width,int height) {
        BufferedImage[] bufferedImagesBefore = null;
        BufferedImage[] scaledBufferedImages = null;
        
        try {
            bufferedImagesBefore = getImageField(field);
            scaledBufferedImages = new BufferedImage[bufferedImagesBefore.length];
            
            double scaleWidth = 0;
            double scaleHeight = 0;
            
            for (int i = 0;i < scaledBufferedImages.length;i++) {
                scaleWidth = bufferedImagesBefore[i].getWidth();
                scaleHeight = bufferedImagesBefore[i].getHeight();
                
                scaledBufferedImages[i] = new BufferedImage((int)scaleWidth,(int)scaleHeight,BufferedImage.TYPE_INT_ARGB);
                        
                scaleWidth = width / scaleWidth;
                scaleHeight = height / scaleHeight;
                
                AffineTransform at = new AffineTransform();
                at.scale(scaleWidth, scaleHeight);
                AffineTransformOp scaleOp = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
                
                scaledBufferedImages[i] = scaleOp.filter(bufferedImagesBefore[i], scaledBufferedImages[i]);
            }
        } catch(Exception e) {
            logger.error("Fehler in getScaledBufferedImageField("+field+","+width+","+height+"): "+e);
        }
        
        return scaledBufferedImages;
    }
    
    private void loadUiImages() {
        this.ui_transferButtonActive = new BufferedImage[1];
        this.ui_transferButtonActive[0] = loadFromJar("/images/ui/transferButtonActive.png");
        
        this.ui_transferButtonLine = new BufferedImage[1];
        this.ui_transferButtonLine[0] = loadFromJar("/images/ui/transferButtonLine.png");
        
        this.ui_transferWindow = new BufferedImage[1];
        this.ui_transferWindow[0] = loadFromJar("/images/ui/transferWindow.png");
        
        this.ui_tooltip_background = new BufferedImage[1];
        this.ui_tooltip_background[0] = loadFromJar("/images/ui/tooltipBackground.png");
        
        this.ui_inventoryButtonLineSelected = new BufferedImage[1];
        this.ui_inventoryButtonLineSelected[0] = loadFromJar("/images/ui/inventoryButtonLineSelected.png");
        
        this.ui_inventoryButtonLine = new BufferedImage[1];
        this.ui_inventoryButtonLine[0] = loadFromJar("/images/ui/inventoryButtonLine.png");
        
        this.ui_inventorySelectionBar = new BufferedImage[1];
        this.ui_inventorySelectionBar[0] = loadFromJar("/images/ui/inventorySelectionBar.png");
        
        this.ui_inventorySlotActive = new BufferedImage[1];
        this.ui_inventorySlotActive[0] = loadFromJar("/images/ui/inventorySlotActive.png");
        
        this.ui_inventorySlot = new BufferedImage[1];
        this.ui_inventorySlot[0] = loadFromJar("/images/ui/inventorySlot.png");
        
        this.ui_inventoryWindow = new BufferedImage[1];
        this.ui_inventoryWindow[0] = loadFromJar("/images/ui/inventoryWindow.png");
        
        this.ui_shieldRingShipIcons = new BufferedImage[4];
        this.ui_shieldRingShipIcons[0] = loadFromJar("/images/ui/iconDummyShield.png");
        this.ui_shieldRingShipIcons[1] = loadFromJar("/images/ui/iconCorvetteShield.png");
        this.ui_shieldRingShipIcons[2] = loadFromJar("/images/ui/iconAlienHunterShield.png");
        this.ui_shieldRingShipIcons[3] = loadFromJar("/images/ui/iconAlienHunterSmallShield.png");
        
        this.ui_shieldRing = new BufferedImage[24];
        this.ui_shieldRing[0] = loadFromJar("/images/ui/shieldElementLeft.png");
        this.ui_shieldRing[1] = loadFromJar("/images/ui/shieldElementBottom.png");
        this.ui_shieldRing[2] = loadFromJar("/images/ui/shieldElementRight.png");
        this.ui_shieldRing[3] = loadFromJar("/images/ui/shieldElementTop.png");
        
        this.ui_shieldRing[4] = loadFromJar("/images/ui/shieldElementLeft1.png");
        this.ui_shieldRing[5] = loadFromJar("/images/ui/shieldElementBottom1.png");
        this.ui_shieldRing[6] = loadFromJar("/images/ui/shieldElementRight1.png");
        this.ui_shieldRing[7] = loadFromJar("/images/ui/shieldElementTop1.png");
        
        this.ui_shieldRing[8] = loadFromJar("/images/ui/shieldElementLeft2.png");
        this.ui_shieldRing[9] = loadFromJar("/images/ui/shieldElementBottom2.png");
        this.ui_shieldRing[10] = loadFromJar("/images/ui/shieldElementRight2.png");
        this.ui_shieldRing[11] = loadFromJar("/images/ui/shieldElementTop2.png");
        
        this.ui_shieldRing[12] = loadFromJar("/images/ui/shieldElementLeft3.png");
        this.ui_shieldRing[13] = loadFromJar("/images/ui/shieldElementBottom3.png");
        this.ui_shieldRing[14] = loadFromJar("/images/ui/shieldElementRight3.png");
        this.ui_shieldRing[15] = loadFromJar("/images/ui/shieldElementTop3.png");
        
        this.ui_shieldRing[16] = loadFromJar("/images/ui/shieldElementLeft4.png");
        this.ui_shieldRing[17] = loadFromJar("/images/ui/shieldElementBottom4.png");
        this.ui_shieldRing[18] = loadFromJar("/images/ui/shieldElementRight4.png");
        this.ui_shieldRing[19] = loadFromJar("/images/ui/shieldElementTop4.png");
        
        this.ui_shieldRing[20] = loadFromJar("/images/ui/shieldElementLeftSelected.png");
        this.ui_shieldRing[21] = loadFromJar("/images/ui/shieldElementBottomSelected.png");
        this.ui_shieldRing[22] = loadFromJar("/images/ui/shieldElementRightSelected.png");
        this.ui_shieldRing[23] = loadFromJar("/images/ui/shieldElementTopSelected.png");
        
        this.ui_slot = new BufferedImage[1];
        this.ui_slot[0] = loadFromJar("/images/ui/slot.png");
        
        this.ui_slotActive = new BufferedImage[1];
        this.ui_slotActive[0] = loadFromJar("/images/ui/slotActive.png");
        
        this.ui_warningBar = new BufferedImage[1];
        this.ui_warningBar[0] = loadFromJar("/images/ui/warningBar.png");
        
        this.ui_leftBar = new BufferedImage[1];
        this.ui_leftBar[0] = loadFromJar("/images/ui/leftBar.png");
        
        this.ui_barbackground = new BufferedImage[1];
        this.ui_barbackground[0] = loadFromJar("/images/ui/barBackground.png");
        
        this.ui_healthBarGreen = new BufferedImage[1];
        this.ui_healthBarGreen[0] = loadFromJar("/images/ui/healthbarGreen.png");
        
        this.ui_healthBar = new BufferedImage[1];
        this.ui_healthBar[0] = loadFromJar("/images/ui/healthbar.png");
        
        this.ui_energyBar = new BufferedImage[1];
        this.ui_energyBar[0] = loadFromJar("/images/ui/energybar.png");
    }
    
    private void loadBackgrounds() {
        this.background_stars = new BufferedImage[1];
        this.background_stars[0] = loadFromJar("/images/backgrounds/stars.png");
        
        this.background_sectors = new BufferedImage[1];
        for (int i = 0;i < this.background_sectors.length;i++) {
            this.background_sectors[i] = loadFromJar("/images/backgrounds/sector"+i+".png");
        }
    }
    
    private void loadShips() {
        this.ship_Dummy = new BufferedImage[1];
        this.ship_Dummy[0] = loadFromJar("/images/ships/shipDummy.png");
        
        this.ship_Corvette = new BufferedImage[1];
        this.ship_Corvette[0] = loadFromJar("/images/ships/shipCorvette.png");
        
        this.ship_AlienHunter = new BufferedImage[1];
        this.ship_AlienHunter[0] = loadFromJar("/images/ships/shipAlienHunter.png");
        
        this.ship_AlienHunterSmall = new BufferedImage[1];
        this.ship_AlienHunterSmall[0] = loadFromJar("/images/ships/shipAlienHunterSmall.png");
    }
    
    private void loadThrusters() {
        this.thruster_Flame_Yellow = new BufferedImage[4];
        
        for (int i = 0;i < this.thruster_Flame_Yellow.length;i++) {
            this.thruster_Flame_Yellow[i] = loadFromJar("/images/thrusters/flame/thrustDummy"+i+".png");
        }
        
        this.thruster_Flame_Green = new BufferedImage[4];
        
        for (int i = 0;i < this.thruster_Flame_Green.length;i++) {
            this.thruster_Flame_Green[i] = loadFromJar("/images/thrusters/flame/thrustAlien"+i+".png");
        }
        
        this.thruster_Standart = new BufferedImage[1];
        this.thruster_Standart[0] = loadFromJar("/images/thrusters/object/thrusterStandart.png");
        
        this.thruster_Alien = new BufferedImage[1];
        this.thruster_Alien[0] = loadFromJar("/images/thrusters/object/thrusterStandart.png");
    }
    
    private void loadParticles() {
        this.particle_dummy = new BufferedImage[1];
        this.particle_dummy[0] = loadFromJar("/images/particles/particleDummy.png");
        
        this.particle_yellowTransparent = new BufferedImage[1];
        this.particle_yellowTransparent[0] = loadFromJar("/images/particles/particleYellowTransparent.png");
    }
    
    private void loadBullets() {
        this.bullet_laserRed = new BufferedImage[1];
        this.bullet_laserRed[0] = loadFromJar("/images/bullets/bulletLaserRed.png");
        
        this.bullet_laserRayBlue = new BufferedImage[1];
        this.bullet_laserRayBlue[0] = loadFromJar("/images/bullets/bulletLaserRayBlue.png");
        
        this.bullet_plasma = new BufferedImage[1];
        this.bullet_plasma[0] = loadFromJar("/images/bullets/bulletPlasma.png");
    }
    
    private void loadWeapons() {
        this.weapon_laser1 = new BufferedImage[1];
        this.weapon_laser1[0] = loadFromJar("/images/weapons/weaponLaser1.png");
        
        this.weapon_laser1_shootAni = new BufferedImage[3];
        this.weapon_laser1_shootAni[0] = loadFromJar("/images/weapons/shootani/weaponLaser1.png");
        this.weapon_laser1_shootAni[1] = loadFromJar("/images/weapons/shootani/weaponLaser2.png");
        this.weapon_laser1_shootAni[2] = loadFromJar("/images/weapons/shootani/weaponLaser3.png");
        
        this.weapon_plasmaGun = new BufferedImage[1];
        this.weapon_plasmaGun[0] = loadFromJar("/images/weapons/weaponPlasmaGun.png");
        
        this.weapon_plasmaGunRight = new BufferedImage[1];
        this.weapon_plasmaGunRight[0] = loadFromJar("/images/weapons/weaponPlasmaGunRight.png");
        
        this.weapon_plasmaLance = new BufferedImage[1];
        this.weapon_plasmaLance[0] = loadFromJar("/images/weapons/weaponPlasmaLance.png");
        
        this.weapon_plasmaLance_shootAni = new BufferedImage[8];
        this.weapon_plasmaLance_shootAni[0] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance1.png");
        this.weapon_plasmaLance_shootAni[1] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance2.png");
        this.weapon_plasmaLance_shootAni[2] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance3.png");
        this.weapon_plasmaLance_shootAni[3] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance4.png");
        this.weapon_plasmaLance_shootAni[4] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance5.png");
        this.weapon_plasmaLance_shootAni[5] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance6.png");
        this.weapon_plasmaLance_shootAni[6] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance7.png");
        this.weapon_plasmaLance_shootAni[7] = loadFromJar("/images/weapons/shootani/weaponPlasmaLance8.png");
    }
    
    private void loadShields() {
        this.shield_standart = new BufferedImage[1];
        this.shield_standart[0] = loadFromJar("/images/shields/shieldStandart.png");
    }
    
    private void loadTurrets() {
        this.turret_doublelaser1 = new BufferedImage[1];
        this.turret_doublelaser1[0] = loadFromJar("/images/turrets/turretDoubleLaser.png");
        
        this.turret_doublelaser1_shootAni = new BufferedImage[1];
        this.turret_doublelaser1_shootAni[0]  = loadFromJar("/images/turrets/shootani/turretDoubleLaser.png");
    }
    
    private void loadLighting() {
        this.lighting_red = new BufferedImage[1];
        this.lighting_red[0] = loadFromJar("/images/lighting/redLighting.png");
        
        this.lighting_yellow = new BufferedImage[1];
        this.lighting_yellow[0] = loadFromJar("/images/lighting/yellowLighting.png");
        
        this.lighting_blue = new BufferedImage[1];
        this.lighting_blue[0] = loadFromJar("/images/lighting/blueLighting.png");
        
        this.lighting_green = new BufferedImage[1];
        this.lighting_green[0] = loadFromJar("/images/lighting/greenLighting.png");
    }
    
    private void loadExplosions() {
        this.explosion_dummy = new BufferedImage[6];
        this.explosion_dummy[0] = loadFromJar("/images/explosions/explosionStandart1.png");
        this.explosion_dummy[1] = loadFromJar("/images/explosions/explosionStandart2.png");
        this.explosion_dummy[2] = loadFromJar("/images/explosions/explosionStandart3.png");
        this.explosion_dummy[3] = loadFromJar("/images/explosions/explosionStandart4.png");
        this.explosion_dummy[4] = loadFromJar("/images/explosions/explosionStandart5.png");
        this.explosion_dummy[5] = loadFromJar("/images/explosions/explosionStandart6.png");
        
        this.explosion_standart_smooth = new BufferedImage[7];
        this.explosion_standart_smooth[0] = loadFromJar("/images/explosions/explosionStandartSmooth1.png");
        this.explosion_standart_smooth[1] = loadFromJar("/images/explosions/explosionStandartSmooth2.png");
        this.explosion_standart_smooth[2] = loadFromJar("/images/explosions/explosionStandartSmooth3.png");
        this.explosion_standart_smooth[3] = loadFromJar("/images/explosions/explosionStandartSmooth4.png");
        this.explosion_standart_smooth[4] = loadFromJar("/images/explosions/explosionStandartSmooth5.png");
        this.explosion_standart_smooth[5] = loadFromJar("/images/explosions/explosionStandartSmooth6.png");
        this.explosion_standart_smooth[6] = loadFromJar("/images/explosions/explosionStandartSmooth7.png");
    }
    
    private void loadIcons() {
        //Item Icons werden vorskaliert auf 50px x 50px um performance zu sparen
        this.icon_ore = new BufferedImage[1];
        this.icon_ore[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconOre.png"),50,50,SCALE_SMOOTH);
        
        this.icon_turretDoubleLaser = new BufferedImage[1];
        this.icon_turretDoubleLaser[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconTurretDoubleLaser.png"),50,50,SCALE_SMOOTH);
        
        this.icon_weaponLaser1 = new BufferedImage[1];
        this.icon_weaponLaser1[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconWeaponLaser1.png"),50,50,SCALE_SMOOTH);
        
        this.icon_weaponPlasmaLance = new BufferedImage[1];
        this.icon_weaponPlasmaLance[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconWeaponPlasmaLance.png"),50,50,SCALE_SMOOTH);
        
        this.icon_Battery = new BufferedImage[1];
        this.icon_Battery[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconBattery.png"),50,50,SCALE_SMOOTH);
        
        this.icon_Reactor = new BufferedImage[1];
        this.icon_Reactor[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconReactor.png"),50,50,SCALE_SMOOTH);
        
        this.icon_Shield = new BufferedImage[1];
        this.icon_Shield[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconShield.png"),50,50,SCALE_SMOOTH);
        
        this.icon_Thruster_Standart = new BufferedImage[1];
        this.icon_Thruster_Standart[0] = getScaledBufferedImage(loadFromJar("/images/icons/iconThrusterStandart.png"),50,50,SCALE_SMOOTH);
        
        this.icon_ShipDummy = new BufferedImage[1];
        this.icon_ShipDummy[0] = loadFromJar("/images/icons/shipIcons/iconShipDummy.png");
        
        this.icon_ShipCorvette = new BufferedImage[1];
        this.icon_ShipCorvette[0] = loadFromJar("/images/icons/shipIcons/iconShipCorvette.png");
        
        this.icon_ShipAlienHunter = new BufferedImage[1];
        this.icon_ShipAlienHunter[0] = loadFromJar("/images/icons/shipIcons/iconShipAlienHunter.png");
        
        this.icon_ShipAlienHunterSmall = new BufferedImage[1];
        this.icon_ShipAlienHunterSmall[0] = loadFromJar("/images/icons/shipIcons/iconShipAlienHunterSmall.png");
    }
    
    private void loadDrop() {
        this.drop = new BufferedImage[14];
        this.drop[0] = loadFromJar("/images/drop/dropImage1.png");
        this.drop[1] = loadFromJar("/images/drop/dropImage2.png");
        this.drop[2] = loadFromJar("/images/drop/dropImage3.png");
        this.drop[3] = loadFromJar("/images/drop/dropImage4.png");
        this.drop[4] = loadFromJar("/images/drop/dropImage5.png");
        this.drop[5] = loadFromJar("/images/drop/dropImage6.png");
        this.drop[6] = loadFromJar("/images/drop/dropImage7.png");
        this.drop[7] = loadFromJar("/images/drop/dropImage8.png");
        this.drop[8] = loadFromJar("/images/drop/dropImage9.png");
        this.drop[9] = loadFromJar("/images/drop/dropImage10.png");
        this.drop[10] = loadFromJar("/images/drop/dropImage11.png");
        this.drop[11] = loadFromJar("/images/drop/dropImage12.png");
        this.drop[12] = loadFromJar("/images/drop/dropImage13.png");
        this.drop[13] = loadFromJar("/images/drop/dropImage14.png");
    }
    
    private BufferedImage loadFromJar(String relativePath) {
        BufferedImage bImage = null;
        
        try {
            bImage = ImageIO.read(this.getClass().getResourceAsStream(relativePath));
        } catch (Exception e) {
            logger.error("Exception in loadFromJar("+relativePath+"): "+e);
        }
        
        return bImage;
    }
}
