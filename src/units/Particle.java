/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import support.Background;

/**
 *
 * @author Sami
 */
public class Particle {
    private Image[] img;
    private String[] files;
    
    private int width;
    private int height;
    
    private int actual;
            
    private int posX;
    private int posY;
    
    private long duration;
    private long lifeTimeStart;
    
    /**
     * 
     * @param files
     * Ein String feld in dem die Dateinamen der Bilder gespeichert sind die als Partikel angezeigt werden sollen (mit Dateiendung!). Das Laden sieht wie folgt aus: root/images/particles/<dateiname.endung>
     * @param width
     * Die Weite des Bildes das als Partikel angezeigt werden soll in Pixel
     * @param height
     * Die Höhe des Bildes das als Partikel angezeigt werden soll in Pixel
     * @param x
     * Die X-Koordinate an der das Bild dargestellt werden soll
     * @param y
     * Die Y-Koordinate an der das Bild dargestellt werden soll
     * @param duration 
     * Die Zeit bis das Partikel wieder verschwinden soll
     */
    public Particle(String[] files,int width,int height,int x,int y,long duration) {
        img = new Image[files.length];
        this.actual = 0;
        
        this.files = files;
        
        this.width = width;
        this.height = height;
        
        this.posX = x;
        this.posY = y;
        
        this.duration = duration;
        this.lifeTimeStart = System.currentTimeMillis();
        
        for (int i = 0;i < files.length;i++) {
            img[i] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/particles/"+files[i]));
            this.img[i] = img[i].getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
        }
    }
    
    public Particle(String[] files,int width,int height,long duration) {
        this(files,width,height,0,0,duration);
    }
    
    public Particle(String file,int width,int height,long duration) {
        this(new String[] {file},width,height,0,0,duration);
    }
    
    public Particle(String file,int width,int height,int x,int y,long duration) {
        this(new String[] {file},width,height,x,y,duration);
    }
    
    public Particle(Particle particle) {
        this(particle.files,particle.width,particle.height,particle.posX,particle.posY,particle.duration);
    }
    
    public boolean draw(Graphics2D g2d) {
        if((this.actual + 1) < this.img.length) {
            this.actual += 1;
        } else {
            this.actual = 0;
        }
        
        g2d.drawImage(this.img[this.actual], (int)Background.X + this.posX, (int)Background.Y + this.posY, null);
        
        if((System.currentTimeMillis() - this.lifeTimeStart) <= this.duration) {
            return false;
        } else {
            return true;
        }
    }
    
    public void renewDuration() {
        this.lifeTimeStart = System.currentTimeMillis();
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public void setPosY(int posY) {
        this.posY = posY;
    }
}
