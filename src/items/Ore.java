/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import Interfaces.Cargo;
import java.awt.Image;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Resources;
import static units.WeaponBase.RAY;
import static units.WeaponBase.REPETIER;
import utils.Utils;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Ore implements Cargo {
    private BufferedImage icon;
    private String name;
    private float value;
    
    public Ore() {
        this.icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_ORE, 50, 50,Resources.SCALE_SMOOTH)[0];
        this.name = StringSelector.getSring(StringSelector.ITEM_ORE);
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
    public boolean equals(Object object) {
        boolean isEqual = false;
        Ore ore = null;
        
        if (object == null || !(object instanceof Ore)) {
            return isEqual;
        }
        
        ore = (Ore)object;
        
        if (name.equals(ore.name) && value == ore.value) {
            isEqual = true;
        }
        
        return isEqual;
    }
    
    public String getTooltip() {
        int leftPartLength = 14;
        String returnValue = "";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.ORE_VALUE), leftPartLength);
        returnValue += (this.value) + "\n";
        
        return returnValue;
    }
}
