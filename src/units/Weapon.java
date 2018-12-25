/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.WeaponObject;
import main.MainLoop;
import support.Resources;
import static units.WeaponBase.LASER;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Weapon extends WeaponBase implements WeaponObject {
    public Weapon(int weaponType, int width, int height, int xOffset, int yOffset, int shipMiddleX, int shipMiddleY, double rotationDegree, long coolDown, int shootType, double energyCost, int side, boolean below, Bullet bullet) {
        super(weaponType, width, height, xOffset, yOffset, shipMiddleX, shipMiddleY, rotationDegree, coolDown, shootType, energyCost, side,bullet);
        
        switch (weaponType) {
            case LASER:
                img = MainLoop.resources.getScaledBufferedImageField(Resources.WEAPON_LASER1, this.width, this.height,Resources.SCALE_SMOOTH);
                shootAnim = MainLoop.resources.getScaledBufferedImageField(Resources.WEAPON_LASER1_SHOOTANI, this.width, this.height,Resources.SCALE_SMOOTH);
                
                icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_WEAPONLASER1, 50, 50,Resources.SCALE_SMOOTH)[0];
                this.animationDelay = 50;
                this.name = StringSelector.getSring(StringSelector.WEAPON_WEAPONLASER1);
                break;
            case PLASMA_GUN:
                img = MainLoop.resources.getScaledBufferedImageField(Resources.WEAPON_PLASMA_GUN, this.width, this.height,Resources.SCALE_SMOOTH);
                shootAnim = MainLoop.resources.getScaledBufferedImageField(Resources.WEAPON_LASER1_SHOOTANI, this.width, this.height,Resources.SCALE_SMOOTH);
                
                icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_WEAPONLASER1, 50, 50,Resources.SCALE_SMOOTH)[0];
                this.animationDelay = 50;
                this.name = StringSelector.getSring(StringSelector.WEAPON_WEAPONLASER1);
                break;
            case PLASMA_LANCE:
                img = MainLoop.resources.getScaledBufferedImageField(Resources.WEAPON_PLASMA_LANCE, this.width, this.height,Resources.SCALE_SMOOTH);
                shootAnim = MainLoop.resources.getScaledBufferedImageField(Resources.WEAPON_PLASMA_LANCE_SHOOTANI, this.width, this.height,Resources.SCALE_SMOOTH);
                
                icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_WEAPONPLASMALANCE, 50, 50,Resources.SCALE_SMOOTH)[0];
                this.animationDelay = 30;
                this.name = StringSelector.getSring(StringSelector.WEAPON_PLASMALANCE);
                break;
        }
        
        this.below = below;
    }
}
