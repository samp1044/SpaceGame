/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Interfaces.Cargo;
import Interfaces.GameUnit;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.Inventory;
import support.Settings;
import values.StringSelector;

/**
 *
 * @author Sami
 */
class InventorySlot implements GameUnit {
    Class type;
    int belonging;
    
    int width;
    int height;
    int offsetX;
    int offsetY;
    
    int middleX;
    int middleY;
    
    BufferedImage icon;
    Inventory inventory;
    
    int pos;
    
    long mouseHoverStart;
    long waitUntilShowTooltip;
    
    TooltipHUD tooltip;
    
    public InventorySlot(int maxStack,int width,int height,int offsetX,int offsetY, Class type, int belonging) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        inventory = new Inventory(1,maxStack);
        this.pos = 0;
        
        mouseHoverStart = 0;
        waitUntilShowTooltip = 500;
        
        this.type = type;
        this.belonging = belonging;
    }
    
    public InventorySlot(int maxStack,int width,int height,int offsetX,int offsetY, Class type) {
        this(maxStack, width, height, offsetX, offsetY, type, 0);
    }
    
    public void setInventory(Inventory inventory,int pos) {
        this.inventory = inventory;
        this.pos = pos;
        
        if (!isEmpty()) {
            this.icon = ((Cargo)this.inventory.getItem(this.pos)).getIcon();
            this.tooltip = null;
        }
    }
    
    public boolean isActive(int posX,int posY) {
        boolean active = false;
        
        if (MainLoop.mausX > posX + offsetX && MainLoop.mausX < posX + offsetX + width && MainLoop.mausY > posY + offsetY && MainLoop.mausY < posY + offsetY + height) {
            if (mouseHoverStart == 0) {
                mouseHoverStart = System.currentTimeMillis();
            }
            active = true;
        } else {
            mouseHoverStart = 0;
        }
        
        return active;
    }
    
    public boolean isClicked(int posX,int posY) {
        boolean clicked = false;
        
        if ((Settings.MOUSE_LEFT_DOWN || Settings.MOUSE_RIGHT_DOWN) && isActive(posX, posY)) {
            clicked = true;
        }
        
        return clicked;
    }
    
    public Object[] add(Object[] objects) {
        Object[] returnValue = null;
        
        returnValue = this.inventory.addItems(this.pos, objects);
        
        if (!isEmpty()) {
            this.icon = ((Cargo)this.inventory.getItem(this.pos)).getIcon();
            this.tooltip = null;
        }
        
        return returnValue;
    }
    
    public Object add(Object object) {
        Object returnValue = null;
        
        if (!this.inventory.addItem(this.pos, object)) {
            returnValue = object;
        }
        
        if (!isEmpty()) {
            this.icon = ((Cargo)this.inventory.getItem(this.pos)).getIcon();
            this.tooltip = null;
        }
        
        return returnValue;
    }
    
    public Object[] removeItems() {
        Object[] returnValue = null;
        
        returnValue = this.inventory.removeAllItems(this.pos);
        
        return returnValue;
    }
    
    public Object[] removeHalfItems() {
        Object[] returnValue = null;
        
        returnValue = this.inventory.removeHalfItems(this.pos);
        
        return returnValue;
    }
    
    public Object getItem() {
        return this.inventory.getItem(this.pos);
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public boolean isEmpty() {
        boolean empty = true;
        
        empty = this.inventory.isEmpty(this.pos);
        
        return empty;
    }
    
    public void draw(Graphics2D g2d,BufferedImage imageNormal, BufferedImage imageActive, int posX,int posY, boolean hoveringItems) {
        //Nur für die Tooltiperstellung
        this.middleX = (int)-Background.X + posX + this.offsetX + (this.width / 2);
        this.middleY = (int)-Background.Y + posY + this.offsetY + (this.height / 2);
        
        if (isActive(posX, posY)) {
            g2d.drawImage(imageActive, posX + offsetX, posY + offsetY, null);
        } else {
            g2d.drawImage(imageNormal, posX + offsetX, posY + offsetY, null);
        }
        
        if (!isEmpty()) {
            if (this.icon != null) {
                g2d.drawImage(icon, posX + offsetX + 15, posY + offsetY + 8, null);
            } else if (!this.inventory.isEmpty(this.pos)) {
                this.icon = ((Cargo)this.inventory.getItem(this.pos)).getIcon();
            }
            
            String number = "";
            
            if (this.inventory.getNumberOfItemsInSlot(this.pos) > 1) {
                number = this.inventory.getNumberOfItemsInSlot(this.pos) + " ";
            }
            
            g2d.drawString(number +((Cargo)this.inventory.getItem(this.pos)).getName(),posX + offsetX + 5,posY + offsetY + 76);
            
            if (isActive(posX, posY) && mouseHoverStart > 0 && System.currentTimeMillis() - mouseHoverStart > waitUntilShowTooltip) {
                if (this.tooltip == null) {
                    if (hoveringItems) {
                        this.tooltip = new TooltipHUD(10, 10, ((Cargo)this.inventory.getItem(this.pos)).getTooltip(), 30, this);
                    } else {
                        this.tooltip = new TooltipHUD(10, 10, ((Cargo)this.inventory.getItem(this.pos)).getTooltip(), 30, null);
                    }
                    
                    MainLoop.tooltips.add(this.tooltip);
                } else if (this.tooltip != null) {
                    this.tooltip.renewLifeTime();
                    
                    if (!MainLoop.tooltips.contains(this.tooltip)) {
                        MainLoop.tooltips.add(this.tooltip);
                    }
                }
                /*if (MainLoop.tooltipHUD == null || (!MainLoop.tooltipHUD.textEquals(((Cargo)this.inventory.getItem(this.pos)).getTooltip()) && !MainLoop.tooltipHUD.textEquals(StringSelector.getSring(StringSelector.WRONG_TYPE_MESSAGE)))) {
                    MainLoop.tooltipHUD = new TooltipHUD(10, 10, ((Cargo)this.inventory.getItem(this.pos)).getTooltip(), 30, null);
                } else if (MainLoop.tooltipHUD.textEquals(((Cargo)this.inventory.getItem(this.pos)).getTooltip()) && !MainLoop.tooltipHUD.textEquals(StringSelector.getSring(StringSelector.WRONG_TYPE_MESSAGE))) {
                    MainLoop.tooltipHUD.renewLifeTime();
                }*/
            } else if (this.tooltip != null) {
                MainLoop.tooltips.remove(this.tooltip);
                this.tooltip = null;
            }
        } else {
            this.icon = null;
            
            if (this.tooltip != null) {
                MainLoop.tooltips.remove(this.tooltip);
                this.tooltip = null;
            }
        }
    }
    
    public Class getType() {
        return this.type;
    }
    
    public int getBelonging() {
        return this.belonging;
    }
    
    public TooltipHUD getTooltip() {
        return this.tooltip;
    }
    
    public TooltipHUD getUsualTooltip() {
        return new TooltipHUD(10, 10, ((Cargo)this.inventory.getItem(this.pos)).getTooltip(), 30, this);
    }

    @Override
    public double getMiddleX() {
        return this.middleX;
    }

    @Override
    public double getMiddleY() {
        return this.middleY;
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
