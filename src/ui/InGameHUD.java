/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Interfaces.Cargo;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Resources;
import support.Settings;
import units.PlayerShip;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class InGameHUD {
    private int windowWidth;
    private int windowHeight;
    
    private BufferedImage imgShieldRingIcon;
    private BufferedImage[] imgShieldRing;
    private BufferedImage[] imgShieldRing1;
    private BufferedImage[] imgShieldRing2;
    private BufferedImage[] imgShieldRing3;
    private BufferedImage[] imgShieldRing4;
    private BufferedImage[] imgShieldRingSelected;
    private BufferedImage[] imgSlotBackground;
    private BufferedImage[] imgSlotActive;
    private BufferedImage[] imgLeftBar;
    private BufferedImage[] imgHealthBar;
    private BufferedImage[] imgEnergyBar;
    private BufferedImage[] imgWarningBar;
    private BufferedImage[] imgBarBackground;
    
    private int healthStatus;
    private int energyStatus;
    
    private long blinkLastTime;
    private boolean blinkState;
    
    public InGameHUD(int width,int height) {
        this.windowWidth = width;
        this.windowHeight = height;
        
        this.imgShieldRing = new BufferedImage[4];
        this.imgShieldRing1 = new BufferedImage[4];
        this.imgShieldRing2 = new BufferedImage[4];
        this.imgShieldRing3 = new BufferedImage[4];
        this.imgShieldRing4 = new BufferedImage[4];
        this.imgShieldRingSelected = new BufferedImage[4];
        
        BufferedImage[] sides = MainLoop.resources.getScaledBufferedImageField(Resources.UI_SHIELDRING, 72, 79,Resources.SCALE_SMOOTH);
        BufferedImage[] bottoms = MainLoop.resources.getScaledBufferedImageField(Resources.UI_SHIELDRING, 172, 31,Resources.SCALE_SMOOTH);
        BufferedImage[] tops = MainLoop.resources.getScaledBufferedImageField(Resources.UI_SHIELDRING, 150, 18,Resources.SCALE_SMOOTH);
        
        this.imgShieldRing[0] = sides[0];
        this.imgShieldRing[1] = bottoms[1];
        this.imgShieldRing[2] = sides[2];
        this.imgShieldRing[3] = tops[3];
        
        this.imgShieldRing1[0] = sides[4];
        this.imgShieldRing1[1] = bottoms[5];
        this.imgShieldRing1[2] = sides[6];
        this.imgShieldRing1[3] = tops[7];
        
        this.imgShieldRing2[0] = sides[8];
        this.imgShieldRing2[1] = bottoms[9];
        this.imgShieldRing2[2] = sides[10];
        this.imgShieldRing2[3] = tops[11];
        
        this.imgShieldRing3[0] = sides[12];
        this.imgShieldRing3[1] = bottoms[13];
        this.imgShieldRing3[2] = sides[14];
        this.imgShieldRing3[3] = tops[15];
        
        this.imgShieldRing4[0] = sides[16];
        this.imgShieldRing4[1] = bottoms[17];
        this.imgShieldRing4[2] = sides[18];
        this.imgShieldRing4[3] = tops[19];
        
        this.imgShieldRingSelected[0] = sides[20];
        this.imgShieldRingSelected[1] = bottoms[21];
        this.imgShieldRingSelected[2] = sides[22];
        this.imgShieldRingSelected[3] = tops[23];
        
        this.imgSlotBackground = MainLoop.resources.getScaledBufferedImageField(Resources.UI_SLOT, 70, 70,Resources.SCALE_SMOOTH);
        this.imgSlotActive = MainLoop.resources.getScaledBufferedImageField(Resources.UI_SLOTACTIVE, 70, 70,Resources.SCALE_SMOOTH);
        
        this.imgLeftBar = MainLoop.resources.getScaledBufferedImageField(Resources.UI_LEFTBAR, 200, 100,Resources.SCALE_SMOOTH);
        this.imgHealthBar = MainLoop.resources.getScaledBufferedImageField(Resources.UI_HEALTHBAR, 1, 18,Resources.SCALE_SMOOTH);
        this.imgEnergyBar = MainLoop.resources.getScaledBufferedImageField(Resources.UI_ENERGYBAR, 1, 18,Resources.SCALE_SMOOTH);
        this.imgWarningBar = MainLoop.resources.getScaledBufferedImageField(Resources.UI_WARNINGBAR, 150, 18,Resources.SCALE_SMOOTH);
        this.imgBarBackground = MainLoop.resources.getScaledBufferedImageField(Resources.UI_BARBACKGROUND, 150, 18,Resources.SCALE_SMOOTH);
        
        this.healthStatus = 150;
        this.energyStatus = 150;
        
        this. blinkLastTime = 0;
        this.blinkState = false;
    }
    
    public void update(int width,int height,double health,double energy) {
        this.windowWidth = width;
        this.windowHeight = height;
        
        this.healthStatus = (int)(150 * (MainLoop.playerShip.getPercentOfHealth() / 100));
        
        double percent = (energy * 100f) / MainLoop.playerShip.getEnergyCapacity();
        this.energyStatus = (int)(150 * (percent / 100));
        
        if (Settings.MOUSE_LEFT_DOWN) {
            if (checkInsideShieldRing("Left")) {
                MainLoop.playerShip.shieldRingClicked("Left");
                MainLoop.ui_click_happened = true;
            } else if (checkInsideShieldRing("Bottom")) {
                MainLoop.playerShip.shieldRingClicked("Bottom");
                MainLoop.ui_click_happened = true;
            } else if (checkInsideShieldRing("Right")) {
                MainLoop.playerShip.shieldRingClicked("Right");
                MainLoop.ui_click_happened = true;
            } else if (checkInsideShieldRing("Top")) {
                MainLoop.playerShip.shieldRingClicked("Top");
                MainLoop.ui_click_happened = true;
            }
        }
    }
    
    public void setShieldIcon(BufferedImage shieldRingIcon) {
        this.imgShieldRingIcon = shieldRingIcon;
    }
    
    public void draw(Graphics2D g2d) {
        drawSlotBar(g2d);
        drawLeftBar(g2d);
        drawShieldRing(g2d);
    }
    
    private void drawSlotBar(Graphics2D g2d) {
        int xCoord = (this.windowWidth / 2) - ((70 * PlayerShip.SLOTCONTENTS.length) / 2);
        Font defaultFont = g2d.getFont();
        Font customFont = new Font("Helvetica",Font.BOLD,12);
        
        g2d.setFont(customFont);
        
        for (int i = 0; i < PlayerShip.SLOTCONTENTS.length;i++) {
            if (i == Settings.SLOT_VALUE) {
                g2d.drawImage(this.imgSlotActive[0], xCoord + 70 * i,0, null);
            } else {
                g2d.drawImage(this.imgSlotBackground[0], xCoord + 70 * i,0, null);
            }
            
            if (MainLoop.playerShip.getWeaponFromSlot(i) != null) {
                g2d.drawImage(((Cargo)MainLoop.playerShip.getWeaponFromSlot(i)).getIcon(), xCoord + 70 * i + 10,2, null);
                g2d.drawString("" +(i+1) + " - " + shortenSlotName(((Cargo)MainLoop.playerShip.getWeaponFromSlot(i)).getName()), xCoord + 70 * i + 5, 66);
            }
        }
        
        g2d.setFont(defaultFont);
    }
    
    private String shortenSlotName(String name) {
        if (name.length() > 6) {
            name = name.substring(0, 6);
        }
        
        return name;
    }
    
    private void drawLeftBar(Graphics2D g2d) {
        g2d.drawImage(this.imgLeftBar[0], 0,this.windowHeight - 100, null);
        
        if (healthStatus < 20 || energyStatus < 20) {
            if (System.currentTimeMillis() - this.blinkLastTime > 200) {
                this.blinkLastTime = System.currentTimeMillis();
                if (this.blinkState) {
                    this.blinkState = false;
                } else {
                    this.blinkState = true;
                }
            } 
        }
        
        Font defaultFont = g2d.getFont();
        Font customFont = new Font("Helvetica",Font.BOLD,13);
        
        g2d.setFont(customFont);
        
        g2d.drawString(StringSelector.getSring(StringSelector.GAMEUI_HULL),5, (this.windowHeight - 100) + 18);
        g2d.drawString(StringSelector.getSring(StringSelector.GAMEUI_ENERGY),5, (this.windowHeight - 100) + 60);
        
        g2d.setFont(defaultFont);
        
        /*for (int i = 0;i < 150;i++) {
            g2d.drawImage(this.imgBarBackground[0], 5 + i,(this.windowHeight - 100) + 23, null);
            g2d.drawImage(this.imgBarBackground[0], 5 + i,(this.windowHeight - 100) + 66, null);
        }*/
        
        g2d.drawImage(this.imgBarBackground[0], 5,(this.windowHeight - 100) + 23, null);
        g2d.drawImage(this.imgBarBackground[0], 5,(this.windowHeight - 100) + 66, null);
        
        if (healthStatus < 20 && this.blinkState == true) {
            /*for (int i = 0;i < 150;i++) {
                g2d.drawImage(this.imgWarningBar[0], 5 + i,(this.windowHeight - 100) + 23, null);
            }*/
            g2d.drawImage(this.imgWarningBar[0], 5,(this.windowHeight - 100) + 23, null);
        }
        
        for (int i = 0;i < healthStatus;i++) {
            g2d.drawImage(this.imgHealthBar[0], 5 + i,(this.windowHeight - 100) + 23, null);
        }
        
        if (energyStatus < 20 && this.blinkState == true) {
            /*for (int i = 0;i < 150;i++) {
                g2d.drawImage(this.imgWarningBar[0], 5 + i,(this.windowHeight - 100) + 66, null);
            }*/
            g2d.drawImage(this.imgWarningBar[0], 5,(this.windowHeight - 100) + 66, null);
        }
        
        for (int i = 0;i < energyStatus;i++) {
            g2d.drawImage(this.imgEnergyBar[0], 5 + i,(this.windowHeight - 100) + 66, null);
        }
    }
    
    private void drawShieldRing(Graphics2D g2d) {
        int ringX = this.windowWidth / 2 - 122;
        int ringY = this.windowHeight - 120;
        
        int offsetLeftY = 20;
        int offsetBottomX = 36;
        int offsetBottomY = 84;
        int offsetRightX = 172;
        int offsetRightY = 20;
        int offsetTopX = 48;
        int offsetTopY = 12;
        
        if (this.imgShieldRingIcon != null) {
            g2d.drawImage(this.imgShieldRingIcon, ringX + 26,ringY + 20, null);
        }
        
        double shieldCapacity = MainLoop.playerShip.getShieldCapacity();
        double shieldEnergyStatus = MainLoop.playerShip.getShieldCurrentEnergyStatus("Left");
        double percent = (shieldEnergyStatus * 100f) / shieldCapacity;
        
        if (shieldCapacity == 0) {
            g2d.drawImage(this.imgShieldRing4[0], ringX,ringY + offsetLeftY, null);
        } else if (percent <= 25) {
            g2d.drawImage(this.imgShieldRing3[0], ringX,ringY + offsetLeftY, null);
        } else if (percent > 25 && percent <= 50) {
            g2d.drawImage(this.imgShieldRing2[0], ringX,ringY + offsetLeftY, null);
        } else if (percent > 50 && percent <= 75) {
            g2d.drawImage(this.imgShieldRing1[0], ringX,ringY + offsetLeftY, null);
        } else {
            g2d.drawImage(this.imgShieldRing[0], ringX,ringY + offsetLeftY, null);
        }
        
        shieldEnergyStatus = MainLoop.playerShip.getShieldCurrentEnergyStatus("Bottom");
        percent = (shieldEnergyStatus * 100f) / shieldCapacity;
        
        if (shieldCapacity == 0) {
            g2d.drawImage(this.imgShieldRing4[1], ringX + offsetBottomX,ringY + offsetBottomY, null);
        } else if (percent <= 25) {
            g2d.drawImage(this.imgShieldRing3[1], ringX + offsetBottomX,ringY + offsetBottomY, null);
        } else if (percent > 25 && percent <= 50) {
            g2d.drawImage(this.imgShieldRing2[1], ringX + offsetBottomX,ringY + offsetBottomY, null);
        } else if (percent > 50 && percent <= 75) {
            g2d.drawImage(this.imgShieldRing1[1], ringX + offsetBottomX,ringY + offsetBottomY, null);
        } else {
            g2d.drawImage(this.imgShieldRing[1], ringX + offsetBottomX,ringY + offsetBottomY, null);
        }
        
        shieldEnergyStatus = MainLoop.playerShip.getShieldCurrentEnergyStatus("Right");
        percent = (shieldEnergyStatus * 100f) / shieldCapacity;
        
        if (shieldCapacity == 0) {
            g2d.drawImage(this.imgShieldRing4[2], ringX + offsetRightX,ringY + offsetRightY, null);
        } else if (percent <= 25) {
            g2d.drawImage(this.imgShieldRing3[2], ringX + offsetRightX,ringY + offsetRightY, null);
        } else if (percent > 25 && percent <= 50) {
            g2d.drawImage(this.imgShieldRing2[2], ringX + offsetRightX,ringY + offsetRightY, null);
        } else if (percent > 50 && percent <= 75) {
            g2d.drawImage(this.imgShieldRing1[2], ringX + offsetRightX,ringY + offsetRightY, null);
        } else {
            g2d.drawImage(this.imgShieldRing[2], ringX + offsetRightX,ringY + offsetRightY, null);
        }
        
        shieldEnergyStatus = MainLoop.playerShip.getShieldCurrentEnergyStatus("Top");
        percent = (shieldEnergyStatus * 100f) / shieldCapacity;
        
        if (shieldCapacity == 0) {
            g2d.drawImage(this.imgShieldRing4[3], ringX + offsetTopX,ringY + offsetTopY, null);
        } else if (percent <= 25) {
            g2d.drawImage(this.imgShieldRing3[3], ringX + offsetTopX,ringY + offsetTopY, null);
        } else if (percent > 25 && percent <= 50) {
            g2d.drawImage(this.imgShieldRing2[3], ringX + offsetTopX,ringY + offsetTopY, null);
        } else if (percent > 50 && percent <= 75) {
            g2d.drawImage(this.imgShieldRing1[3], ringX + offsetTopX,ringY + offsetTopY, null);
        } else {
            g2d.drawImage(this.imgShieldRing[3], ringX + offsetTopX,ringY + offsetTopY, null);
        }
        
        if (checkInsideShieldRing("Left")) {
            g2d.drawImage(this.imgShieldRingSelected[0], ringX,ringY + offsetLeftY, null);
        } else if (checkInsideShieldRing("Bottom")) {
            g2d.drawImage(this.imgShieldRingSelected[1], ringX + offsetBottomX,ringY + offsetBottomY, null);
        } else if (checkInsideShieldRing("Right")) {
            g2d.drawImage(this.imgShieldRingSelected[2], ringX + offsetRightX,ringY + offsetRightY, null);
        } else if (checkInsideShieldRing("Top")) {
            g2d.drawImage(this.imgShieldRingSelected[3], ringX + offsetTopX,ringY + offsetTopY, null);
        }
    }
    
    private boolean checkInsideShieldRing(String which) {
        boolean isInside = false;
        int mausX = MainLoop.mausX;
        int mausY = MainLoop.mausY;
        
        int ringX = this.windowWidth / 2 - 122;
        int ringY = this.windowHeight - 120;
        
        if (mausX >= ringX + 3 && mausX <= ringX + 240 && mausY >= ringY + 15 && mausY <= ringY + 111) {
            switch (which) {
                case "Left":
                    if (mausX >= ringX + 3 && mausX <= ringX + 63 && mausY >= ringY + 24 && mausY <= ringY + 95) {
                        isInside = true;
                    }
                    break;
                case "Bottom":
                    if (mausX >= ringX + 43 && mausX <= ringX + 201 && mausY >= ringY + 88 && mausY <= ringY + 112) {
                        isInside = true;
                    }
                    break;
                case "Right":
                    if (mausX >= ringX + 182 && mausX <= ringX + 240 && mausY >= ringY + 24 && mausY <= ringY + 95) {
                        isInside = true;
                    }
                    break;
                case "Top":
                    if (mausX >= ringX + 59 && mausX <= ringX + 186 && mausY >= ringY + 15 && mausY <= ringY + 30) {
                        isInside = true;
                    }
                    break;
            }
        }
        
        return isInside;
    }
}
