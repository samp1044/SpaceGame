/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.Graphics2D;
import java.awt.Point;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class FixedCollisionBox extends CollisionBox {
    double rotation;
    public Point[][] coord;
    
    public FixedCollisionBox(float middleX,float middleY,int width,int height) {
        super(middleX,middleY,width,height);
        this.coord = new Point[0][0];
        setRotation(0);
    }
    
    public FixedCollisionBox(double middleX,double middleY,int width,int height) {
        super((float)middleX,(float)middleY,width,height);
        this.coord = new Point[0][0];
        setRotation(0);
    }
    
    public FixedCollisionBox(CollisionBox b) {
        super(b);
        this.coord = new Point[0][0];
        setRotation(0);
    }
    
    public FixedCollisionBox(FixedCollisionBox b) {
        super(b);
        this.coord = new Point[0][0];
        setRotation(b.rotation);
    }
    
    @Override
    public void updateBox(float middleX, float middleY) {
        super.updateBox(middleX, middleY);
        setRotation(this.rotation);
    }
    
    @Override
    public boolean isOverlapping(CollisionBox b) {
        boolean overlaps = false;
        
        OuterLoop:
        for (int i = 0;i < this.coord.length;i++) {
            for (int j = 0;j < this.coord[i].length;j++) {
                if (b.isInside((int)this.coord[i][j].getX(), (int)this.coord[i][j].getY())) {
                    overlaps = true;
                    break OuterLoop;
                }
            }
        }
        
        return overlaps;
    }
    
    @Override
    public boolean isOverlapping(FixedCollisionBox b) {
        boolean overlaps = false;
        
        OuterLoop:
        for (int i = 0;i < this.coord.length;i++) {
            for (int j = 0;j < this.coord[i].length;j++) {
                if (b.isInside((int)this.coord[i][j].getX(), (int)this.coord[i][j].getY())) {
                    overlaps = true;
                    break OuterLoop;
                }
            }
        }
        
        return overlaps;
    }
    
    public boolean isInside(int tX,int tY) {
        boolean inside = false;
        
        OuterLoop:
        for (int i = 0;i < this.coord.length;i++) {
            for (int j = 0;j < this.coord[i].length;j++) {
                if (tX == (int)this.coord[i][j].getX() && tY == (int)this.coord[i][j].getY()) {
                    inside = true;
                    break OuterLoop;
                }
            }
        }
        
        return inside;
    }
    
    public  void setRotation (double angle) {
        this.rotation = angle;
        
        Vector2D helpingVector = new Vector2D(middleX,middleY,x,y); //Vektor der auf die eckpunkte (x,y) von der mitte (middleX, middleY) zeigt, helping vektor weil er öfters benutzt werden sollte, im endeffekt aber nur einmal benutzt wurde
        Vector2D widthV = new Vector2D(width,0); //Vektor der von den eckpunkten (x,y) mit der länge width nach rechts zu dem eckpunkt (x + width, y) zeigt, also die obere Kante von dem Rechteck
        Vector2D heightV = new Vector2D(0,height); //Vektor der von den eckpunkten (x,y) mit der länge height nach unten zu dem eckpunkt (x, y + height) zeigt, also die linke Kante von dem Rechteck
        
        helpingVector = helpingVector.getRotatedVector(this.rotation); //Rotieren des Vektors zu den Eckpunkten um die gespeicherte gradzahl. Er zeigt am ende also von den mittelkoordinaten zu dem rotierten eckpunkt
        widthV = widthV.getRotatedVector(rotation); //Rotieren des weiten vektors um die gleiche gradzahl  sodass die obere kante auch entsprechend gekippt wird
        heightV = heightV.getRotatedVector(rotation); //Rotieren des höhen vektors um die gradzahl, dass auch die linke kante entsprechend gekippt wird
        
        int currentX = (int)(this.middleX + helpingVector.getX()); //In currentX werden die tatsächlichen eckpunkte (linke, obere ecke) nach dem rotieren um den mittelpunkt gespeichert
        int currentY = (int)(this.middleY + helpingVector.getY()); //also mittelkoordinaten + rotierterVektor
        
        int verticalX = currentX; //die berechneten eckpunkte werden in die "working" variablen kopiert, mit denen nachher gerechnet wird
        int verticalY = currentY;
        
        int workingX = verticalX;
        int workingY = verticalY;
        
        int widthLength = (int)widthV.getLength(); //Es wird die Länge der gedrehten oberen kante ermittelt, wird für die anzahl der durchläufe in der forschleife nachher verwendet
        int heightLength = (int)heightV.getLength(); //Die Länge der gedrehten linken Katen wird ermittelt, ebenfalls für die forschleife
        
        if (widthLength <= 0) { //Falls die weite so klein war das der gedrehte vektor aufgrund von der rundung in int eine länge von 0 hätte wird er hier auf 1 gesetzt, damit die forschleife mindestens einmal ausgeführt wird
                                //also wenigstens der linke obere eckpunkt auf collision geprüft wird
            widthLength = 1;
        }
        
        if (heightLength <= 0) { //Gleiches bei der länge der höhe
            heightLength = 1;
        }
        
        widthV = widthV.getUnitVector(); //die gedrehten vektoren werden auf die länge 1 gesetzt damit sie nachher multipliziert werden können
        heightV = heightV.getUnitVector(); //gleiches bei höhe
        
        this.coord = new Point[widthLength][heightLength];
        
        Label: //Label um aus der innersten for schleife alle schleifen zu beenden und mit dem return statement fortzufahren
        for (int i = 0;i < heightLength;i++) { //Äußere Schleife geht die koordinaten der gedrehten linken Kante durch, sie läuft also so lange wie die linke Kante lang ist (in gerundeten int werten)
            verticalX = currentX + (int)heightV.multiplyWithNumber(i).getX(); //zu den eckpunkten werden die koordinaten des höhenvektors dazuaddiert der vorher mit der i variable multipliziert wird, dadurch werden
            verticalY = currentY + (int)heightV.multiplyWithNumber(i).getY(); //Die koordinaten der linken Kante schritt für schritt durchgegangen
            
            workingX = verticalX; //Die workingX werden mit den aktualisierten currentX gleichgesetzt damit mit den workingX gerechnet werden kann und die currentX unangetastet bleiben
            workingY = verticalY; //Die currentX sind immer die koordinaten von der gedrehten linken kante, wäre sie NICHT GEDREHT, wären die current Koordinaten (0,0) - (0,1) - (0,2) - (0,3) - usw. es würden also immer die Y koordinaten erhöht werden, also die vertikale anfangslinie
            
            for (int j = 0;j < widthLength;j++) { //Innere Schleife geht die koordinaten der oberen Kante durch, sie läuft solange wie die obere Kante lang war.
                workingX = verticalX + (int)widthV.multiplyWithNumber(j).getX(); //Die working koordinaten sind immer die versetzte obere Kante, wäre sie NICHT GEDREHT wären sie im ersten durchlauf (0,0) - (1,0) - (2,0) - (3,0) - usw. es würde also immer die X koordinaten erhöht werden
                workingY = verticalY + (int)widthV.multiplyWithNumber(j).getY(); //Es wird also die in der äußeren schleife immer um eins nach unten versetzte horizontale Linie durchgegangen
                
                this.coord[j][i] = new Point(workingX,workingY);
            }
        }
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawLine((int)Background.X + (int)this.coord[0][0].getX(),(int)Background.Y +  (int)this.coord[0][0].getY(),(int)Background.X +  (int)this.coord[this.coord.length - 1][0].getX(),(int)Background.Y +  (int)this.coord[this.coord.length - 1][0].getY());
        g2d.drawLine((int)Background.X + (int)this.coord[this.coord.length - 1][0].getX(), (int)Background.Y + (int)this.coord[this.coord.length - 1][0].getY(),(int)Background.X +  (int)this.coord[this.coord.length - 1][this.coord[0].length - 1].getX(),(int)Background.Y +  (int)this.coord[this.coord.length - 1][this.coord[0].length - 1].getY());
        g2d.drawLine((int)Background.X + (int)this.coord[this.coord.length - 1][this.coord[0].length - 1].getX(),(int)Background.Y +  (int)this.coord[this.coord.length - 1][this.coord[0].length - 1].getY(),(int)Background.X +  (int)this.coord[0][this.coord[0].length - 1].getX(),(int)Background.Y +  (int)this.coord[0][this.coord[0].length - 1].getY());
        g2d.drawLine((int)Background.X + (int)this.coord[0][this.coord[0].length - 1].getX(),(int)Background.Y +  (int)this.coord[0][this.coord[0].length - 1].getY(),(int)Background.X +  (int)this.coord[0][0].getX(),(int)Background.Y +  (int)this.coord[0][0].getY());
    }
    
    public String toString() {
        String s = "FixedCollisionBox:\n";
        s += "rotation angle: "+this.rotation+" \n\n";
        
        for (int i = 0;i < this.coord.length;i++) {
            for (int j = 0;j < this.coord[i].length;j++) {
                s += (""+ this.coord[i][j].getX() + ":" + this.coord[i][j].getY() + " ");
            }
            
            s += "\n";
        }
        
        return s;
    }
}
