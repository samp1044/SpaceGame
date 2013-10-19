/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import utils.Vector2D;

/**
 * @author Sami
 */
public class CollisionBoxOld {
    private float x;
    private float y;
    
    private int width;
    private int height;
    
    private float middleX;
    private float middleY;
    
    double rotation;
    
    public CollisionBoxOld(float middleX,float middleY,int width,int height) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.width = width;
        this.height = height;
        
        this.x = this.middleX - (this.width / 2);
        this.y = this.middleY - (this.height / 2);
        
        this.rotation = 0;
    }
    
    public CollisionBoxOld(double middleX,double middleY,int width,int height) {
        this((float)middleX,(float)middleY,width,height);
    }
    
    public CollisionBoxOld() {
        this(0,0,0,0);
    }
    
    public void updateBox(float middleX, float middleY,double rotation) {
        this.middleX = middleX;
        this.middleY = middleY;
        
        this.x = this.middleX - (this.width / 2);
        this.y = this.middleY - (this.height / 2);
        
        this.rotation = rotation;
    }
    
    public boolean isOverlapping(CollisionBoxOld b) {
        boolean overlaps = false;
        
        Vector2D helpingVector = new Vector2D(b.middleX,b.middleY,b.x,b.y); //Vektor der auf die eckpunkte (x,y) von der mitte (middleX, middleY) zeigt, helping vektor weil er öfters benutzt werden sollte, im endeffekt aber nur einmal benutzt wurde
        Vector2D widthV = new Vector2D(b.width,0); //Vektor der von den eckpunkten (x,y) mit der länge width nach rechts zu dem eckpunkt (x + width, y) zeigt, also die obere Kante von dem Rechteck
        Vector2D heightV = new Vector2D(0,b.height); //Vektor der von den eckpunkten (x,y) mit der länge height nach unten zu dem eckpunkt (x, y + height) zeigt, also die linke Kante von dem Rechteck
        
        helpingVector = helpingVector.getRotatedVector(b.rotation); //Rotieren des Vektors zu den Eckpunkten um die gespeicherte gradzahl. Er zeigt am ende also von den mittelkoordinaten zu dem rotierten eckpunkt
        widthV = widthV.getRotatedVector(b.rotation); //Rotieren des weiten vektors um die gleiche gradzahl  sodass die obere kante auch entsprechend gekippt wird
        heightV = heightV.getRotatedVector(b.rotation); //Rotieren des höhen vektors um die gradzahl, dass auch die linke kante entsprechend gekippt wird
        
        int currentX = (int)(b.middleX + helpingVector.getX()); //In currentX werden die tatsächlichen eckpunkte (linke, obere ecke) nach dem rotieren um den mittelpunkt gespeichert
        int currentY = (int)(b.middleY + helpingVector.getY()); //also mittelkoordinaten + rotierterVektor
        
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
        
        Label: //Label um aus der innersten for schleife alle schleifen zu beenden und mit dem return statement fortzufahren
        for (int i = 0;i < heightLength;i++) { //Äußere Schleife geht die koordinaten der gedrehten linken Kante durch, sie läuft also so lange wie die linke Kante lang ist (in gerundeten int werten)
            verticalX = currentX + (int)heightV.multiplyWithNumber(i).getX(); //zu den eckpunkten werden die koordinaten des höhenvektors dazuaddiert der vorher mit der i variable multipliziert wird, dadurch werden
            verticalY = currentY + (int)heightV.multiplyWithNumber(i).getY(); //Die koordinaten der linken Kante schritt für schritt durchgegangen
            
            workingX = verticalX; //Die workingX werden mit den aktualisierten currentX gleichgesetzt damit mit den workingX gerechnet werden kann und die currentX unangetastet bleiben
            workingY = verticalY; //Die currentX sind immer die koordinaten von der gedrehten linken kante, wäre sie NICHT GEDREHT, wären die current Koordinaten (0,0) - (0,1) - (0,2) - (0,3) - usw. es würden also immer die Y koordinaten erhöht werden, also die vertikale anfangslinie
            
            for (int j = 0;j < widthLength;j++) { //Innere Schleife geht die koordinaten der oberen Kante durch, sie läuft solange wie die obere Kante lang war.
                workingX = verticalX + (int)widthV.multiplyWithNumber(j).getX(); //Die working koordinaten sind immer die versetzte obere Kante, wäre sie NICHT GEDREHT wären sie im ersten durchlauf (0,0) - (1,0) - (2,0) - (3,0) - usw. es würde also immer die X koordinaten erhöht werden
                workingY = verticalY + (int)widthV.multiplyWithNumber(j).getY(); //Es wird also die in der äußeren schleife immer um eins nach unten versetzte horizontale Linie durchgegangen
                //System.out.println("Inside "+workingX+" "+workingY);
                if (isInside(workingX, workingY)) { //Im if werden schließlich die ausgerechneten koordinaten mit den übergebenen verglichen ob diese gleich sind -> ob sie in der collision box liegen. Es wird ausschließlich mit INT gerechnet und alle Koordinaten dafür gerundet
                    overlaps = true; //Wenn sie drinnen sind wird die return variable inside auf true gesetzt
                    break Label; //und die schleife mit break <Label> unterbrochen, sodass mit dem return statement fortgefahren wird
                } 
            }
            
            //System.out.println("Inside -------------------");
        }
        
        return overlaps;
    }
    
    public boolean isInside(int tX,int tY) {
        boolean inside = false; //Variable die am Ende zurückgegeben wird
        
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
        
        Label: //Label um aus der innersten for schleife alle schleifen zu beenden und mit dem return statement fortzufahren
        for (int i = 0;i < heightLength;i++) { //Äußere Schleife geht die koordinaten der gedrehten linken Kante durch, sie läuft also so lange wie die linke Kante lang ist (in gerundeten int werten)
            verticalX = currentX + (int)heightV.multiplyWithNumber(i).getX(); //zu den eckpunkten werden die koordinaten des höhenvektors dazuaddiert der vorher mit der i variable multipliziert wird, dadurch werden
            verticalY = currentY + (int)heightV.multiplyWithNumber(i).getY(); //Die koordinaten der linken Kante schritt für schritt durchgegangen
            
            workingX = verticalX; //Die workingX werden mit den aktualisierten currentX gleichgesetzt damit mit den workingX gerechnet werden kann und die currentX unangetastet bleiben
            workingY = verticalY; //Die currentX sind immer die koordinaten von der gedrehten linken kante, wäre sie NICHT GEDREHT, wären die current Koordinaten (0,0) - (0,1) - (0,2) - (0,3) - usw. es würden also immer die Y koordinaten erhöht werden, also die vertikale anfangslinie
            
            for (int j = 0;j < widthLength;j++) { //Innere Schleife geht die koordinaten der oberen Kante durch, sie läuft solange wie die obere Kante lang war.
                workingX = verticalX + (int)widthV.multiplyWithNumber(j).getX(); //Die working koordinaten sind immer die versetzte obere Kante, wäre sie NICHT GEDREHT wären sie im ersten durchlauf (0,0) - (1,0) - (2,0) - (3,0) - usw. es würde also immer die X koordinaten erhöht werden
                workingY = verticalY + (int)widthV.multiplyWithNumber(j).getY(); //Es wird also die in der äußeren schleife immer um eins nach unten versetzte horizontale Linie durchgegangen
                //System.out.println("Inside "+workingX+" "+workingY);
                if (workingX == tX && workingY == tY) { //Im if werden schließlich die ausgerechneten koordinaten mit den übergebenen verglichen ob diese gleich sind -> ob sie in der collision box liegen. Es wird ausschließlich mit INT gerechnet und alle Koordinaten dafür gerundet
                    inside = true; //Wenn sie drinnen sind wird die return variable inside auf true gesetzt
                    break Label; //und die schleife mit break <Label> unterbrochen, sodass mit dem return statement fortgefahren wird
                } 
            }
            
            //System.out.println("Inside -------------------");
        }
        
        return inside;
    }
    

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
