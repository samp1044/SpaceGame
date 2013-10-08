/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import utils.Vector2D;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Sami
 */
public class Ship {
    private Image img;
    private float rotationDegree;
    private int width;
    private int height;
    
    private float middleX;
    private float middleY;
    private float posX;
    private float posY;
    
    private Vector2D direction;
    private Vector2D force;
    
    private Thruster[] thrusterForward;
    private Thruster[] thrusterBackward;
    private Thruster[] thrusterFrontLeft;
    private Thruster[] thrusterBackLeft;
    private Thruster[] thrusterFrontRight;
    private Thruster[] thrusterBackRight;
    
    public Ship(String file,int width,int height,int x,int y) {
        this.img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/ships/"+file));
        this.img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        this.rotationDegree = 0.0f;
        
        this.width = width;
        this.height = height;
        
        this.posX = x;
        this.posY = y;
        
        this.middleX = this.posX + (width / 2.0F);
        this.middleY = this.posY + (height / 2.0F);
        
        this.direction = new Vector2D(0,-1);
        this.force = new Vector2D(0,0);
        
        this.thrusterForward = new Thruster[0];
        this.thrusterBackward = new Thruster[0];
        
        this.thrusterFrontLeft = new Thruster[0];
        this.thrusterBackLeft = new Thruster[0];
        this.thrusterFrontRight = new Thruster[0];
        this.thrusterBackRight = new Thruster[0];
    }
    
    public void updateShip(int mausX,int mausY) {
        this.middleX = this.middleX + this.force.getX();
        this.middleY = this.middleY + this.force.getY();
        
        this.posX = this.middleX - (this.width / 2.0F);
        this.posY = this.middleY - (this.height / 2.0F);
        
        for (int i = 0;i < this.thrusterForward.length; i++) {
            this.thrusterForward[i].updatePositionData(middleX, middleY);
        }
        
        for (int i = 0;i < this.thrusterBackward.length; i++) {
            this.thrusterBackward[i].updatePositionData(middleX, middleY);
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length; i++) {
            this.thrusterFrontLeft[i].updatePositionData(middleX, middleY);
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length; i++) {
            this.thrusterBackLeft[i].updatePositionData(middleX, middleY);
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length; i++) {
            this.thrusterFrontRight[i].updatePositionData(middleX, middleY);
        }
        
        for (int i = 0;i < this.thrusterBackRight.length; i++) {
            this.thrusterBackRight[i].updatePositionData(middleX, middleY);
        }
        
        manageRotation(mausX, mausY);
    }
    
