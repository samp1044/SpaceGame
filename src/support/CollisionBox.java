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
    public double x;
    public double y;
    
    public double x2;
    public double y2;
    
    public double x3;
    public double y3;
    
    public double x4;
    public double y4;
    
    public int width;
    public int height;
    
    protected double middleX;
    protected double middleY;
    
    public CollisionBox(double middleX,double middleY,int width,int height) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.width = width;
        this.height = height;
        
        this.x = this.middleX - (this.width / 2);
        this.y = this.middleY - (this.height / 2);
        
        this.x2 = this.middleX + (this.width / 2);
        this.y2 = this.middleY - (this.height / 2);
        
        this.x3 = this.middleX - (this.width / 2);
        this.y3 = this.middleY + (this.height / 2);
        
        this.x4 = this.middleX + (this.width / 2);
        this.y4 = this.middleY + (this.height / 2);
    }
    
    public CollisionBox(CollisionBox b) {
        this.x = b.x;
        this.y = b.y;
        
        this.x2 = b.x2;
        this.y2 = b.y2;
        
        this.x3 = b.x3;
        this.y3 = b.y3;
        
        this.x4 = b.x4;
        this.y4 = b.y4;
        
        this.width = b.width;
        this.height = b.height;
        
        this.middleX = b.middleX;
        this.middleY = b.middleY;
    }
    
    public void updateBox(double middleX, double middleY) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.x = this.middleX - (this.width / 2);
        this.y = this.middleY - (this.height / 2);
        
        this.x2 = this.middleX + (this.width / 2);
        this.y2 = this.middleY - (this.height / 2);
        
        this.x3 = this.middleX - (this.width / 2);
        this.y3 = this.middleY + (this.height / 2);
        
        this.x4 = this.middleX + (this.width / 2);
        this.y4 = this.middleY + (this.height / 2);
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
    
    public double getMiddleX() {
        return this.middleX;
    }
    
    public double getMiddleY() {
        return this.middleY;
    }
    
    public double getUpperLeftX() {
        return this.x;
    }
    
    public double getUpperLeftY() {
        return this.y;
    }
    
    public double getUpperRightX() {
        return this.x2;
    }
    
    public double getUpperRightY() {
        return this.y2;
    }
    
    public double getDownLeftX() {
        return this.x3;
    }
    
    public double getDownLeftY() {
        return this.y3;
    }
    
    public double getDownRightX() {
        return this.x4;
    }
    
    public double getDownRightY() {
        return this.y4;
    }
    
    @Override
    public String toString() {
        String s = "CollisionBox:\n\n";
        
        s += "x: "+this.x+" y: "+this.y+" x2: "+this.x2+" y2: "+this.y2+" x3: "+this.x3+" y3: "+this.y3+" x4: "+this.x4+" y4: "+this.y4+"\n\n";
        
        for (int i = (int)y; i < (int)(y + height);i++) { //Y
            for (int j = (int)x;j < (int)(x + width);j++) { //X
                s += ("" + j + ":" + i + " ");
            }
            
            s+= "\n";
        }
        
        return s;
    }
}
