/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;


/**
 * Unfertig da momentan ungebraucht
 */
public class DynamicCollisionBox extends CollisionBox {
    private double rotation;
    
    public DynamicCollisionBox(float middleX,float middleY,int width,int height) {
        super(middleX,middleY,width,height);
        this.rotation = 0;
    }
    
    public DynamicCollisionBox(double middleX,double middleY,int width,int height) {
        super((float)middleX,(float)middleY,width,height);
        this.rotation = rotation;
    }
}
