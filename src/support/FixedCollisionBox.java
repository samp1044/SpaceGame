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
public class FixedCollisionBox extends CollisionBox {
    double rotation;
    
    public FixedCollisionBox(double middleX,double middleY,int width,int height) {
        super(middleX,middleY,width,height);
        setRotation(0);
    }
    
    public FixedCollisionBox(CollisionBox b) {
        super(b);
        setRotation(0);
    }
    
    public FixedCollisionBox(FixedCollisionBox b) {
        super(b);
        setRotation(b.rotation);
    }
    
    @Override
    public void updateBox(double middleX, double middleY) {
        super.updateBox(middleX, middleY);
        setRotation(this.rotation);
    }
    
    @Override
    public boolean isOverlapping(FixedCollisionBox b) {
        boolean collision = true;
        
        if (!checkCollision(this,b)) {
            return false;
        }
        
        if (!checkCollision(b,this)) {
            return false;
        }
        
        return collision;
    }
    
    private boolean checkCollision(FixedCollisionBox base, FixedCollisionBox foreign) {
        boolean collision = true;
        //ul..upper left, ur..upper right, dl..down left, dr..down right corner
        double ulX = 0;
        double ulY = 0;
        
        double urX = 0;
        double urY = 0;
        
        double dlX = 0;
        double dlY = 0;
        
        double drX = 0;
        double drY = 0;
        
        //1 Dimensional projezierte Eckpunkte:
        //leftCorner_Own, rightCorner_Own
        double lc_O = 0;
        double rc_O = 0;
        
        //leftCorner_Foreign, rightCorner_Foreign
        double lc_F = 0;
        double rc_F = 0;
        
        if (base.rotation != 0) {
            Vector2D ul = new Vector2D(base.middleX,base.middleY,foreign.getUpperLeftX(),foreign.getUpperLeftY()).getRotatedVector(-base.rotation);
            Vector2D ur = new Vector2D(base.middleX,base.middleY,foreign.getUpperRightX(),foreign.getUpperRightY()).getRotatedVector(-base.rotation);
            Vector2D dl = new Vector2D(base.middleX,base.middleY,foreign.getDownLeftX(),foreign.getDownLeftY()).getRotatedVector(-base.rotation);
            Vector2D dr = new Vector2D(base.middleX,base.middleY,foreign.getDownRightX(),foreign.getDownRightY()).getRotatedVector(-base.rotation);
            
            ulX = base.middleX + ul.getX();
            ulY = base.middleY + ul.getY();
            
            urX = base.middleX + ur.getX();
            urY = base.middleY + ur.getY();
            
            dlX = base.middleX + dl.getX();
            dlY = base.middleY + dl.getY();
            
            drX = base.middleX + dr.getX();
            drY = base.middleY + dr.getY();
        } else {
            ulX = foreign.getUpperLeftX();
            ulY = foreign.getUpperLeftY();
            
            urX = foreign.getUpperRightX();
            urY = foreign.getUpperRightY();
            
            dlX = foreign.getDownLeftX();
            dlY = foreign.getDownLeftY();
            
            drX = foreign.getDownRightX();
            drY = foreign.getDownRightY();
        }
        
        //Rechts/Links projektion (y - vergleich)
        
        lc_O = base.middleY - (base.height / 2);
        rc_O = base.middleY + (base.height / 2);
        
        //Oberste Y koordinate für lc_F
        lc_F = ulY;
        
        if (urY < lc_F) {
            lc_F = urY;
        }
        
        if (dlY < lc_F) {
            lc_F = dlY;
        }
        
        if (drY < lc_F) {
            lc_F = drY;
        }
        
        //Unterste Y koordinate für rc_F
        rc_F = ulY;
        
        if (urY > rc_F) {
            rc_F = urY;
        }
        
        if (dlY > rc_F) {
            rc_F = dlY;
        }
        
        if (drY > rc_F) {
            rc_F = drY;
        }
        
        //A: lc_O, B: rc_O, A1: lc_F, B1: rc_F
        if (!((lc_F >= lc_O && lc_F <= rc_O) || (rc_F >= lc_O && rc_F <= rc_O) || (lc_O >= lc_F && lc_O <= rc_F) || (rc_O >= lc_F && rc_O <= rc_F))) {
            return false;
        }
        
        lc_O = base.middleX - (base.width / 2);
        rc_O = base.middleX + (base.width / 2);
        
        //Linke X koordinate für lc_F
        lc_F = ulX;
        
        if (urX < lc_F) {
            lc_F = urX;
        }
        
        if (dlX < lc_F) {
            lc_F = dlX;
        }
        
        if (drX < lc_F) {
            lc_F = drX;
        }
        
        //Rechte X koordinate für rc_F
        rc_F = ulX;
        
        if (urX > rc_F) {
            rc_F = urX;
        }
        
        if (dlX > rc_F) {
            rc_F = dlX;
        }
        
        if (drX > rc_F) {
            rc_F = drX;
        }
        
        if (!((lc_F >= lc_O && lc_F <= rc_O) || (rc_F >= lc_O && rc_F <= rc_O) || (lc_O >= lc_F && lc_O <= rc_F) || (rc_O >= lc_F && rc_O <= rc_F))) {
            return false;
        }
        
        return collision;
    }
    
    public  void setRotation (double angle) {
        this.rotation = angle;
        
        Vector2D ul = new Vector2D(this.middleX,this.middleY,this.middleX - (this.width / 2),this.middleY - (this.height / 2)).getRotatedVector(rotation);
        Vector2D ur = new Vector2D(this.middleX,this.middleY,this.middleX + (this.width / 2),this.middleY - (this.height / 2)).getRotatedVector(rotation);
        Vector2D dl = new Vector2D(this.middleX,this.middleY,this.middleX - (this.width / 2),this.middleY + (this.height / 2)).getRotatedVector(rotation);
        Vector2D dr = new Vector2D(this.middleX,this.middleY,this.middleX + (this.width / 2),this.middleY + (this.height / 2)).getRotatedVector(rotation);
        
        this.x = this.middleX + ul.getX();
        this.y = this.middleY + ul.getY();
        
        this.x2 = this.middleX + ur.getX();
        this.y2 = this.middleY + ur.getY();
        
        this.x3 = this.middleX + dl.getX();
        this.y3 = this.middleY + dl.getY();
        
        this.x4 = this.middleX + dr.getX();
        this.y4 = this.middleY + dr.getY();
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawLine((int)(Background.X + this.x), (int)(Background.Y + this.y), (int)(Background.X + this.x2), (int)(Background.Y + this.y2));
        g2d.drawLine((int)(Background.X + this.x2), (int)(Background.Y + this.y2), (int)(Background.X + this.x4), (int)(Background.Y + this.y4));
        g2d.drawLine((int)(Background.X + this.x4), (int)(Background.Y + this.y4), (int)(Background.X + this.x3), (int)(Background.Y + this.y3));
        g2d.drawLine((int)(Background.X + this.x3), (int)(Background.Y + this.y3), (int)(Background.X + this.x), (int)(Background.Y + this.y));
    }
    
    public String toString() {
        String s = "FixedCollisionBox:\n";
        s += "rotation angle: "+this.rotation+" \n\n";
        s += super.toString();
        return s;
    }
}
