/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import utils.Logger;
/**
 *
 * @author Sami
 */
public class Main {
    
    static Logger logger = new Logger(Main.class);
    
    public static void main(String[] args) {
        logger.info("Programm started. Calling Main Loop");
        MainLoop main = new MainLoop();
    }
}
