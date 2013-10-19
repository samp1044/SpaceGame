/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.Graphics2D;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class CollisionBox {
    public float x;
    public float y;
    
    public int width;
    public int height;
    
    protected float middleX;
    protected float middleY;
    
    public CollisionBox(float middleX,float middleY,int width,int height) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.width = width;
        this.height = height;
        
        this.x = this.middleX - (this.width / 2);
        this.y = this.middleY - (this.height / 2);
    }
    
    public CollisionBox(double middleX,double middleY,int width,int height) {
        this((float)middleX,(float)middleY,width,height);
    }
    
    public CollisionBox(CollisionBox b) {
        this.x = b.x;
        this.y = b.y;
        
        this.width = b.width;
        this.height = b.height;
        
        this.middleX = b.middleX;
        this.middleY = b.middleY;
    }
    
    public void updateBox(float middleX, float middleY) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.x = this.middleX - (this.width / 2);
        this.y = this.middleY - (this.height / 2);
    }
    
    public boolean isOverlapping(CollisionBox b) {
        boolean overlaps = false;
        
        OuterLoop:
        for (int i = (int)b.y; i < (int)(b.y + b.height);i++) { //Y
            for (int j = (int)b.x;j < (int)(b.x + b.width);j++) { //X
                if (isInside(j,i)) {
                    overlaps = true;
                    break OuterLoop;
                }
            }
        }
        
        return overlaps;
    }
    
    public boolean isOverlapping(FixedCollisionBox b) {
        return false;
    }
    
    public boolean isInside(int tX,int tY) {
        boolean inside = false;
        
        if (this.x < tX && tX < this.x + this.width) {
            if (this.y < tY && tY < this.y + this.height) {
                inside = true;
            }
        }
        
        return inside;
    }
    
    public boolean isInRange(CollisionBox b) {
        boolean inRange = false;
        
        int length1 = this.width;
        int length2 = b.width;
        
        if (length1 < this.height) {
            length1 = this.height;
        }
        
        if (length2 < b.height) {
            length2 = b.height;
        }
        
        double distance = new Vector2D(b.middleX,b.middleY,this.middleX,this.middleY).getLength();
        
        if (distance < (length1 + length2)) {
            inRange = true;
        }
        
        return inRange;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawLine((int)Background.X + (int)this.x,(int)Background.Y + (int)this.y,(int)Background.X + (int)this.x + this.width,(int)Background.Y + (int)this.y);
        g2d.drawLine((int)Background.X + (int)this.x + this.width,(int)Background.Y + (int)this.y,(int)Background.X + (int)this.x + this.width,(int)Background.Y +  (int)this.y + this.height);
        g2d.drawLine((int)Background.X + (int)this.x + this.width,(int)Background.Y +  (int)this.y + this.height,(int)Background.X + (int)this.x,(int)Background.Y + (int)this.y + this.height);
        g2d.drawLine((int)Background.X + (int)this.x,(int)Background.Y + (int)this.y + this.height,(int)Background.X + (int)this.x,(int)Background.Y + (int)this.y);
    }
    
    @Override
    public String toString() {
        String s = "CollisionBox:\n\n";
        
        for (int i = (int)y; i < (int)(y + height);i++) { //Y
            for (int j = (int)x;j < (int)(x + width);j++) { //X
                s += ("" + i + ":" + j + " ");
            }
            
            s+= "\n";
        }
        
        return s;
    }
}
