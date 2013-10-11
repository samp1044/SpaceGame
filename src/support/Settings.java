/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.event.KeyEvent;

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
    
    public static boolean FORWARD_Key_down = false;
    public static boolean BACKWARD_Key_down = false;
    public static boolean LEFT_Key_down = false;
    public static boolean RIGHT_Key_down = false;
    
    public static boolean particlesEnabled = true;
}