    public void throttleForward() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            thrustStrength += this.thrusterForward[i].getThrustStrength();
            this.thrusterForward[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void throttleBackward() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            thrustStrength += this.thrusterBackward[i].getThrustStrength();
            this.thrusterBackward[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.multiplyWithNumber(-1).getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void throttleRight() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            thrustStrength += this.thrusterFrontLeft[i].getThrustStrength();
            this.thrusterFrontLeft[i].setActive();
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            thrustStrength += this.thrusterBackLeft[i].getThrustStrength();
            this.thrusterBackLeft[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.getTurnedLeftVektor().getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void throttleLeft() {
        float thrustStrength = 0.0f;
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            thrustStrength += this.thrusterFrontRight[i].getThrustStrength();
            this.thrusterFrontRight[i].setActive();
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            thrustStrength += this.thrusterBackRight[i].getThrustStrength();
            this.thrusterBackRight[i].setActive();
        }
        
        this.force = this.force.addVector(this.direction.getTurnedRightVektor().getUnitVector().multiplyWithNumber(thrustStrength));
    }
    
    public void addForce(Vector2D vector) {
        this.force = this.force.addVector(vector);
    }
    
    private void manageRotation(int mausX,int mausY) {
        boolean leftOf = this.direction.isLeftOf(new Vector2D(mausX-this.middleX,mausY-this.middleY));
        Vector2D mouse = new Vector2D(mausX-this.middleX,mausY-this.middleY);
        
        //(float) zum runden (statt 179.9999...° => 180.0°)
        //Funkioniert nicht (sollte mit maximalerdrehgeschwindigkeit zum mauszeiger drehen, eventuell später nochmal überarbeiten bzw neuschreiben)
        /*if((float)(this.direction.getAngle(mouse)) > 1) {
            if (leftOf) {
                if((float)(this.direction.getAngle(mouse)) >= this.rotationLeftThrust) {
                    this.direction = this.direction.getRotatedVector(this.rotationLeftThrust);
                } else {
                    this.direction = this.direction.getRotatedVector(this.direction.getAngle(mouse));
                }
            } else {
                if((float)(this.direction.getAngle(mouse)) >= this.rotationRightThrust) {
                    this.direction = this.direction.getRotatedVector(this.rotationRightThrust * -1.0);
                } else {
                    this.direction = this.direction.getRotatedVector(this.direction.getAngle(mouse) * -1.0);
                }
            }
        } else {*/
            this.direction = mouse.getUnitVector();
        //}
        
        calculateRotationDegree();
    }
    
    private void calculateRotationDegree() {
        boolean isLeft = false;
        float oldRotationDegree = this.rotationDegree;
        Vector2D fixedVector = new Vector2D(0,-1);
        
        if (this.middleX + this.direction.getX() < this.middleX) {
            isLeft = true;
        }
        
        this.rotationDegree = (float)fixedVector.getAngle(this.direction);
        
        if(isLeft) {
            this.rotationDegree = 360 - this.rotationDegree;
        }
        
        if(oldRotationDegree < this.rotationDegree - 0.1) {
            for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
                this.thrusterFrontLeft[i].setActive();
            }

            for (int i = 0;i < this.thrusterBackRight.length;i++) {
                this.thrusterBackRight[i].setActive();
            }
        } else if(oldRotationDegree > this.rotationDegree + 0.1) {
            for (int i = 0;i < this.thrusterFrontRight.length;i++) {
                this.thrusterFrontRight[i].setActive();
            }

            for (int i = 0;i < this.thrusterBackLeft.length;i++) {
                this.thrusterBackLeft[i].setActive();
            }
        }
    }
    
    public void draw(Graphics2D g2d) {
        g2d.rotate(Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
        g2d.drawImage(this.img, (int)this.posX, (int)this.posY, null);
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            this.thrusterForward[i].draw(g2d,this.direction);
        }
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            this.thrusterBackward[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            this.thrusterFrontLeft[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            this.thrusterFrontRight[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            this.thrusterBackLeft[i].draw(g2d);
        }
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            this.thrusterBackRight[i].draw(g2d);
        }
        
        g2d.rotate(-Math.toRadians(this.rotationDegree), (int)this.posX+this.width/2, (int)this.posY+this.height/2);
    }
    
    public void addForwardThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterForward.length + 1];
        
        for (int i = 0;i < this.thrusterForward.length;i++) {
            dummy[i] = this.thrusterForward[i];
        }
        
        dummy[this.thrusterForward.length] = thruster;
        this.thrusterForward = dummy;
    }
    
    public void addBackwardThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterBackward.length + 1];
        
        for (int i = 0;i < this.thrusterBackward.length;i++) {
            dummy[i] = this.thrusterBackward[i];
        }
        
        dummy[this.thrusterBackward.length] = thruster;
        this.thrusterBackward = dummy;
    }
    
    public void addFrontLeftThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterFrontLeft.length + 1];
        
        for (int i = 0;i < this.thrusterFrontLeft.length;i++) {
            dummy[i] = this.thrusterFrontLeft[i];
        }
        
        dummy[this.thrusterFrontLeft.length] = thruster;
        this.thrusterFrontLeft = dummy;
    }
    
    public void addFrontRightThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterFrontRight.length + 1];
        
        for (int i = 0;i < this.thrusterFrontRight.length;i++) {
            dummy[i] = this.thrusterFrontRight[i];
        }
        
        dummy[this.thrusterFrontRight.length] = thruster;
        this.thrusterFrontRight = dummy;
    }
    
    public void addBackLeftThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterBackLeft.length + 1];
        
        for (int i = 0;i < this.thrusterBackLeft.length;i++) {
            dummy[i] = this.thrusterBackLeft[i];
        }
        
        dummy[this.thrusterBackLeft.length] = thruster;
        this.thrusterBackLeft = dummy;
    }
    
    public void addBackRightThruster(Thruster thruster) {
        Thruster[] dummy = new Thruster[this.thrusterBackRight.length + 1];
        
        for (int i = 0;i < this.thrusterBackRight.length;i++) {
            dummy[i] = this.thrusterBackRight[i];
        }
        
        dummy[this.thrusterBackRight.length] = thruster;
        this.thrusterBackRight = dummy;
    }
    
    public Image getImg() {
        return this.img;
    }
    
    public int getDrawX() {
        return (int)this.posX;
    }
    
    public int getDrawY() {
        return (int)this.posY;
    }
    
    public float getMiddleX() {
        return this.middleX;
    }
    
    public float getMiddleY() {
        return this.middleY;
    }
}
