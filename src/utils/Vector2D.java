/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Point;

/**
 *
 * @author Sami
 */
public class Vector2D {
    private double x;
    private double y;
    
    public Vector2D(double x,double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2D(Point p) {
        this(p.x,p.y);
    }
    
    public Vector2D(float fromX,float fromY,float toX,float toY) {
        this(toX - fromX,toY - fromY);
    }
    
    public Vector2D(double fromX,double fromY,double toX,double toY) {
        this(toX - fromX,toY - fromY);
    }
    
    public Vector2D(Point from,Point to) {
        this(to.x - from.x,to.y - from.y);
    }
    
    public Vector2D() {
        this(0.0f,0.0f);
    }
    
    /**
     * Einheitsvektor.
     */
    public Vector2D getUnitVector() {
        Vector2D v = new Vector2D();
        double length = Math.sqrt((this.x * this.x) + (this.y * this.y));
        
        if (length == 0) {
            int a = 0;
        }
        
        v.setX((this.x/length));
        v.setY((this.y/length));
        
        v.checkForInvalidNumber();
        
        return v;
    }
    
    /**
     * Betrag des Vektors.
     */
    public double getLength() {
        double length = 0.0;
        
        length = Math.sqrt((this.x * this.x) + (this.y * this.y));
        
        return length;
    }
    
    /**
     * Linksgedrehter Vektor.
     * <br>
     * (Normalvektor auf die Linke Seite, X-Koordinate * -1).
     */
    public Vector2D getTurnedLeftVektor() {
        Vector2D v = new Vector2D();
        
        v.setY(this.x);
        v.setX(this.y * -1.0f);
        
        return v;
    }
    
    /**
     * Rechtsgedrehter Vektor.
     * <br>
     * (Normalvektor auf die Rechte Seite, Y-Koordinate * -1).
     */
    public Vector2D getTurnedRightVektor() {
        Vector2D v = new Vector2D();
        
        v.setY(this.x * -1.0f);
        v.setX(this.y);
        
        return v;
    }
    
    /**
     * Addiert 2 Vektoren miteinander und liefert das Ergebnis als Vector2D zurück.
     */
    public Vector2D addVector(Vector2D v) {
        Vector2D vector = new Vector2D();
        
        vector.setX(this.x + v.x);
        vector.setY(this.y + v.y);
        
        return vector;
    }
    
    /**
     * Subtrahiert 2 Vektoren miteinander und liefert das Ergebnis als Vector2D zurück.
     */
    public Vector2D subtractVector(Vector2D v) {
        Vector2D vector = new Vector2D();
        
        vector.setX(this.x - v.x);
        vector.setY(this.y - v.y);
        
        return vector;
    }
    
    /**
     * Multipliziert die X und die Y Koordinate mit der übergebenen Zahl und liefert das Ergebnis als Vector2D zurück.
     */
    public Vector2D multiplyWithNumber(double number) {
        Vector2D vector = new Vector2D();
        
        vector.setX((this.x * number));
        vector.setY((this.y * number));
        
        return vector;
    }
    
    /**
     * Dividiert die X und die Y Koordinate mit der übergebenen Zahl und liefert das Ergebnis als Vector2D zurück.
     */
    public Vector2D divideByNumber(double number) {
        Vector2D vector = new Vector2D();
        
        vector.setX((this.x / number));
        vector.setY((this.y / number));
        
        vector.checkForInvalidNumber();
        
        return vector;
    }
    
    /**
     * Liefert das Skalarprodukt als Double wert zurück. 
     */
    public double getScalarProduct(Vector2D v) {
        double scalarP = 0.0;
        
        scalarP = this.x * v.x + this.y * v.y;
        
        return scalarP;
    }
    
    /**
     * Berechnet den Winkel zwischen dem aktuellen Vektor und den übergebenen Vektor v. 
     * Der Winkel wird in Grad als Double Wert zurückgeliefert.<br><br>
     * Durch die ungenauigkeit von Double empfiehlt es sich den Return wert auf float zu casten,
     * um etwa statt 179.999..° ganze 180.0° zu bekommen.
     */
    public double getAngle(Vector2D v) {
        double angle = 0.0;
        
        angle = Math.acos(getScalarProduct(v) / (getLength() * v.getLength()));
        angle = angle * 180 / Math.PI;
        
        return angle;
    }
    
    /**
     * <b>Funktioniert nicht!</b>
     */
    public double getFullAngle(Vector2D v) {
        double angle = 0.0;
        
        angle = Math.acos(getScalarProduct(v) / (getLength() * v.getLength()));
        angle = angle * 180 / Math.PI;
        
        if(this.isLeftOf(v)) {
            angle = 360 - angle;
        }
        
        return angle;
    }
    
    /**
     * Liefert den um die übergebene Gradzahl gedrehten Vektor zurück. Gedreht wird gegen den
     * Uhrzeiger, um mit dem Uhrzeiger zu drehen einen negativen double Wert übergeben.
     */
    public Vector2D getRotatedVector(double degree) {
        Vector2D v = new Vector2D();
        
        degree = Math.toRadians(degree);
        
        v.x =  (this.x * Math.cos(degree) - this.y * Math.sin(degree));
        v.y =  (this.x * Math.sin(degree) + this.y * Math.cos(degree));
        
        v.checkForInvalidNumber();
        
        return v;
    }
    
    /**
     * Bildet 3 Point Objekte mit den übergebenen Daten. Das Erste ist ein angenommener Punkt,
     * das Zweite der 1.Punkt plus der Vektor Instanz und das Dritte der 1.Punkt + dem übergebenen
     * Vektor. 
     * <br><br>
     * Möglicher Genauigkeitsverlust durch Konvertierung in int
     */
    public boolean isLeftOf(Vector2D v) {
        //Hat Probleme bei 90° Winkeln
        /*Point a = new Point(1,1);
        Point b = new Point();
        Point c = new Point();
        
        b.x = (int)(a.x + this.x);
        b.y = (int)(a.y + this.y);
        
        c.x = (int)(a.x + v.x);
        c.y = (int)(a.y + v.x);
        
        return ((b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x)) > 0;*/
        
        /* Alternativ version die die Winkel vergleicht:*/
         
        Vector2D instanceV = new Vector2D(this.x,this.y);
        double angle1 = instanceV.getAngle(v);
        
        instanceV = instanceV.getRotatedVector(0.1);
        
        double angle2 = instanceV.getAngle(v);
        
        if (angle2 <= angle1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Bildet 3 Point Objekte mit den übergebenen Daten. Das Erste ist ein angenommener Punkt,
     * das Zweite der 1.Punkt plus der Vektor Instanz und das Dritte ist der übergebene Punkt 
     * <br><br>
     * Möglicher Genauigkeitsverlust durch Konvertierung in int
     */
    public boolean isLeftOf(Point c) {
        Point a = new Point(1,1);
        Point b = new Point();
        
        b.x = (int)(a.x + this.x);
        b.y = (int)(a.y + this.y);
        
        return ((b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x)) > 0;
    }
    
    public boolean equals(Object v) {
        boolean isEqual = false;
        Vector2D vec = null;
        
        if (v == null) {
            return isEqual;
        }
        
        if (this.getClass() != v.getClass()) {
            return isEqual;
        }
        
        vec = (Vector2D) v;
        
        if (this.x == vec.x && this.y == vec.y) {
            isEqual = true;
        }
        
        return isEqual;
    }
    
    private void checkForInvalidNumber() {
        if (Double.isNaN(this.x)) {
            this.x = 0;
        }
        
        if (Double.isNaN(this.y)) {
            this.y = 0;
        }
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public String toString() {
        return "2D Vector (x: "+this.x+"/y: "+this.y+")";
    }
}
