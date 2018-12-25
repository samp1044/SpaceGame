/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import Interfaces.StringList;
import support.Settings;

/**
 *
 * @author Sami
 */
public class StringSelector {
    public static final int GAMEUI_HULL = 0;
    public static final int GAMEUI_ENERGY = 1;
    
    public static final int INVENTORYUI_INVENTORYHEADER = 2;
    public static final int INVENTORYUI_WEAPONSLOTHEADER = 3;
    public static final int INVENTORYUI_SPECIALSLOTHEADER = 4;
    
    public static final int INVENTORYUI_SHIPMASS = 60;
    public static final int INVENTORYUI_SHIPMASSUNIT = 61;
    public static final int INVENTORYUI_SHIPARMOR = 62;
    public static final int INVENTORYUI_SHIPHULL = 5;
    public static final int INVENTORYUI_SHIPSHIELD = 6;
    public static final int INVENTORYUI_SHIPSHIELDUNIT = 7;
    public static final int INVENTORYUI_SHIPREACTOR = 8;
    public static final int INVENTORYUI_SHIPREACTORUNIT = 9;
    public static final int INVENTORYUI_SHIPBATTERY = 10;
    public static final int INVENTORYUI_SHIPBATTERYUNIT = 11;
    public static final int INVENTORYUI_SHIPFRONTTHRUST = 12;
    public static final int INVENTORYUI_SHIPFRONTTHRUSTUNIT = 13;
    public static final int INVENTORYUI_SHIPLEFTTHRUST = 14;
    public static final int INVENTORYUI_SHIPLEFTTHRUSTUNIT = 15;
    public static final int INVENTORYUI_SHIPRIGHTTHRUST = 16;
    public static final int INVENTORYUI_SHIPRIGHTTHRUSTUNIT = 17;
    public static final int INVENTORYUI_SHIPBACKTHRUST = 18;
    public static final int INVENTORYUI_SHIPBACKTHRUSTUNIT = 19;
    public static final int INVENTORYUI_SELECTIONBAR_OPEN = 20;
    public static final int INVENTORYUI_SELECTION_BACK = 21;
    public static final int INVENTORYUI_SELECTION_HEADERTHRUST = 22;
    
    public static final int TURRET_TURRETDOUBLELASER = 23;
    
    public static final int WEAPON_WEAPONLASER1 = 24;
    public static final int WEAPON_PLASMALANCE = 58;
    
    public static final int ITEM_ORE = 25;
    
    public static final int SHIELD_NAME = 26;
    public static final int BATTERY_NAME = 27;
    public static final int REACTOR_NAME = 28;
    public static final int THRUSTER_NAME_STANDART = 29;
    
    public static final int WEAPON_TYPE = 30;
    public static final int TURRET_TYPE = 31;
    public static final int SHIELD_TYPE = 32;
    public static final int BATTERY_TYPE = 33;
    public static final int REACTOR_TYPE = 34;
    public static final int ORE_TYPE = 35;
    public static final int WRONG_TYPE_MESSAGE = 36;
    public static final int NOT_ENOUGH_SPACE_MESSAGE = 37;
    
    public static final int SHIELD_STRENGTH = 38;
    public static final int SHIELD_RELOAD = 39;
    public static final int SHIELD_RELOAD_DELAY = 40;
    
    public static final int BATTERY_CAPACITY = 41;
    
    public static final int REACTOR_OUTPUT = 42;
    public static final int REACTOR_DELAY = 43;
    
    public static final int THRUSTER_TYPE = 44;
    public static final int THRUSTER_STANDART = 45;
    public static final int THRUSTER_ALIEN = 59;
    public static final int THRUSTER_STRENGTH = 46;
    
    public static final int WEAPON_SHOOTTYPE = 47;
    public static final int WEAPON_DAMAGE = 48;
    public static final int WEAPON_COOLDOWN = 49;
    public static final int WEAPON_ENERGYCOST = 50;
    public static final int WEAPON_REPETIER = 51;
    public static final int WEAPON_RAY = 52;
    
    public static final int ORE_VALUE = 53;
    public static final int RELOAD_TIME_UNIT = 54;
    
    public static final int ITEMDROP = 55;
    public static final int TRANSFER_BUTTON_OK = 56;
    public static final int TRANSFER_BUTTON_TAKEALL = 57;
    
    public static StringList[] stringLists;
    
    public static void init() {
        stringLists = new StringList[1];
        
        stringLists[0] = new StringsEN();
    }
    
    public static String getSring(int field) {
        String string = "";
        
        string = stringLists[Settings.language].getString(field);
        
        return string;
    }
}
