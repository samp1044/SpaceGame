/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Interfaces.Cargo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Inventory;
import support.Resources;
import support.Settings;
import units.ItemDrop;
import utils.ObjectList;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class TransferHUD {
    private static final int INVENTORY = 1;
    private static final int ITEMDROP = 2;
    
    private int posX = 0;
    private int posY = 0;
    private int width;
    private int height;
    private int differenceX;
    private int differenceY;
    
    private BufferedImage window;
    private BufferedImage buttonLine;
    private BufferedImage buttonSelected_TakeAll;
    private BufferedImage buttonSelected_Ok;
    private BufferedImage playerShipIcon;
    private BufferedImage itemDropIcon;
    private BufferedImage slotImage;
    private BufferedImage slotActiveImage;
    
    private ObjectList cargoSlots;
    private ObjectList itemDropSlots;
    private InventorySlot clickedSlot;
    private InventorySlot activeSlot;
    private Inventory hoveringItems;
    private ObjectList itemDrops;
    
    private TooltipHUD tooltip;
    
    private boolean clickHappened;
    private boolean clickRightHappened;
    
    private boolean blockWindowMovement;
    
    private int selected;
    
    public static boolean open = false;
    
    public TransferHUD(ItemDrop itemDrop) {
        width = 1470;
        height = 655;
        
        this.window = MainLoop.resources.getScaledBufferedImageField(Resources.UI_TRANSFERWINDOW, width, height,Resources.SCALE_SMOOTH)[0];
        
        buttonLine = MainLoop.resources.getScaledBufferedImageField(Resources.UI_TRANSFERBUTTON_LINE, 5, 48,Resources.SCALE_SMOOTH)[0];
        buttonSelected_TakeAll = MainLoop.resources.getScaledBufferedImageField(Resources.UI_TRANSFERBUTTON_ACTIVE, 235, 48,Resources.SCALE_SMOOTH)[0];
        buttonSelected_Ok = MainLoop.resources.getScaledBufferedImageField(Resources.UI_TRANSFERBUTTON_ACTIVE, 210, 48,Resources.SCALE_SMOOTH)[0];
        
        playerShipIcon = MainLoop.resources.getScaledBufferedImage(MainLoop.playerShip.getIcon(), 38, 38,Resources.SCALE_SMOOTH);
        itemDropIcon = MainLoop.resources.getScaledBufferedImageField(Resources.DROP, 38, 38,Resources.SCALE_SMOOTH)[0];
        
        slotImage = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORYSLOT, 82, 82,Resources.SCALE_SMOOTH)[0];
        slotActiveImage = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORYSLOT_ACTIVE, 82, 82,Resources.SCALE_SMOOTH)[0];
        
        posX = (MainLoop.width - width) / 2;
        posY = (MainLoop.height - height) / 2;
        
        differenceX = MainLoop.mausX - posX;
        differenceY = MainLoop.mausY - posY;
        
        clickHappened = false;
        clickRightHappened = false;
        
        selected = 0;
        
        cargoSlots = new ObjectList();
        itemDropSlots = new ObjectList();
        hoveringItems = new Inventory(1,64);
        itemDrops = new ObjectList();
        
        int horizontal = (itemDrop.getInventory().getAvailableSlots() / 7) + 1;
        
        for (int i = 0;i < horizontal;i++) {
            for (int j = 0;j < 7 && (j + (i * 7)) < itemDrop.getInventory().getAvailableSlots();j++) {
                InventorySlot inventorySlot = new InventorySlot(64,82,82,(16 * (j + 1)) + (82 * j),(16 * (i + 1)) + (82 * i) + 56, Cargo.class,ITEMDROP);
                inventorySlot.setInventory(itemDrop.getInventory(), j + (7 * i));
                itemDropSlots.add(inventorySlot);
            }
        }
        
        horizontal = (MainLoop.playerShip.getCargoSpace().getAvailableSlots() / 7) + 1;
        
        for (int i = 0;i < horizontal;i++) {
            for (int j = 0;j < 7 && (j + (i * 7)) < MainLoop.playerShip.getCargoSpace().getAvailableSlots();j++) {
                InventorySlot inventorySlot = new InventorySlot(64,82,82,740 + (16 * (j + 1)) + (82 * j),(16 * (i + 1)) + (82 * i) + 56, Cargo.class,INVENTORY);
                inventorySlot.setInventory(MainLoop.playerShip.getCargoSpace(), j + (7 * i));
                cargoSlots.add(inventorySlot);
            }
        }
        
        blockWindowMovement = false;
    }
    
    public void update() {
        boolean slotClicked = false;
        clickedSlot = null;
        activeSlot = null;
        
        if (Settings.ESCAPE_DOWN) {
            open = false;
        }
        
        checkSelection();
        
        if (selected > 0) {
            blockWindowMovement = true;
        }
        
        if (!clickHappened && !clickRightHappened) {
            for (int i = 0;i < cargoSlots.size();i++) {
                InventorySlot slot = (InventorySlot)cargoSlots.getElementAt(i);

                if (slot.isClicked(posX, posY)) {
                    clickedSlot = slot;
                    slotClicked = true;
                    break;
                } else if (slot.isActive(posX, posY)) {
                    activeSlot = slot;
                    blockWindowMovement = true;
                }
            }
            
            if (!slotClicked) {
                for (int i = 0;i < itemDropSlots.size();i++) {
                    InventorySlot slot = (InventorySlot)itemDropSlots.getElementAt(i);

                    if (slot.isClicked(posX, posY)) {
                        clickedSlot = slot;
                        slotClicked = true;
                        break;
                    } else if (slot.isActive(posX, posY)) {
                        activeSlot = slot;
                        blockWindowMovement = true;
                    }
                }
            }

            if (!slotClicked) {
                clickedSlot = null;
            } else {
                if (hoveringItems.isEmpty()) {
                    if (Settings.MOUSE_LEFT_DOWN) {
                        if (Settings.SHIFT_DOWN) {
                            if(clickedSlot.getBelonging() == INVENTORY) {
                                if (itemDropSlots.getElementAt(0) != null) {
                                    clickedSlot.add(((InventorySlot)itemDropSlots.getElementAt(0)).getInventory().addItems(clickedSlot.removeItems()));
                                }
                            } else {
                                if (cargoSlots.getElementAt(0) != null) {
                                    clickedSlot.add(((InventorySlot)cargoSlots.getElementAt(0)).getInventory().addItems(clickedSlot.removeItems()));
                                }
                            }
                        } else {
                            hoveringItems.addItems(0, clickedSlot.removeItems());
                        }
                    } else if (Settings.MOUSE_RIGHT_DOWN) {
                        hoveringItems.addItems(0, clickedSlot.removeHalfItems());
                    }
                } else if (clickedSlot.isEmpty()) {
                    if (clickedSlot.getType().isInstance(hoveringItems.getItem(0))) {
                        if (Settings.MOUSE_LEFT_DOWN) {
                            clickedSlot.add(hoveringItems.removeAllItems(0));
                        } else if (Settings.MOUSE_RIGHT_DOWN) {
                            clickedSlot.add(hoveringItems.removeItem(0));
                        }
                    } else {
                        this.tooltip = new TooltipHUD(10, 10, StringSelector.getSring(StringSelector.WRONG_TYPE_MESSAGE), 350, null);
                        MainLoop.tooltips.add(this.tooltip);
                    }
                } else if (hoveringItems.getItem(0).equals(clickedSlot.getItem())) {
                    if (Settings.MOUSE_LEFT_DOWN) {
                        hoveringItems.addItems(0,clickedSlot.add(hoveringItems.removeAllItems(0)));
                    } else if (Settings.MOUSE_RIGHT_DOWN) {
                        hoveringItems.addItem(0,clickedSlot.add(hoveringItems.removeItem(0)));
                    }
                } else {
                    if (clickedSlot.getType().isInstance(hoveringItems.getItem(0))) {
                        if (Settings.MOUSE_LEFT_DOWN) {
                            Inventory temporary = new Inventory(1,hoveringItems.getMaxStack());
                            
                            temporary.addItems(hoveringItems.removeAllItems(0));
                            hoveringItems.addItems(0,clickedSlot.removeItems());
                            clickedSlot.add(temporary.removeAllItems(0));
                        }
                    } else {
                        this.tooltip = new TooltipHUD(10, 10, StringSelector.getSring(StringSelector.WRONG_TYPE_MESSAGE), 700, null);
                        this.tooltip = new TooltipHUD(-this.tooltip.getWidth() - 10, 10, StringSelector.getSring(StringSelector.WRONG_TYPE_MESSAGE), 350, null);
                        MainLoop.tooltips.add(this.tooltip);
                    }
                }
            }
            
            if (!slotClicked && Settings.MOUSE_LEFT_DOWN && selected > 0) {
                if (selected == 1) {
                    for (int i = 0;i < itemDropSlots.size();i++) {
                        InventorySlot slot = (InventorySlot)itemDropSlots.getElementAt(i);
                        MainLoop.playerShip.addInventory(slot.getInventory());
                    }
                    
                    MainLoop.ui_click_happened = true;
                } else if (selected == 2) {
                    open = false;
                    
                    MainLoop.ui_click_happened = true;
                }
            }
        } else {
            if (!blockWindowMovement) {
                for (int i = 0;i < cargoSlots.size();i++) {
                    InventorySlot slot = (InventorySlot)cargoSlots.getElementAt(i);

                    if (slot.isActive(posX, posY)) {
                        activeSlot = slot;
                        blockWindowMovement = true;
                        break;
                    }
                }
            }
            
            if (!blockWindowMovement) {
                for (int i = 0;i < itemDropSlots.size();i++) {
                    InventorySlot slot = (InventorySlot)itemDropSlots.getElementAt(i);

                    if (slot.isActive(posX, posY)) {
                        activeSlot = slot;
                        blockWindowMovement = true;
                        break;
                    }
                }
            }
        }
        
        //Fenster verschiebe Funktion
        if (Settings.MOUSE_LEFT_DOWN && !slotClicked && !blockWindowMovement) { //If linker Mausbutton down
            if (MainLoop.mausX > posX && MainLoop.mausX < posX + width && MainLoop.mausY > posY && MainLoop.mausY < posY + height) {    //If Maus innerhalb des Fensters
                if (MainLoop.mausX - posX != differenceX || MainLoop.mausY - posY != differenceY) { //If Veränderung der differenz maus X/Y zu FensterPos X/Y
                    posX = MainLoop.mausX - differenceX;    //Wenn ja ausrechnen der neuen FensterPos mit maus minus differenz
                    posY = MainLoop.mausY - differenceY;
                }
            } else if (!hoveringItems.isEmpty()) {
                itemDrops.add(hoveringItems.removeAllItems(0));
            }
        }
        
        differenceX = MainLoop.mausX - posX;    //Aktualisierung der aktuellen Differenz von Maus X/Y zu Fenster pos X/Y
        differenceY = MainLoop.mausY - posY;
        
        if (Settings.MOUSE_LEFT_DOWN) {
            clickHappened = true;
        } else {
            clickHappened = false;
            blockWindowMovement = false;
        }
        
        if (Settings.MOUSE_RIGHT_DOWN) {
            clickRightHappened = true;
        } else {
            clickRightHappened = false;
        }
        
        if (activeSlot != null && this.hoveringItems != null && !activeSlot.isEmpty()) {
            if (activeSlot.getType().isInstance(hoveringItems.getItem(0)) && activeSlot.getTooltip() != null) {
                if (this.tooltip == null) {
                    this.tooltip = new TooltipHUD(10, 10, ((Cargo)hoveringItems.getItem(0)).getTooltip(), 30, null);
                    this.tooltip = new TooltipHUD(-this.tooltip.getWidth() - 10, 10, ((Cargo)hoveringItems.getItem(0)).getTooltip(), 30, null);
                    MainLoop.tooltips.add(this.tooltip);
                } else {
                    this.tooltip.renewLifeTime();
                    
                    if (this.tooltip.textEquals(StringSelector.getSring(StringSelector.WRONG_TYPE_MESSAGE)) || !this.tooltip.textEquals(((Cargo)hoveringItems.getItem(0)).getTooltip())) {
                        this.tooltip = null;
                    }
                }
            } else {
                this.tooltip = null;
            }
        }
        
        if (TransferHUD.open == false) {
            destroy();
        }
    }
    
    public void destroy() {
        if (itemDrops.size() > 0 || !hoveringItems.isEmpty()) {
            int number = itemDrops.size();
            
            if (!hoveringItems.isEmpty()) {
                number += 1;
            }
            
            ItemDrop drop = new ItemDrop(new Inventory(number,64),MainLoop.playerShip.getMiddleX(),MainLoop.playerShip.getMiddleY() + MainLoop.playerShip.getHeight());

            for (int i = 0;i < itemDrops.size();i++) {
                drop.addItems(i,(Object[])itemDrops.getElementAt(i));
            }
            
            if (!hoveringItems.isEmpty()) {
                drop.addItems(number - 1, hoveringItems.getAllItems(0));
            }
            
            MainLoop.drops.add(drop);
        }
    }
    
    private void checkSelection() {
        int mausX = MainLoop.mausX;
        int mausY = MainLoop.mausY;
        
        if (mausY > posY + 602 && mausY < posY + 649) {
            if (mausX >= posX + 1015 && mausX < posX + 1247) {
                selected = 1;
            } else if (mausX >= posX + 1255 && mausX < posX + 1464 ){
                selected = 2;
            }
        } else {
            selected = 0;
        }
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(window, posX, posY, null);
        
        g2d.drawImage(itemDropIcon, posX + 10, posY + 10, null);
        g2d.drawImage(playerShipIcon, posX + 740, posY + 10, null);
        
        Font defaultFont = g2d.getFont();
        Color defaultColor = g2d.getColor();
        
        //Überschrift
        Font customFont = new Font("Helvetica",Font.BOLD,26);
        g2d.setFont(customFont);
        
        g2d.drawString(StringSelector.getSring(StringSelector.ITEMDROP), posX + 56, posY + 38);
        g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_INVENTORYHEADER), posX + 735 + 56, posY + 38);
        
        //Buttons
        customFont = new Font("Helvetica",Font.BOLD,20);
        g2d.setFont(customFont);
        
        g2d.drawImage(this.buttonLine, posX + 1010, posY + 602, null);
        g2d.drawImage(this.buttonLine, posX + 1250, posY + 602, null);
        
        if (selected != 0) {
            if (selected == 1) {
                g2d.drawImage(buttonSelected_TakeAll, posX + 1015,posY + 602, null);
                g2d.drawString(StringSelector.getSring(StringSelector.TRANSFER_BUTTON_OK), posX + 1336, posY + 633);
                
                g2d.setColor(Color.YELLOW);
                g2d.drawString(StringSelector.getSring(StringSelector.TRANSFER_BUTTON_TAKEALL), posX + 1092, posY + 633);
            } else if (selected == 2) {
                g2d.drawImage(buttonSelected_Ok, posX + 1255,posY + 602, null);
                g2d.drawString(StringSelector.getSring(StringSelector.TRANSFER_BUTTON_TAKEALL), posX + 1092, posY + 633);
                
                g2d.setColor(Color.YELLOW);
                g2d.drawString(StringSelector.getSring(StringSelector.TRANSFER_BUTTON_OK), posX + 1336, posY + 633);
            }
            
            g2d.setColor(defaultColor);
        } else {
            g2d.drawString(StringSelector.getSring(StringSelector.TRANSFER_BUTTON_OK), posX + 1336, posY + 633);
            g2d.drawString(StringSelector.getSring(StringSelector.TRANSFER_BUTTON_TAKEALL), posX + 1092, posY + 633);
        }
        
        //Slots
        customFont = new Font("Helvetica",Font.BOLD,10);
        g2d.setFont(customFont);
        
        for (int i = 0;i < cargoSlots.size();i++) {
            InventorySlot slot = (InventorySlot)cargoSlots.getElementAt(i);
            slot.draw(g2d, slotImage, slotActiveImage, posX, posY,!this.hoveringItems.isEmpty());
        }
        
        for (int i = 0;i < itemDropSlots.size();i++) {
            InventorySlot slot = (InventorySlot)itemDropSlots.getElementAt(i);
            slot.draw(g2d, slotImage, slotActiveImage, posX, posY,!this.hoveringItems.isEmpty());
        }
        
        if (!hoveringItems.isEmpty()) {
            String number = "";
            
            if (hoveringItems.getNumberOfItemsInSlot(0) > 1) {
                number = hoveringItems.getNumberOfItemsInSlot(0) + " ";
            }
            
            g2d.drawImage(((Cargo)hoveringItems.getItem(0)).getIcon(), MainLoop.mausX - 25, MainLoop.mausY - 25,null);
            g2d.drawString(number + " " + ((Cargo)hoveringItems.getItem(0)).getName(), MainLoop.mausX - 38, MainLoop.mausY + 43);
            
        }
        
        g2d.setFont(defaultFont);
        g2d.setColor(defaultColor);
    }
}
