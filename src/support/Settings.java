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
    public static boolean RUN = true;
    public static boolean DEBUG = true;
    public static boolean INFO = true;
    public static boolean ERROR = true;
    
    public static int KeyFORWARD = KeyEvent.VK_W;
    public static int KeyBACKWARD = KeyEvent.VK_S;
    public static int KeyLEFT = KeyEvent.VK_A;
    public static int KeyRIGHT = KeyEvent.VK_D;
    
    public static int KeyDEBUG_INFO = KeyEvent.VK_F3;
    
    public static int KeySHOOT = MouseEvent.BUTTON1;
    
    public static boolean FORWARD_Key_down = false;
    public static boolean BACKWARD_Key_down = false;
    public static boolean LEFT_Key_down = false;
    public static boolean RIGHT_Key_down = false;
    public static boolean SHOOT_Key_down = false;
    
    public static boolean showDebug = false;
    public static boolean particlesEnabled = true;
    public static boolean backgroundsEnabled = false;
}
