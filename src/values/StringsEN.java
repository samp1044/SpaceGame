/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import Interfaces.StringList;

/**
 *
 * @author Sami
 */
public class StringsEN implements StringList {
    public static String GAMEUI_HULL = "Hull";
    public static String GAMEUI_ENERGY = "Energy";
    
    public static String INVENTORYUI_INVENTORYHEADER = "Inventory";
    public static String INVENTORYUI_WEAPONSLOTHEADER = "Weapons";
    public static String INVENTORYUI_SPECIALSLOTHEADER = "Special-slots";
    
    public static String INVENTORYUI_SHIPMASS = "Mass";
    public static String INVENTORYUI_SHIPMASSUNIT = "t";
    public static String INVENTORYUI_SHIPARMOR = "Armor";
    public static String INVENTORYUI_SHIPHULL = "Hull";
    public static String INVENTORYUI_SHIPSHIELD = "Shield";
    public static String INVENTORYUI_SHIPSHIELDUNIT = "MW";
    public static String INVENTORYUI_SHIPREACTOR = "Reactor";
    public static String INVENTORYUI_SHIPREACTORUNIT = "MW";
    public static String INVENTORYUI_SHIPBATTERY = "Battery";
    public static String INVENTORYUI_SHIPBATTERYUNIT = "MW";
    public static String INVENTORYUI_SHIPFRONTTHRUST = "Front-Thrust";
    public static String INVENTORYUI_SHIPFRONTTHRUSTUNIT = "MN";
    public static String INVENTORYUI_SHIPLEFTTHRUST = "Left-Thrust";
    public static String INVENTORYUI_SHIPLEFTTHRUSTUNIT = "MN";
    public static String INVENTORYUI_SHIPRIGHTTHRUST = "Right-Thrust";
    public static String INVENTORYUI_SHIPRIGHTTHRUSTUNIT = "MN";
    public static String INVENTORYUI_SHIPBACKTHRUST = "Back-Thrust";
    public static String INVENTORYUI_SHIPBACKTHRUSTUNIT = "MN";
    public static String INVENTORYUI_SELECTIONBAR_OPEN = "Open";
    public static String INVENTORYUI_SELECTION_BACK = "Back";
    public static String INVENTORYUI_SELECTION_HEADERTHRUST = "Thrusters";
    
    public static String TURRET_TURRETDOUBLELASER = "Laser turret";
    
    public static String WEAPON_WEAPONLASER1 = "Laser cannon";
    public static String WEAPON_PLASMALANCE = "Plasmalance";
    
    public static String ITEM_ORE = "Ore";
    
    public static String SHIELD_NAME = "Shield";
    public static String BATTERY_NAME = "Energy Cell";
    public static String REACTOR_NAME = "Reactor";
    public static String THRUSTER_NAME_STANDART = "Thruster";
    
    public static String WEAPON_TYPE = "Weapon";
    public static String TURRET_TYPE = "Turret";
    public static String SHIELD_TYPE = "Shield";
    public static String BATTERY_TYPE = "Battery";
    public static String REACTOR_TYPE = "Reactor";
    public static String ORE_TYPE = "Ore";
    public static String WRONG_TYPE_MESSAGE = "Can't be put here";
    public static String NOT_ENOUGH_SPACE_MESSAGE = "Not enough Cargo Space available";
    
    public static String SHIELD_STRENGTH = "Strength:";
    public static String SHIELD_RELOAD = "Reload rate:";
    public static String SHIELD_RELOAD_DELAY = "Reload delay:";
    
    public static String BATTERY_CAPACITY = "Capacity:";
    
    public static String REACTOR_OUTPUT = "Ouput:";
    public static String REACTOR_DELAY = "Output delay:";
    
    public static String THRUSTER_TYPE = "Type:";
    public static String THRUSTER_STANDART = "Standart";
    public static String THRUSTER_ALIEN = "Alien";
    public static String THRUSTER_STRENGTH = "Thrust:";
    
    public static String WEAPON_SHOOTTYPE = "Shoot type:";
    public static String WEAPON_DAMAGE = "Damage:";
    public static String WEAPON_COOLDOWN = "Cooldown:";
    public static String WEAPON_ENERGYCOST = "Energy cost:";
    public static String WEAPON_REPETIER = "Repetier";
    public static String WEAPON_RAY = "Ray";
    
    public static String ORE_VALUE = "Value:";
    public static String RELOAD_TIME_UNIT = "MS";
    
