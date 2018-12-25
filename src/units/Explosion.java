/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.Resources;

/**
 *
 * @author Sami
 */
public class Explosion {
    public static final int DUMMY = 1;
    public static final int STANDART_SMOOTH = 3;
    
    private int type;
    
    private BufferedImage[] images;
    private int actual;
    
    private long lastAnimationImage_Time;
    private long animationDelay;
    
    private double middleX;
    private double middleY;
    
    private double posX;
    private double posY;
    
    private int width;
    private int height;
    
    private ItemDrop itemDrop;
    
    public Explosion (int type) {
        this(type,null);
    }
    
    public Explosion (int type, ItemDrop itemDrop) {
        this.type = type;
        this.lastAnimationImage_Time = 0;
        this.itemDrop = itemDrop;
    }
    
    public Explosion(int type, int width, int height,double middleX, double middleY, ItemDrop itemDrop) {
        this(type,itemDrop);
        
        this.width = width;
        this.height = height;
        
        if (this.width > this.height) {
            this.height = this.width;
        } else if (this.height > this.width) {
            this.width = this.height;
        }
        
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.posX = this.middleX - (this.width / 2);
        this.posY = this.middleY - (this.height / 2);
        
        this.lastAnimationImage_Time = 0;
        
        switch (this.type) {
            case DUMMY:
                images = MainLoop.resources.getScaledBufferedImageField(Resources.EXPLOSION_DUMMY, width, height,Resources.SCALE_FAST);
                this.animationDelay = 100;
                break;
            case STANDART_SMOOTH:
                images = MainLoop.resources.getScaledBufferedImageField(Resources.EXPLOSION_STANDART_SMOOTH, width, height,Resources.SCALE_FAST);
                this.animationDelay = 100;
                break;
        }
    }
    
    public Explosion(int type, int width, int height,double middleX, double middleY) {
        this(type,width, height, middleX, middleY, null);
    }
    
    public Explosion getRenewedExplosion(int width, int height,double middleX, double middleY, ItemDrop itemDrop) {
        return new Explosion(this.type, width, height, middleX, middleY,itemDrop);
    }
    
    public boolean draw(Graphics2D g2d) {
        boolean destroy = false;
        
        if ((this.actual + 1) < this.images.length && (System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual += 1;
            this.lastAnimationImage_Time = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual = 0;
            
            if (this.itemDrop != null) {
                this.itemDrop.renewLifetime();
                MainLoop.drops.add(this.itemDrop);
            }
            
            destroy = true;
        }
        
        g2d.drawImage(this.images[this.actual], (int)(Background.X + this.posX), (int)(Background.Y + this.posY), null);
        
        return destroy;
    }
}
