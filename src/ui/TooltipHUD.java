/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Interfaces.GameUnit;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.Resources;

/**
 *
 * @author Sami
 */
public class TooltipHUD {
    private BufferedImage background;
    public String text;
    
    private Object updateSource;
    
    private int x;
    private int y;
    
    private int offsetX;
    private int offsetY;
    
    private int width;
    private int height;
    
    private long start;
    private long duration;
    
    private final Font font = new Font("Helvetica",Font.BOLD,12);
    private final int rowSpacing = 15;
    private final int margin = 5;
    
    public boolean active;
    
    public TooltipHUD(int offsetX, int offsetY, String msg,long durationTime,Object updateSource) {
        int rows = 0;
        int width = 0;
        int height = 0;
        
        this.updateSource = updateSource;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        if (updateSource == null) {
            x = MainLoop.mausX + offsetX;
            y = MainLoop.mausY + offsetY;
        } else {
            x = (int)(Background.X + ((GameUnit)this.updateSource).getMiddleX() + offsetX);
            y = (int)(Background.Y + ((GameUnit)this.updateSource).getMiddleY() + offsetY);
        }
        
        text = msg;
        duration = durationTime;
        start = System.currentTimeMillis();
        
        for (int i = 0;i < text.length();i++) {
            if (text.charAt(i) == '\n') {
                rows++;
            }
        }
        
        if (text.length() > 0 && text.charAt(text.length() - 1) != '\n') {
            text += "\n";
            rows++;
        }
        
        width = stringLength(text) * 6 + 2 * margin;
        height = rows * rowSpacing + 3 * margin;
        
        background = MainLoop.resources.getScaledBufferedImageField(Resources.UI_TOOLTIP_BACKGROUND, width, height,Resources.SCALE_FAST)[0];
        
        this.width = width;
        this.height = height;
        
        active = true;
    } 
    
    public boolean update() {
        if (updateSource == null) {
            x = MainLoop.mausX + offsetX;
            y = MainLoop.mausY + offsetY;
        } else {
            x = (int)(Background.X + ((GameUnit)this.updateSource).getMiddleX() + offsetX);
            y = (int)(Background.Y + ((GameUnit)this.updateSource).getMiddleY() + offsetY);
        }
        
        if (duration >= 0 && System.currentTimeMillis() - start >= duration) {
            return false;
        }
        
        return true;
    }
    
    public void renewLifeTime() {
        this.start = System.currentTimeMillis();
    }
    
    public void draw(Graphics2D g2d) {
        int index = 0;
        Font defaultFont = g2d.getFont();
        String textCopy = text;
        
        if (background != null) {
            g2d.drawImage(background, x,y, null);
        }
        g2d.setFont(font);
        
        do {
            g2d.drawString(textCopy.substring(0,textCopy.indexOf("\n")), x + margin, y + margin + (rowSpacing * (index + 1)));
            
            textCopy = textCopy.substring(textCopy.indexOf('\n') + 1);
            index++;
        } while(textCopy.contains("\n"));
        
        g2d.setFont(defaultFont);
    }
    
    public boolean textEquals(String text) {
        boolean equals = false;
        String textCopy = text;
        
        if (textCopy == null) {
            return equals;
        }
        
        if (textCopy.length() > 0 && textCopy.charAt(textCopy.length() - 1) != '\n') {
            textCopy += '\n';
        }
        
        if (textCopy.equals(this.text)) {
            equals = true;
        }
        
        return equals;
    }
    
    private int stringLength(String text) {
        int maxLength = 0;
        String textCopy = text;
        
        do {
            if (textCopy.indexOf('\n') > maxLength) {
                maxLength = textCopy.indexOf('\n');
            }
            textCopy = textCopy.substring(textCopy.indexOf('\n') + 1);
        } while(textCopy.contains("\n"));
        
        return maxLength;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
}
