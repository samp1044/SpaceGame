/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Interfaces.Cargo;
import Interfaces.SpecialObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Inventory;
import support.Resources;
import support.Settings;
import units.Battery;
import units.ItemDrop;
import units.Reactor;
import units.Shield;
import units.Thruster;
import units.Turret;
import units.Weapon;
import units.WeaponBase;
import utils.ObjectList;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class InventoryHUD {
    public static boolean open = false;
    
    private int posX = 0;
    private int posY = 0;
    private int width;
    private int height;
    private int differenceX;
    private int differenceY;
    
    private BufferedImage window;
    private BufferedImage selectionBar;
    private BufferedImage buttonLine;
    private BufferedImage buttonLineSelected;
    private BufferedImage playerShipIcon;
    private BufferedImage playerShipIconBig;
    private BufferedImage slotImage;
    private BufferedImage slotActiveImage;
    
    private ObjectList cargoSlots;
    private InventorySlot[] weaponSlots;
    private InventorySlot[] specialSlots;
    private InventorySlot[] shipItemSlots;
    private InventorySlot clickedSlot;
    private InventorySlot activeSlot;
    private Inventory hoveringItems;
    private ObjectList itemDrops;
    
    private TooltipHUD tooltip;
    
    private int selected;
    private int selectedWindow;
    private boolean selectedWindowOpen;
    
    private boolean clickHappened;
    private boolean clickRightHappened;
    
    private boolean blockWindowMovement;
    
    public InventoryHUD(int mainWidth,int mainHeight) {
        width = 1470;
        height = 655;
        window = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORYWINDOW, width, height,Resources.SCALE_SMOOTH)[0];
        playerShipIcon = MainLoop.resources.getScaledBufferedImage(MainLoop.playerShip.getIcon(),150, 150,Resources.SCALE_SMOOTH);
        playerShipIconBig = MainLoop.resources.getScaledBufferedImage(MainLoop.playerShip.getIcon(),300, 300,Resources.SCALE_SMOOTH);
        
        posX = (mainWidth - width) / 2;
        posY = (mainHeight - height) / 2;
        
        differenceX = MainLoop.mausX - posX;
        differenceY = MainLoop.mausY - posY;
        
        selectionBar = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORY_SELECTIONBAR, 318, 23,Resources.SCALE_SMOOTH)[0];
        buttonLine = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORY_BUTTONLINE, 119, 5,Resources.SCALE_SMOOTH)[0];
        buttonLineSelected = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORY_BUTTONLINE_SELECTED, 119, 5,Resources.SCALE_SMOOTH)[0];
        
        selected = 0;
        selectedWindow = 0;
        selectedWindowOpen = false;
        
        clickHappened = false;
        clickRightHappened = false;
        
        cargoSlots = new ObjectList();
        
        slotImage = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORYSLOT, 82, 82,Resources.SCALE_SMOOTH)[0];
        slotActiveImage = MainLoop.resources.getScaledBufferedImageField(Resources.UI_INVENTORYSLOT_ACTIVE, 82, 82,Resources.SCALE_SMOOTH)[0];
        
        int horizontal = (MainLoop.playerShip.getCargoSpace().getAvailableSlots() / 11) + 1;
        
        for (int i = 0;i < horizontal;i++) {
            for (int j = 0;j < 11 && (j + (i * 11)) < MainLoop.playerShip.getCargoSpace().getAvailableSlots();j++) {
                InventorySlot inventorySlot = new InventorySlot(64,82,82,(16 * (j + 1)) + (82 * j),(16 * (i + 1)) + (82 * i) + 56, Cargo.class);
                inventorySlot.setInventory(MainLoop.playerShip.getCargoSpace(), j + (11 * i));
                cargoSlots.add(inventorySlot);
            }
        }
        
        weaponSlots = new InventorySlot[MainLoop.playerShip.getWeaponSlots() + MainLoop.playerShip.getTurretSlots()];
        
        for (int i = 0;i < MainLoop.playerShip.getWeaponSlots();i++) {
            weaponSlots[i] = new InventorySlot(1,82,82,(16 * (i + 1)) + (82 * i) + 100, 558, Weapon.class);
            weaponSlots[i].add(new Object[]{MainLoop.playerShip.getWeaponFromSlot(i)});
        }
        
        for (int i = MainLoop.playerShip.getWeaponSlots();i < weaponSlots.length;i++) {
            weaponSlots[i] = new InventorySlot(1,82,82,(16 * (i + 1)) + (82 * i) + 100, 558, Turret.class);
            weaponSlots[i].add(new Object[]{MainLoop.playerShip.getWeaponFromSlot(i)});
        } 
        
        specialSlots = new InventorySlot[3];
        specialSlots[0] = new InventorySlot(1,82,82,1148,558, SpecialObject.class);
        specialSlots[1] = new InventorySlot(1,82,82,1248,558, SpecialObject.class);
        specialSlots[2] = new InventorySlot(1,82,82,1348,558, SpecialObject.class);
        
        shipItemSlots = new InventorySlot[7];
        shipItemSlots[0] = new InventorySlot(1,82,82,1248,170, Shield.class);
        shipItemSlots[1] = new InventorySlot(1,82,82,1248,260, Reactor.class);
        shipItemSlots[2] = new InventorySlot(1,82,82,1248,348, Battery.class);
        shipItemSlots[3] = new InventorySlot(1,82,82,1248,120, Thruster.class);
        shipItemSlots[4] = new InventorySlot(1,82,82,1130,260, Thruster.class);
        shipItemSlots[5] = new InventorySlot(1,82,82,1366,260, Thruster.class);
        shipItemSlots[6] = new InventorySlot(1,82,82,1248,400, Thruster.class);
        
        shipItemSlots[0].add(MainLoop.playerShip.getShield());
        shipItemSlots[1].add(MainLoop.playerShip.getReactor());
        shipItemSlots[2].add(MainLoop.playerShip.getBattery());
        
        shipItemSlots[6].add(MainLoop.playerShip.getForwardThruster());
        shipItemSlots[4].add(MainLoop.playerShip.getLeftThruster());
        shipItemSlots[5].add(MainLoop.playerShip.getRightThruster());
        shipItemSlots[3].add(MainLoop.playerShip.getBackwardThruster());
        
        clickedSlot = null;
        activeSlot = null;
        
        hoveringItems = new Inventory(1,64);
        itemDrops = new ObjectList();
        
        blockWindowMovement = false;
        
        this.tooltip = null;
    }
    
    public void update() {
        boolean slotClicked = false;
        clickedSlot = null;
        activeSlot = null;
        
        if (Settings.ESCAPE_DOWN) {
            InventoryHUD.open = false;
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
                for (int i = 0;i < weaponSlots.length;i++) {
                    if (weaponSlots[i].isClicked(posX, posY)) {
                        clickedSlot = weaponSlots[i];
                        slotClicked = true;
                        break;
                    } else if (weaponSlots[i].isActive(posX, posY)) {
                        activeSlot = weaponSlots[i];
                        blockWindowMovement = true;
                    }
                }
            }

            if (!slotClicked) {
                for (int i = 0;i < specialSlots.length;i++) {
                    if (specialSlots[i].isClicked(posX, posY)) {
                        clickedSlot = specialSlots[i];
                        slotClicked = true;
                        break;
                    } else if (specialSlots[i].isActive(posX, posY)) {
                        activeSlot = specialSlots[i];
                        blockWindowMovement = true;
                    }
                }
            }
            
            if (!slotClicked) {
                InventorySlot[] currentSlots = null;

                if (selectedWindow == 1) {
                    currentSlots = new InventorySlot[1];
                    currentSlots[0] = shipItemSlots[0];
                } else if (selectedWindow == 2) {
                    currentSlots = new InventorySlot[1];
                    currentSlots[0] = shipItemSlots[1];
                } else if (selectedWindow == 3) {
                    currentSlots = new InventorySlot[1];
                    currentSlots[0] = shipItemSlots[2];
                } else if (selectedWindow >= 4 && selectedWindow <= 7) {
                    currentSlots = new InventorySlot[4];
                    currentSlots[0] = shipItemSlots[3];
                    currentSlots[1] = shipItemSlots[4];
                    currentSlots[2] = shipItemSlots[5];
                    currentSlots[3] = shipItemSlots[6];
                }

                if (currentSlots != null) {
                    for (int i = 0;i < currentSlots.length;i++) {
                        if (currentSlots[i].isClicked(posX, posY)) {
                            clickedSlot = currentSlots[i];
                            slotClicked = true;
                            break;
                        } else if (currentSlots[i].isActive(posX, posY)) {
                            activeSlot = currentSlots[i];
                            blockWindowMovement = true;
                        }
                    }
                }
            }

            if (!slotClicked) {
                clickedSlot = null;
            } else {
                if (hoveringItems.isEmpty()) {
                    if (Settings.MOUSE_LEFT_DOWN) {
                        if (Settings.SHIFT_DOWN && !clickedSlot.isEmpty()) {
                            if (clickedSlot.getType() == Cargo.class) {
                                if (clickedSlot.getItem() instanceof Weapon) {
                                    for (int i = 0;i < weaponSlots.length;i++) {
                                        if (weaponSlots[i].getType() == Weapon.class && weaponSlots[i].isEmpty()) {
                                            clickedSlot.add(weaponSlots[i].add(clickedSlot.removeItems()));
                                            break;
                                        }
                                    }
                                } else if (clickedSlot.getItem() instanceof Turret) {
                                    for (int i = 0;i < weaponSlots.length;i++) {
                                        if (weaponSlots[i].getType() == Turret.class && weaponSlots[i].isEmpty()) {
                                            clickedSlot.add(weaponSlots[i].add(clickedSlot.removeItems()));
                                            break;
                                        }
                                    }
                                } else if (clickedSlot.getItem() instanceof Shield && shipItemSlots[0].isEmpty()) {
                                    clickedSlot.add(shipItemSlots[0].add(clickedSlot.removeItems()));
                                } else if (clickedSlot.getItem() instanceof Reactor && shipItemSlots[1].isEmpty()) {
                                    clickedSlot.add(shipItemSlots[1].add(clickedSlot.removeItems()));
                                } else if (clickedSlot.getItem() instanceof Battery && shipItemSlots[2].isEmpty()) {
                                    clickedSlot.add(shipItemSlots[2].add(clickedSlot.removeItems()));
                                } else if (clickedSlot.getItem() instanceof Thruster) {
                                    if(shipItemSlots[3].isEmpty()) {
                                        clickedSlot.add(shipItemSlots[3].add(clickedSlot.removeItems()));
                                    } else if(shipItemSlots[4].isEmpty()) {
                                        clickedSlot.add(shipItemSlots[4].add(clickedSlot.removeItems()));
                                    } else if(shipItemSlots[5].isEmpty()) {
                                        clickedSlot.add(shipItemSlots[5].add(clickedSlot.removeItems()));
                                    } else if(shipItemSlots[6].isEmpty()) {
                                        clickedSlot.add(shipItemSlots[6].add(clickedSlot.removeItems()));
                                    }
                                } else if (clickedSlot.getItem() instanceof SpecialObject) {
                                    if (specialSlots[0].isEmpty()) {
                                        clickedSlot.add(specialSlots[0].add(clickedSlot.removeItems()));
                                    } else if (specialSlots[1].isEmpty()) {
                                        clickedSlot.add(specialSlots[1].add(clickedSlot.removeItems()));
                                    } else if (specialSlots[2].isEmpty()) {
                                        clickedSlot.add(specialSlots[2].add(clickedSlot.removeItems()));
                                    }
                                }
                            } else {
                                if (cargoSlots.getElementAt(0) != null) {
                                    clickedSlot.add(((InventorySlot) cargoSlots.getElementAt(0)).getInventory().addItems(clickedSlot.removeItems()));
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

            if (selectedWindowOpen) {
                if (selected == 8 && Settings.MOUSE_LEFT_DOWN) {
                    selectedWindowOpen = false;
                    selectedWindow = 0;
                    selected = 0;
                }
            } else {
                if (selected > 0 && selected < 8 && Settings.MOUSE_LEFT_DOWN) {
                    selectedWindowOpen = true;
                    selectedWindow = selected;
                    selected = 0;
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
                for (int i = 0;i < weaponSlots.length;i++) {
                    if (weaponSlots[i].isActive(posX, posY)) {
                        activeSlot = weaponSlots[i];
                        blockWindowMovement = true;
                        break;
                    }
                }
            }

            if (!blockWindowMovement) {
                for (int i = 0;i < specialSlots.length;i++) {
                    if (specialSlots[i].isActive(posX, posY)) {
                        activeSlot = specialSlots[i];
                        blockWindowMovement = true;
                        break;
                    }
                }
            }
            
            if (!blockWindowMovement) {
                InventorySlot[] currentSlots = null;

                if (selectedWindow == 1) {
                    currentSlots = new InventorySlot[1];
                    currentSlots[0] = shipItemSlots[0];
                } else if (selectedWindow == 2) {
                    currentSlots = new InventorySlot[1];
                    currentSlots[0] = shipItemSlots[1];
                } else if (selectedWindow == 3) {
                    currentSlots = new InventorySlot[1];
                    currentSlots[0] = shipItemSlots[2];
                } else if (selectedWindow >= 4 && selectedWindow <= 7) {
                    currentSlots = new InventorySlot[4];
                    currentSlots[0] = shipItemSlots[3];
                    currentSlots[1] = shipItemSlots[4];
                    currentSlots[2] = shipItemSlots[5];
                    currentSlots[3] = shipItemSlots[6];
                }

                if (currentSlots != null) {
                    for (int i = 0;i < currentSlots.length;i++) {
                        if (currentSlots[i].isActive(posX, posY)) {
                            activeSlot = currentSlots[i];
                            blockWindowMovement = true;
                            break;
                        }
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
        
        if (slotClicked) {
            MainLoop.playerShip.setShield((Shield)shipItemSlots[0].getItem());
            MainLoop.playerShip.setReactor((Reactor)shipItemSlots[1].getItem());
            MainLoop.playerShip.setBattery((Battery)shipItemSlots[2].getItem());
            MainLoop.playerShip.setForwardThruster((Thruster)shipItemSlots[6].getItem());
            MainLoop.playerShip.setLeftThruster((Thruster)shipItemSlots[4].getItem());
            MainLoop.playerShip.setRightThruster((Thruster)shipItemSlots[5].getItem());
            MainLoop.playerShip.setBackwardThruster((Thruster)shipItemSlots[3].getItem());
            
            for (int i = 0;i < weaponSlots.length;i++) {
                MainLoop.playerShip.setWeapon((WeaponBase)weaponSlots[i].getItem(), i);
            }
        }
        
        if (InventoryHUD.open == false) {
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
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(window, posX, posY, null);
        
        Font defaultFont = g2d.getFont();
        Color defaultColor = g2d.getColor();
        
        //Überschrift
        Font customFont = new Font("Helvetica",Font.BOLD,30);
        g2d.setFont(customFont);
        
        g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_INVENTORYHEADER), posX + 16, posY + 40);
        
        //Unterüberschriften
        customFont = new Font("Helvetica",Font.BOLD,20);
        g2d.setFont(customFont);
        
        g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_WEAPONSLOTHEADER), posX + 10, posY + 570);
        g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SPECIALSLOTHEADER), posX + 1340, posY + 540);
        
        //SelectionBar
        if (selected > 0 && selected < 8 && selectedWindowOpen == false) {
            int offsetY = 317;
            
            if (selected == 1) {
                offsetY += (25 * 0);
            } else if (selected == 2) {
                offsetY += (25 * 1);
            } else if (selected == 3) {
                offsetY += (25 * 2);
            } else if (selected == 4) {
                offsetY += (25 * 3);
            } else if (selected == 5) {
                offsetY += (25 * 4);
            } else if (selected == 6) {
                offsetY += (25 * 5);
            } else if (selected == 7) {
                offsetY += (25 * 6);
            }
            
            customFont = new Font("Helvetica",Font.BOLD,15);
            g2d.setFont(customFont);
            g2d.setColor(Color.YELLOW);
            
            g2d.drawImage(selectionBar, posX + 1120, posY + offsetY,null);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SELECTIONBAR_OPEN), posX + 1125, posY + offsetY + 16);
            
            g2d.setColor(defaultColor);
        } else if (selectedWindowOpen == true) {
            customFont = new Font("Helvetica",Font.BOLD,16);
            g2d.setFont(customFont);

            if (selected < 8) {
                g2d.drawImage(buttonLine, posX + 1117, posY + 514,null);
                g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SELECTION_BACK), posX + 1121, posY + 537);
            } else {
                g2d.setColor(Color.YELLOW);
                g2d.drawImage(buttonLineSelected, posX + 1117, posY + 514,null);
                g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SELECTION_BACK), posX + 1121, posY + 537);
                g2d.setColor(defaultColor);
            }
        }
        
        //Ship Info
        if (selectedWindowOpen == false) {
            g2d.drawImage(playerShipIcon, posX + 1220, posY + 80, null);
            
            customFont = new Font("Helvetica",Font.BOLD,16);
            g2d.setFont(customFont);
            
            int customOffset = 1200;
            int customOffsetY = 260;

            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPMASS), posX + customOffset, posY + customOffsetY + 25 * 0);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPARMOR), posX + customOffset, posY + customOffsetY + 25 * 1);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPHULL), posX + customOffset, posY + customOffsetY + 25 * 2);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPSHIELD), posX + customOffset, posY + customOffsetY + 25 * 3);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPREACTOR), posX + customOffset, posY + customOffsetY + 25 * 4);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBATTERY), posX + customOffset, posY + customOffsetY + 25 * 5);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPFRONTTHRUST), posX + customOffset, posY + customOffsetY + 25 * 6);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPLEFTTHRUST), posX + customOffset, posY + customOffsetY + 25 * 7);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPRIGHTTHRUST), posX + customOffset, posY + customOffsetY + 25 * 8);
            g2d.drawString(StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBACKTHRUST), posX + customOffset, posY + customOffsetY + 25 * 9);

            customOffset = 1320;
            
            g2d.drawString((int)MainLoop.playerShip.getMass()+" "+StringSelector.getSring(StringSelector.INVENTORYUI_SHIPMASSUNIT), posX + customOffset, posY + customOffsetY + 25 * 0);
            g2d.drawString((int)MainLoop.playerShip.getArmor()+"", posX + customOffset, posY + customOffsetY + 25 * 1);
            g2d.drawString((int)MainLoop.playerShip.getHealth()+" / "+(int)MainLoop.playerShip.getMaxHealth(), posX + customOffset, posY + customOffsetY + 25 * 2);
            g2d.drawString((int)MainLoop.playerShip.getShieldCurrentEnergyStatus("All") + " / "+(int)(MainLoop.playerShip.getShieldCapacity() * 4) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPSHIELDUNIT), posX + customOffset, posY + customOffsetY + 25 * 3);
            g2d.drawString((int)MainLoop.playerShip.getReactorOutput()+ " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPREACTORUNIT), posX + customOffset, posY + customOffsetY + 25 * 4);
            g2d.drawString((int)MainLoop.playerShip.getEnergy() + " / " + (int)MainLoop.playerShip.getEnergyCapacity() + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBATTERYUNIT), posX + customOffset, posY + customOffsetY + 25 * 5);
            g2d.drawString((int)(MainLoop.playerShip.getFrontThrust() / 1000) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPFRONTTHRUSTUNIT), posX + customOffset, posY + customOffsetY + 25 * 6);
            g2d.drawString((int)(MainLoop.playerShip.getLeftThrust() / 1000) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPLEFTTHRUSTUNIT), posX + customOffset, posY + customOffsetY + 25 * 7);
            g2d.drawString((int)(MainLoop.playerShip.getRightThrust() / 1000) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPRIGHTTHRUSTUNIT), posX + customOffset, posY + customOffsetY + 25 * 8);
            g2d.drawString((int)(MainLoop.playerShip.getBackThrust() / 1000) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBACKTHRUSTUNIT), posX + customOffset, posY + customOffsetY + 25 * 9);
        } else {
            String header = "";
            InventorySlot[] currentSlots = null;
            
            customFont = new Font("Helvetica",Font.BOLD,20);
            g2d.setFont(customFont);
            
            if (selectedWindow == 1) {
                header = StringSelector.getSring(StringSelector.INVENTORYUI_SHIPSHIELD);
                
                currentSlots = new InventorySlot[1];
                currentSlots[0] = shipItemSlots[0];
            } else if (selectedWindow == 2) {
                header = StringSelector.getSring(StringSelector.INVENTORYUI_SHIPREACTOR);
                
                currentSlots = new InventorySlot[1];
                currentSlots[0] = shipItemSlots[1];
            } else if (selectedWindow == 3) {
                header = StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBATTERY);
                
                currentSlots = new InventorySlot[1];
                currentSlots[0] = shipItemSlots[2];
            } else if (selectedWindow >= 4 && selectedWindow <= 7) {
                header = StringSelector.getSring(StringSelector.INVENTORYUI_SELECTION_HEADERTHRUST);
                
                currentSlots = new InventorySlot[4];
                currentSlots[0] = shipItemSlots[3];
                currentSlots[1] = shipItemSlots[4];
                currentSlots[2] = shipItemSlots[5];
                currentSlots[3] = shipItemSlots[6];
            }
            
            g2d.drawString(header,posX + 1124,posY + 80);
            g2d.drawImage(playerShipIconBig, posX + 1140, posY + 150, null);
            
            customFont = new Font("Helvetica",Font.BOLD,10);
            g2d.setFont(customFont);
            
            for (int i = 0;i < currentSlots.length;i++) {
                currentSlots[i].draw(g2d, slotImage, slotActiveImage, posX, posY,!this.hoveringItems.isEmpty());
            }
        }
        
        //Slots
        customFont = new Font("Helvetica",Font.BOLD,10);
        g2d.setFont(customFont);
        
        for (int i = 0;i < cargoSlots.size();i++) {
            InventorySlot slot = (InventorySlot)cargoSlots.getElementAt(i);
            slot.draw(g2d, slotImage, slotActiveImage, posX, posY,!this.hoveringItems.isEmpty());
        }
        
        for (int i = 0;i < weaponSlots.length;i++) {
            weaponSlots[i].draw(g2d, slotImage, slotActiveImage, posX, posY,!this.hoveringItems.isEmpty());
        }
        
        for (int i = 0;i < specialSlots.length;i++) {
            specialSlots[i].draw(g2d, slotImage, slotActiveImage, posX, posY,!this.hoveringItems.isEmpty());
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
    
    private void checkSelection() {
        int mausX = MainLoop.mausX;
        int mausY = MainLoop.mausY;
        
        if (mausX > posX + 1117 && mausX < posX + 1236 && mausY >= posY + 514 && mausY < posY + 542) {
            selected = 8;
        } else if (mausX > posX + 1180 && mausX < posX + 1430) {
            int offsetY = 317;
                    
            if (mausY >= posY + offsetY + (25 * 0) && mausY < posY + offsetY + (25 * 1)) {
                selected = 1;
            } else if (mausY >= posY + offsetY + (25 * 1) && mausY < posY + offsetY + (25 * 2)) {
                selected = 2;
            } else if (mausY >= posY + offsetY + (25 * 2) && mausY < posY + offsetY + (25 * 3)) {
                selected = 3;
            } else if (mausY >= posY + offsetY + (25 * 3) && mausY < posY + offsetY + (25 * 4)) {
                selected = 4;
            } else if (mausY >= posY + offsetY + (25 * 4) && mausY < posY + offsetY + (25 * 5)) {
                selected = 5;
            } else if (mausY >= posY + offsetY + (25 * 5) && mausY < posY + offsetY + (25 * 6)) {
                selected = 6;
            } else if (mausY >= posY + offsetY + (25 * 6) && mausY < posY + offsetY + (25 * 7)) {
                selected = 7;
            } else {
                selected = 0;
            }
        } else {
            selected = 0;
        }
    }
}


