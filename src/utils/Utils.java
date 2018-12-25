/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;


/**
 * Klasse für statische Hilfsmethoden
 * @author Sami
 */
public class Utils {
    private static Logger logger = new Logger(Utils.class);
    
    public static String trimStringToGivenLength(String string,int length) {
        if (length > 0) {
            if (length < string.length()) {
                string = string.substring(0, length);
            } else if (length > string.length()) {
                int offSet = length - string.length();
                
                for (int i = 0;i < offSet;i++) {
                    string += " ";
                }
            }
        }
        
        return string;
    }
    
    /**
     * Depreciated! -> Huge Performance Leak!! Use Resources.getScaledBufferedImage() instead
     * @param image
     * @param width
     * @param height
     * @return 
     */
    public static BufferedImage getScaledBufferedImageInstance(BufferedImage image, int width, int height) {
        Image scaledImage = null;
        BufferedImage scaledBufferedImage = null;
        
        try {
            scaledImage = image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            scaledBufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            
            scaledBufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
            scaledBufferedImage.getGraphics().dispose();
        } catch(Exception e) {
            logger.error("Fehler in getScaledBufferedImageInstance("+image+","+width+","+height+"): "+e);
        }
        
        return scaledBufferedImage;
    }
}
