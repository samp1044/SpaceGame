/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.GregorianCalendar;

/**
 *
 * @author Sami
 */
public class Date {
    private int tag;
    private int monat;
    private int jahr;
    private int dayOfYear;
    
    public Date(int tag,int monat,int jahr) {
        this.tag = tag;
        this.monat = monat;
        this.jahr = jahr;
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(this.jahr, this.monat, this.tag);
        this.dayOfYear = cal.get(GregorianCalendar.DAY_OF_YEAR);
    }
    
    public Date() {
        GregorianCalendar cal = new GregorianCalendar();
        this.jahr = cal.get(GregorianCalendar.YEAR);
        this.monat = cal.get(GregorianCalendar.MONTH);
        this.tag = cal.get(GregorianCalendar.DAY_OF_MONTH);
        this.dayOfYear = cal.get(GregorianCalendar.DAY_OF_YEAR);
        
    }
    
    public boolean isBiggerThan(Date d) {
        boolean isBigger = false;
        
        if (this.jahr > d.jahr) {
            isBigger = true;
        } else if (this.jahr == d.jahr && this.dayOfYear > d.dayOfYear) {
            isBigger = true;
        }
        
        return isBigger;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getMonat() {
        return monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    public int getJahr() {
        return jahr;
    }
    
    public int getDayOfYear() {
        return this.dayOfYear;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }
}
