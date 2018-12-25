/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.Background;
import support.FPSManager;
import support.FixedCollisionBox;
import support.Resources;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Bullet {
    public static final int LASER_RED = 3;
    public static final int LASER_BLUE = 4;
    public static final int PLASMA = 5;
    
    int type;
    int shootType;
    
    BufferedImage[] img;
    BufferedImage[] imgLighting;
    private int actual;
    private int lightingActual;
    
    private long lastAnimationImage_Time;
    private long animationDelay;
    
    private int width;
    private int height;
    
    private double x;
    private double y;
    
    private int flag;
    FixedCollisionBox boxCollider;
    
    private Vector2D direction;
    private double rotation;
    
    private long startTime;
    private long lifeTime;
    
    private double damage;
    private double speed;
    
    private Particle particle;
    
    public Bullet(int type, int shootType, int width,int height, double x,double y,Vector2D direction,double damage,int flag) {
        this.type = type;
        this.shootType = shootType;
        
        this.x = x;
        this.y = y;
        
        this.width = width;
        this.height = height;
        
        this.direction = direction;
        this.damage = damage;
        
        this.actual = 0;
        this.lightingActual = 0;
        
        this.lastAnimationImage_Time = 0;
        
        if (shootType == WeaponBase.RAY) {
            initRay();
        }
        
        switch (type) {
            case LASER_RED:
                this.img = MainLoop.resources.getScaledBufferedImageField(Resources.BULLET_LASER_RED, width, height,Resources.SCALE_SMOOTH);
                this.imgLighting = MainLoop.resources.getScaledBufferedImageField(Resources.LIGHTING_RED, width + 20, height + 20,Resources.SCALE_SMOOTH);
                this.particle = null;
                this.animationDelay = 50;
                break;
            case LASER_BLUE:
                this.img = MainLoop.resources.getScaledBufferedImageField(Resources.BULLET_LASER_RAY_BLUE, width, height,Resources.SCALE_SMOOTH);
                this.imgLighting = MainLoop.resources.getScaledBufferedImageField(Resources.LIGHTING_BLUE, width + 20, height + 20,Resources.SCALE_SMOOTH);
                this.particle = null;
                this.animationDelay = 0;
                break;
            case PLASMA:
                this.img = MainLoop.resources.getScaledBufferedImageField(Resources.BULLET_PLASMA, width, height,Resources.SCALE_SMOOTH);
                this.imgLighting = MainLoop.resources.getScaledBufferedImageField(Resources.LIGHTING_GREEN, width + 20, height + 20,Resources.SCALE_SMOOTH);
                this.particle = null;
                this.animationDelay = 50;
                break;
        }
        
        switch (shootType) {
            case WeaponBase.REPETIER:
                this.speed = 1500;
                this.lifeTime = 4000;
                break;
            case WeaponBase.RAY:
                this.speed = 0;
                this.lifeTime = 50;
                break;
            default:
                this.speed = 1500;
                this.lifeTime = 4000;
                break;
                
        }
        
        calculateRotation(direction);
        
        this.direction = this.direction.getUnitVector().multiplyWithNumber(this.speed);
        
        this.flag = flag;
        boxCollider = new FixedCollisionBox(this.x + (width/2),this.y + (height / 2),width,height);
        
        this.startTime = System.currentTimeMillis();
    }
    
    public Bullet() {
        
    }
    
    /**
     * Kopier Konstruktor
     */
    public Bullet(Bullet bullet) {
        this.img = bullet.img;
        this.imgLighting = bullet.imgLighting;
        this.width = bullet.width;
        this.height = bullet.height;
        
        this.actual = 0;
        this.lightingActual = bullet.lightingActual;
        
        this.lastAnimationImage_Time = 0;
        this.animationDelay = bullet.animationDelay;
        
        this.type = bullet.type;
        this.direction = bullet.direction;
        this.x = bullet.x;
        this.y = bullet.y;
        
        this.rotation = bullet.rotation;
        this.damage = bullet.damage;
        this.speed = bullet.speed;
        
        this.lifeTime = bullet.lifeTime;
        this.flag = bullet.flag;
        this.boxCollider = new FixedCollisionBox(bullet.boxCollider);
    }
    
    public void draw(Graphics2D g2d) {
        g2d.rotate(Math.toRadians(this.rotation), (int)Background.X+ (int)this.x+this.width/2, (int)Background.Y + (int)this.y+this.height/2);
        
        if ((this.actual + 1) < img.length && (System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual += 1;
            this.lastAnimationImage_Time = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - this.lastAnimationImage_Time) >= this.animationDelay) {
            this.actual = 0;
            this.lastAnimationImage_Time = System.currentTimeMillis();
            
            /*if (this.type == LASER_RAY_BLUE) {  //Destroy if its a ray type and continued one animation cycle
                destroy = true;
            }*/
        }
        
        if ((this.lightingActual + 1) < this.imgLighting.length) {
            this.lightingActual += 1;
        } else {
            this.lightingActual = 0;
        }
        
        g2d.drawImage(this.imgLighting[this.lightingActual], (int)(Background.X + this.x - 10), (int)(Background.Y + this.y - 10),null);
        g2d.drawImage(this.img[this.actual], (int)(Background.X + this.x), (int)(Background.Y + this.y),null);
        
        g2d.rotate(-Math.toRadians(this.rotation), (int)Background.X+ (int)this.x+this.width/2, (int)Background.Y + (int)this.y+this.height/2);
    }
    
    public boolean update() {
        boolean destroy = false;
        
        this.x = this.x + this.direction.getX() * FPSManager.DELTA;
        this.y = this.y + this.direction.getY() * FPSManager.DELTA;
        
        this.boxCollider.updateBox(this.x + (this.width/2),this.y + (this.height / 2));
        
        destroy = hasHit();
        
        if (this.shootType == WeaponBase.RAY) {
            destroy = false;
        }
        
        if((System.currentTimeMillis() - this.startTime) >= this.lifeTime) {
            destroy = true;
        }
        
        return destroy;
    }
    
    private boolean hasHit() {
        boolean hit = false;
        
        if (this.flag != Ship.ALLIED) {
            hit = MainLoop.playerShip.isHit(this,this.boxCollider,this.flag,this.damage);
            
            if (hit) {
                return hit;
            }
        }
        
        for (int i = 0;i < MainLoop.aiShips.size();i++) {
            AiShip aiShip = (AiShip)MainLoop.aiShips.getElementAt(i);
            
            hit = aiShip.isHit(this,this.boxCollider,this.flag,this.damage);
            
            if (hit) {
                return hit;
            }
        }
        
        return hit;
    }
    
    private boolean checkForCollision() {
        boolean collision = false;
        
        if (this.flag != Ship.ALLIED) {
            if (MainLoop.playerShip != null) {
                collision = this.boxCollider.isOverlapping(MainLoop.playerShip.getCollisionBox());
            }
            
            if (collision) {
                return collision;
            }
        }
        
        for (int i = 0;i < MainLoop.aiShips.size();i++) {
            AiShip aiShip = (AiShip)MainLoop.aiShips.getElementAt(i);
            
            collision = this.boxCollider.isOverlapping(aiShip.getCollisionBox());
            
            if (collision) {
                return collision;
            }
        }
        
        return collision;
    }
    
    private void initRay() {
        double actMiddleX = this.getMiddleX();
        double actMiddleY = this.getMiddleY();
        Vector2D directionVector = direction.getTurnedLeftVektor().getTurnedLeftVektor().getUnitVector().multiplyWithNumber(this.height / 2);

        actMiddleX = actMiddleX + directionVector.getX();
        actMiddleY = actMiddleY + directionVector.getY();

        directionVector = direction.getUnitVector();

        for (int i = 0;i < this.height;i++) {
            this.boxCollider = new FixedCollisionBox(actMiddleX,actMiddleY,this.width,1);

            if (checkForCollision()) {
                this.height = i;
                break;
            }

            actMiddleX += directionVector.getX();
            actMiddleY += directionVector.getY();
        }
    }
    
    public boolean equals(Object object) {
        boolean isEqual = false;
        Bullet bullet = null;
        
        if (object == null || !(object instanceof Bullet)) {
            return isEqual;
        }
        
        bullet = (Bullet)object;
        
        if (this.type == bullet.type && this.damage == bullet.damage) {
            isEqual = true;
        }
        
        return isEqual;
    }
    
    public void hitExplosion() {
        Explosion explosion = null;
        int explosionType = 0;
        
        switch (this.type) {
            case LASER_RED:
            case LASER_BLUE:
                explosionType = Explosion.STANDART_SMOOTH;
                break;
            case PLASMA:
                explosionType = Explosion.STANDART_SMOOTH;
                break;
            default:
                explosionType = Explosion.STANDART_SMOOTH;
                break;
        }
        
        explosion = new Explosion(explosionType,this.width,this.width,this.getMiddleX(),this.getMiddleY(),null);
        MainLoop.explosions.add(explosion);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setX(double x) {
        this.x = x;
        this.boxCollider.updateBox(this.x + (this.width/2),this.y + (this.height / 2));
    }
    
    public void setY(double y) {
        this.y = y;
        this.boxCollider.updateBox(this.x + (this.width/2),this.y + (this.height / 2));
    }
    
    public void setDirection(Vector2D direction) {
        this.direction = direction;
        this.direction = this.direction.getUnitVector().multiplyWithNumber(this.speed);
        
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
        
        this.rotation = (double)fixedVector.getAngle(direction);
        
        if(isLeft) {
            this.rotation = 360 - this.rotation;
        }
    }
    
    public boolean isHit(FixedCollisionBox b, int flag) {
        boolean hit = false;
        
        if (this.flag == flag) {
            if (this.boxCollider.isInRange(b)) {
              hit = this.boxCollider.isOverlapping(b);
            }
        }
        
        return hit;
    }
    
    public void renewLifeTime() {
        this.startTime = System.currentTimeMillis();
    }
    
    public void setFraction(int fraction) {
        this.flag = fraction;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public FixedCollisionBox getBox() {
        return this.boxCollider;
    }
    
    public double getMiddleX() {
        return this.x + this.width / 2;
    }
    
    public double getMiddleY() {
        return this.y + this.height / 2;
    }
    
    public int getShootType() {
        return this.shootType;
    }
}
