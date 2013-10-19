/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.Image;
import main.MainLoop;
import support.Background;
import support.FixedCollisionBox;
import support.Resources;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Bullet {
    public static final int NEUTRAL = 1;
    public static final int ENEMY = 2;
    
    public static final int LASER_RED = 3;
    
    Image[] img;
    private int actual;
    
    private int width;
    private int height;
    
    private float x;
    private float y;
    
    private int flag;
    FixedCollisionBox boxCollider;
    
    private Vector2D directon;
    private float rotation;
    
    private float damage;
    private float speed;
    
    public Bullet(int type,int width,int height, float x,float y,Vector2D direction,float damage,int flag) {
        this.x = x;
        this.y = y;
        
        this.width = width;
        this.height = height;
        
        this.directon = direction;
        this.damage = damage;
        
        this.actual = 0;
        
        switch (type) {
            case LASER_RED:
                this.img = MainLoop.resources.getScaledImageField(Resources.BULLET_LASER_RED, width, height);
                this.speed = 20;
                break;
        }
        
        calculateRotation(direction);
        
        this.directon = this.directon.getUnitVector().multiplyWithNumber(this.speed);
        
        this.flag = flag;
        boxCollider = new FixedCollisionBox(this.x + (width/2),this.y + (height / 2),width,height);
    }
    
    /**
     * Kopier Konstruktor
     */
    public Bullet(Bullet bullet) {
        this.img = bullet.img;
        this.width = bullet.width;
        this.height = bullet.height;
        this.actual = 0;
        
        this.directon = bullet.directon;
        this.x = bullet.x;
        this.y = bullet.y;
        
        this.rotation = bullet.rotation;
        this.damage = bullet.damage;
        this.speed = bullet.speed;
        
        this.flag = bullet.flag;
        this.boxCollider = new FixedCollisionBox(bullet.boxCollider);
    }
    
    public void draw(Graphics2D g2d) {
        update();
        
        g2d.rotate(Math.toRadians(this.rotation), (int)Background.X+ (int)this.x+this.width/2, (int)Background.Y + (int)this.y+this.height/2);
        
        if ((this.actual + 1) < img.length) {
            this.actual += 1;
        } else {
            this.actual = 0;
        }
        
        g2d.drawImage(this.img[this.actual], (int)(Background.X + this.x), (int)(Background.Y + this.y),null);
        
        g2d.rotate(-Math.toRadians(this.rotation), (int)Background.X+ (int)this.x+this.width/2, (int)Background.Y + (int)this.y+this.height/2);
    }
    
    private void update() {
        this.x = this.x + this.directon.getX();
        this.y = this.y + this.directon.getY();
        
        this.boxCollider.updateBox(this.x + (this.width/2),this.y + (this.height / 2));
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setX(float x) {
        this.x = x;
        this.boxCollider.updateBox(this.x + (this.width/2),this.y + (this.height / 2));
    }
    
    public void setY(float y) {
        this.y = y;
        this.boxCollider.updateBox(this.x + (this.width/2),this.y + (this.height / 2));
    }
    
    public void setDirection(Vector2D direction) {
        this.directon = direction;
        this.directon = this.directon.getUnitVector().multiplyWithNumber(this.speed);
        
        calculateRotation(direction);
        this.boxCollider.setRotation(this.rotation);
    }
    
    private void calculateRotation(Vector2D direction) {
        boolean isLeft = false;
        Vector2D fixedVector = new Vector2D(0,-1);
        
        int middleX = (int)(x + (width/2));
        
        if (middleX + direction.getX() < middleX) {
            isLeft = true;
        }
        
        this.rotation = (float)fixedVector.getAngle(direction);
        
        if(isLeft) {
            this.rotation = 360 - this.rotation;
        }
    }
    
    public boolean isHit(FixedCollisionBox b, float shipPosX, float shipPosY,int width,int height, int flag) {
        boolean hit = false;
        
        if (this.flag == flag) {
            if (this.boxCollider.isInRange(b)) {
              hit =  this.boxCollider.isOverlapping(b);
            }
        }
        
        return hit;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public FixedCollisionBox getBox() {
        return this.boxCollider;
    }
}
