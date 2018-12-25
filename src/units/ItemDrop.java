/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.Cargo;
import Interfaces.GameUnit;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.FPSManager;
import support.Inventory;
import support.Resources;
import support.Settings;
import ui.TooltipHUD;
import ui.TransferHUD;
import utils.Utils;
import utils.Vector2D;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class ItemDrop implements GameUnit {
    private Inventory inventory;
    
    private BufferedImage[] image;
    private int actual;
    private long animationDelay;
    private long lastAnimationImage_Time;
    
    private double posX;
    private double posY;
    
    private int width;
    private int height;
    
    private long lifeTime;
    private long lifeTimeStart;
    
    private Ship target;
    private double speed;
    
    private long mouseHoverStart;
    private long waitUntilShowTooltip;
    
    private boolean wasFrozen;
    private long frozenStartTime;
    
    private TooltipHUD tooltip;
    
    public ItemDrop(Inventory inventory, double posX, double posY) {
        this.inventory = inventory;
        
        this.posX = posX;
        this.posY = posY;
        
        this.width = 30;
        this.height = 30;
        
        this.lifeTime = 20000;
        this.lifeTimeStart = System.currentTimeMillis();
        
        image = MainLoop.resources.getScaledBufferedImageField(Resources.DROP,width,height,Resources.SCALE_SMOOTH);
        this.actual = 0;
        this.animationDelay = 50;
        this.lastAnimationImage_Time = 0;
        
        this.speed = 1000;
        
        this.mouseHoverStart = 0;
        this.waitUntilShowTooltip = 400;
        
        this.wasFrozen = false;
        this.frozenStartTime = 0;
    }
    
    public boolean update() {
        boolean destroy = false;
        
        if (this.inventory.isEmpty()) {
            destroy = true;
        }
        
        if (this.wasFrozen) {
            this.lifeTime = this.lifeTime + (System.currentTimeMillis() - this.frozenStartTime);
            
            this.wasFrozen = false;
            this.frozenStartTime = 0;
        }
        
        if (target != null) {
            Vector2D direction = new Vector2D(posX,posY,this.target.getMiddleX(),this.target.getMiddleY());
            
            if (direction.getLength() < this.width + this.target.getHeight()) {
                TransferHUD.open = true;
                MainLoop.transferHUD = new TransferHUD(this);
                
                this.wasFrozen = true;
                this.frozenStartTime = System.currentTimeMillis();
                
                target = null;
                return false;
            }
            
            direction = direction.getUnitVector().multiplyWithNumber(speed * FPSManager.DELTA);
            
            posX += direction.getX();
            posY += direction.getY();
        } else {
            if (isActivated()) {
                Vector2D direction = new Vector2D(posX,posY,MainLoop.playerShip.getMiddleX(),MainLoop.playerShip.getMiddleY());
                
                if (direction.getLength() < 400) {
                    this.target = MainLoop.playerShip;
                }
            }
            
            if (mouseOver() && System.currentTimeMillis() - this.mouseHoverStart > this.waitUntilShowTooltip) {
                if (this.tooltip == null) {
                    this.tooltip = new TooltipHUD(0,0,getTooltipText(),30,this);
                    MainLoop.tooltips.add(this.tooltip);
                } else {
                    this.tooltip.renewLifeTime();
                }
                /*if (MainLoop.tooltipHUD == null || (!MainLoop.tooltipHUD.textEquals(getTooltipText()) && !MainLoop.tooltipHUD.textEquals(StringSelector.getSring(StringSelector.NOT_ENOUGH_SPACE_MESSAGE)))) {
                    MainLoop.tooltipHUD = new TooltipHUD(0,0,getTooltipText(),30,this);
                } else if (MainLoop.tooltipHUD.textEquals(getTooltipText())) {
                    MainLoop.tooltipHUD.renewLifeTime();
                }*/
            } else if (this.tooltip != null) {
                MainLoop.tooltips.remove(this.tooltip);
                this.tooltip = null;
            }
            
            if (System.currentTimeMillis() - this.lifeTimeStart > this.lifeTime) {
                destroy = true;
            }
        }
        
        return destroy;
    }
    
    private boolean isActivated() {
        boolean activated = false;
        
        if (Settings.MOUSE_LEFT_DOWN && mouseOver()) {
            MainLoop.ui_click_happened = true;
            activated = true;
        }
        
        return activated;
    }
    
    private boolean mouseOver() {
        boolean isOver = false;
        
        if (MainLoop.mausX > Background.X + posX && MainLoop.mausX < Background.X + posX + width && MainLoop.mausY > Background.Y + posY && MainLoop.mausY < Background.Y + posY + height) {
            isOver = true;
            
            if (mouseHoverStart == 0) {
                mouseHoverStart = System.currentTimeMillis();
            }
        } else {
            mouseHoverStart = 0;
        }
        
        return isOver;
    }
    
    private String getTooltipText() {
        String tooltipText = "";
        
        for (int i = 0;i < this.inventory.getAvailableSlots();i++) {
            if (this.inventory.getItem(i) != null) {
                int number = this.inventory.getNumberOfItemsInSlot(i);
                
                tooltipText += Utils.trimStringToGivenLength(""+number, 3) + ((Cargo)this.inventory.getItem(i)).getName() + '\n';
            }
        }
        
        return tooltipText;
    }
    
    public Object[] addItems(int pos,Object[] objects) {
        Object[] returnValue = null;
        
        returnValue = this.inventory.addItems(pos, objects);
        
        return returnValue;
    }
    
    public void draw(Graphics2D g2d) {
        if ((this.actual + 1) < image.length && (System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual += 1;
            this.lastAnimationImage_Time = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual = 0;
            this.lastAnimationImage_Time = System.currentTimeMillis();
        }
        
        if (mouseOver() && !MainLoop.ui_open) {
            g2d.drawRoundRect((int)(Background.X + posX) - 3, (int)(Background.Y + posY) - 3, width + 5, height + 5, 5, 5);
            
            Font defaultFont = g2d.getFont();
            
            g2d.setFont(new Font("Helvetica",Font.BOLD,10));
            g2d.drawString(StringSelector.getSring(StringSelector.ITEMDROP), (int)(Background.X + posX) - 8, (int)(Background.Y + posY) - 10);
            
            g2d.setFont(defaultFont);
        }
        
        g2d.drawImage(this.image[this.actual], (int)(Background.X + posX), (int)(Background.Y + posY), null);
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public void setX(double x) {
        this.posX = x;
    }
    
    public void setY(double y) {
        this.posY = y;
    }
    
    @Override
    public double getMiddleX() {
        return this.posX + (this.width / 2);
    }

    @Override
    public double getMiddleY() {
        return this.posY + (this.height / 2);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
    
    public void renewLifetime() {
        this.lifeTimeStart = System.currentTimeMillis();
    }
}
