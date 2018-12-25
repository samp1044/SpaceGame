/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Sami
 */
public class Settings {
    //Logger output einstellungen
    public static boolean RUN = true;
    public static boolean DEBUG = true;
    public static boolean INFO = true;
    public static boolean ERROR = true;
    
    //Tastenzuweisungen
    public static int KeyFORWARD = KeyEvent.VK_W;
    public static int KeyBACKWARD = KeyEvent.VK_S;
    public static int KeyLEFT = KeyEvent.VK_A;
    public static int KeyRIGHT = KeyEvent.VK_D;
    
    public static int KeySHOOT = MouseEvent.BUTTON1;
    public static int KeyIGNORE_MOUSE_MOVEMENT = KeyEvent.VK_SHIFT;
    
    public static int KeyINVENTORY = KeyEvent.VK_I;
    
    //Slot steuerung
    public static int KeySLOT1 = KeyEvent.VK_1;
    public static int KeySLOT2 = KeyEvent.VK_2;
    public static int KeySLOT3 = KeyEvent.VK_3;
    public static int KeySLOT4 = KeyEvent.VK_4;
    public static int KeySLOT5 = KeyEvent.VK_5;
    public static int KeySLOT6 = KeyEvent.VK_6;
    public static int KeySLOT7 = KeyEvent.VK_7;
    public static int KeySLOT8 = KeyEvent.VK_8;
    public static int KeySLOT9 = KeyEvent.VK_9;
    public static int KeySLOT0 = KeyEvent.VK_0;
    
    //UI Steuerungen
    
    //Nicht änderbare Tastenzuweisungen
    public static final int KeyDEBUG_INFO = KeyEvent.VK_F3;
    public static final int KeySCREENSHOT = KeyEvent.VK_F1;
    
    //Status Variablen
    public static boolean FORWARD_Key_down = false;
    public static boolean BACKWARD_Key_down = false;
    public static boolean LEFT_Key_down = false;
    public static boolean RIGHT_Key_down = false;
    public static boolean SHOOT_Key_down = false;
    
    public static int SLOT_VALUE = 0;
    public static boolean INCREASE_SLOT = false;
    public static boolean DECREASE_SLOT = false;
    
    public static boolean INVENTORY_KEY_DOWN = false;
    
    public static boolean MOUSE_LEFT_DOWN = false;
    public static boolean MOUSE_RIGHT_DOWN = false;
    public static boolean ESCAPE_DOWN = false;
    public static boolean SHIFT_DOWN = false;
    
    public static boolean IGNORE_MOUSE_MOVEMENT = false;
    
    public static boolean showDebug = false;
    public static boolean particlesEnabled = false;
    public static boolean backgroundsEnabled = false;
    public static int language = 0;
    public static int solutionWidth = 1600;
    public static int solutionHeight = 834;
    
    public static boolean takeScreenshot = false;
}
