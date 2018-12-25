/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.Cargo;
import java.awt.Image;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Resources;
import utils.Utils;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Battery implements Cargo {
    double capacity;
    double energy;
    
    private BufferedImage icon;
    private String name;
    
    public Battery(double capacity) {
        this.capacity = capacity;
        this.energy = capacity;
        
        icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_BATTERY, 50, 50,Resources.SCALE_SMOOTH)[0];
        name = StringSelector.getSring(StringSelector.BATTERY_NAME);
    }
    
    public double energyLoss(double amount) {
        if (this.energy - amount >= 0) {
            this.energy -= amount;
            return amount;
        } else {
            amount = this.energy;
            this.energy = 0;
            return amount;
        }
    }
    
    public void energyCharge(double amount) {
        if (this.energy + amount <= this.capacity) {
            this.energy += amount;
        } else {
            this.energy = this.capacity;
        }
    }
    
    public double getCapacity() {
        return this.capacity;
    }
    
    public double getAvailableEnergy() {
        return this.energy;
    }

    @Override
    public BufferedImage getIcon() {
        return this.icon;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getTooltip() {
        int leftPartLength = 20;
        String returnValue = "";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.BATTERY_CAPACITY), leftPartLength);
        returnValue += (this.capacity) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPBATTERYUNIT) + "\n";
        
        return returnValue;
    }
}