    public static String ITEMDROP = "Item drop";
    public static String TRANSFER_BUTTON_OK = "Close";
    public static String TRANSFER_BUTTON_TAKEALL = "Take all";

    @Override
    public String getString(int field) {
        String string = "";
        
        switch (field) {
            case StringSelector.GAMEUI_HULL:
                string = StringsEN.GAMEUI_HULL;
                break;
            case StringSelector.GAMEUI_ENERGY:
                string = StringsEN.GAMEUI_ENERGY;
                break;
            case StringSelector.INVENTORYUI_INVENTORYHEADER:
                string = StringsEN.INVENTORYUI_INVENTORYHEADER;
                break;
            case StringSelector.INVENTORYUI_WEAPONSLOTHEADER:
                string = StringsEN.INVENTORYUI_WEAPONSLOTHEADER;
                break;
            case StringSelector.INVENTORYUI_SPECIALSLOTHEADER:
                string = StringsEN.INVENTORYUI_SPECIALSLOTHEADER;
                break;
            case StringSelector.INVENTORYUI_SHIPMASS:
                string = StringsEN.INVENTORYUI_SHIPMASS;
                break;
            case StringSelector.INVENTORYUI_SHIPMASSUNIT:
                string = StringsEN.INVENTORYUI_SHIPMASSUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPARMOR:
                string = StringsEN.INVENTORYUI_SHIPARMOR;
                break;
            case StringSelector.INVENTORYUI_SHIPHULL:
                string = StringsEN.INVENTORYUI_SHIPHULL;
                break;
            case StringSelector.INVENTORYUI_SHIPSHIELD:
                string = StringsEN.INVENTORYUI_SHIPSHIELD;
                break;
            case StringSelector.INVENTORYUI_SHIPSHIELDUNIT:
                string = StringsEN.INVENTORYUI_SHIPSHIELDUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPREACTOR:
                string = StringsEN.INVENTORYUI_SHIPREACTOR;
                break;
            case StringSelector.INVENTORYUI_SHIPREACTORUNIT:
                string = StringsEN.INVENTORYUI_SHIPREACTORUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPBATTERY:
                string = StringsEN.INVENTORYUI_SHIPBATTERY;
                break;
            case StringSelector.INVENTORYUI_SHIPBATTERYUNIT:
                string = StringsEN.INVENTORYUI_SHIPBATTERYUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPFRONTTHRUST:
                string = StringsEN.INVENTORYUI_SHIPFRONTTHRUST;
                break;
            case StringSelector.INVENTORYUI_SHIPFRONTTHRUSTUNIT:
                string = StringsEN.INVENTORYUI_SHIPFRONTTHRUSTUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPLEFTTHRUST:
                string = StringsEN.INVENTORYUI_SHIPLEFTTHRUST;
                break;
            case StringSelector.INVENTORYUI_SHIPLEFTTHRUSTUNIT:
                string = StringsEN.INVENTORYUI_SHIPLEFTTHRUSTUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPRIGHTTHRUST:
                string = StringsEN.INVENTORYUI_SHIPRIGHTTHRUST;
                break;
            case StringSelector.INVENTORYUI_SHIPRIGHTTHRUSTUNIT:
                string = StringsEN.INVENTORYUI_SHIPRIGHTTHRUSTUNIT;
                break;
            case StringSelector.INVENTORYUI_SHIPBACKTHRUST:
                string = StringsEN.INVENTORYUI_SHIPBACKTHRUST;
                break;
            case StringSelector.INVENTORYUI_SHIPBACKTHRUSTUNIT:
                string = StringsEN.INVENTORYUI_SHIPBACKTHRUSTUNIT;
                break;
            case StringSelector.INVENTORYUI_SELECTIONBAR_OPEN:
                string = StringsEN.INVENTORYUI_SELECTIONBAR_OPEN;
                break;
            case StringSelector.INVENTORYUI_SELECTION_BACK:
                string = StringsEN.INVENTORYUI_SELECTION_BACK;
                break;
            case StringSelector.INVENTORYUI_SELECTION_HEADERTHRUST:
                string = StringsEN.INVENTORYUI_SELECTION_HEADERTHRUST;
                break;
            case StringSelector.WEAPON_WEAPONLASER1:
                string = StringsEN.WEAPON_WEAPONLASER1;
                break;
            case StringSelector.WEAPON_PLASMALANCE:
                string = StringsEN.WEAPON_PLASMALANCE;
                break;
            case StringSelector.TURRET_TURRETDOUBLELASER:
                string = StringsEN.TURRET_TURRETDOUBLELASER;
                break;
            case StringSelector.ITEM_ORE:
                string = StringsEN.ITEM_ORE;
                break;
            case StringSelector.SHIELD_NAME:
                string = StringsEN.SHIELD_NAME;
                break;
            case StringSelector.BATTERY_NAME:
                string = StringsEN.BATTERY_NAME;
                break;
            case StringSelector.REACTOR_NAME:
                string = StringsEN.REACTOR_NAME;
                break;
            case StringSelector.THRUSTER_NAME_STANDART:
                string = StringsEN.THRUSTER_NAME_STANDART;
                break;
            case StringSelector.WEAPON_TYPE:
                string = StringsEN.WEAPON_TYPE;
                break;
            case StringSelector.TURRET_TYPE:
                string = StringsEN.TURRET_TYPE;
                break;
            case StringSelector.SHIELD_TYPE:
                string = StringsEN.SHIELD_TYPE;
                break;
            case StringSelector.BATTERY_TYPE:
                string = StringsEN.BATTERY_TYPE;
                break;
            case StringSelector.REACTOR_TYPE:
                string = StringsEN.REACTOR_TYPE;
                break;
            case StringSelector.ORE_TYPE:
                string = StringsEN.ORE_TYPE;
                break;
            case StringSelector.WRONG_TYPE_MESSAGE:
                string = StringsEN.WRONG_TYPE_MESSAGE;
                break;
            case StringSelector.NOT_ENOUGH_SPACE_MESSAGE:
                string = StringsEN.NOT_ENOUGH_SPACE_MESSAGE;
                break;
            case StringSelector.SHIELD_STRENGTH:
                string = StringsEN.SHIELD_STRENGTH;
                break;
            case StringSelector.SHIELD_RELOAD:
                string = StringsEN.SHIELD_RELOAD;
                break;
            case StringSelector.SHIELD_RELOAD_DELAY:
                string = StringsEN.SHIELD_RELOAD_DELAY;
                break;
            case StringSelector.BATTERY_CAPACITY:
                string = StringsEN.BATTERY_CAPACITY;
                break;
            case StringSelector.REACTOR_OUTPUT:
                string = StringsEN.REACTOR_OUTPUT;
                break;
            case StringSelector.REACTOR_DELAY:
                string = StringsEN.REACTOR_DELAY;
                break;
            case StringSelector.THRUSTER_TYPE:
                string = StringsEN.THRUSTER_TYPE;
                break;
            case StringSelector.THRUSTER_STANDART:
                string = StringsEN.THRUSTER_STANDART;
                break;
            case StringSelector.THRUSTER_ALIEN:
                string = StringsEN.THRUSTER_ALIEN;
                break;
            case StringSelector.THRUSTER_STRENGTH:
                string = StringsEN.THRUSTER_STRENGTH;
                break;
            case StringSelector.WEAPON_SHOOTTYPE:
                string = StringsEN.WEAPON_SHOOTTYPE;
                break;
            case StringSelector.WEAPON_DAMAGE:
                string = StringsEN.WEAPON_DAMAGE;
                break;
            case StringSelector.WEAPON_COOLDOWN:
                string = StringsEN.WEAPON_COOLDOWN;
                break;
            case StringSelector.WEAPON_ENERGYCOST:
                string = StringsEN.WEAPON_ENERGYCOST;
                break;
            case StringSelector.WEAPON_REPETIER:
                string = StringsEN.WEAPON_REPETIER;
                break;
            case StringSelector.WEAPON_RAY:
                string = StringsEN.WEAPON_RAY;
                break;
            case StringSelector.ORE_VALUE:
                string = StringsEN.ORE_VALUE;
                break;
            case StringSelector.RELOAD_TIME_UNIT:
                string = StringsEN.RELOAD_TIME_UNIT;
                break;
            case StringSelector.ITEMDROP:
                string = StringsEN.ITEMDROP;
                break;
            case StringSelector.TRANSFER_BUTTON_OK:
                string = StringsEN.TRANSFER_BUTTON_OK;
                break;
            case StringSelector.TRANSFER_BUTTON_TAKEALL:
                string = StringsEN.TRANSFER_BUTTON_TAKEALL;
                break;
        }
        
        return string;
    }
}
