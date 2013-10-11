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
public class Time {
    private int stunden;
    private int minuten;
    private int sekunden;
    
    public Time(int stunden,int minuten,int sekunden) {
        if (stunden >= 0 && stunden < 24) {
            this.stunden = stunden;
        } else if (stunden < 0) {
            this.stunden = 0;
        } else {
            this.stunden = 23;
        }
        
        if (minuten >= 0 && minuten < 60) {
            this.minuten = minuten;
        } else if(minuten < 0) {
            this.minuten = 0;
        } else {
            this.minuten = 59;
        }
        
        if (sekunden >= 0 && sekunden < 60) {
            this.sekunden = sekunden;
        } else if(sekunden < 0) {
            this.sekunden = 0;
        } else {
            this.sekunden = 59;
        }
    }
    
    public Time(int stunden, int minuten) {
        this(stunden,minuten,0);
    }
    
    public Time() {
        GregorianCalendar cal = new GregorianCalendar();
        this.stunden = cal.get(cal.HOUR_OF_DAY);
        this.minuten = cal.get(cal.MINUTE);
        this.sekunden = cal.get(cal.SECOND);
    }
    
    public boolean isLaterThan(Time t) {
        boolean isLater = false;
        
        if(this.stunden > t.stunden) {
            isLater = true;
        } else if (this.stunden == t.stunden) {
            if (this.minuten > t.minuten) {
                isLater = true;
            } else if(this.minuten == t.minuten) {
                if (this.sekunden > t.sekunden) {
                    isLater = true;
                }
            }
        }
        
        return isLater;
    }
    
    public int getStunden() {
        return stunden;
    }

    public void setStunden(int stunden) {
        if (stunden >= 0 && stunden < 24) {
            this.stunden = stunden;
        } else if (stunden < 0) {
            this.stunden = 0;
        } else {
            this.stunden = 23;
        }
    }

    public int getMinuten() {
        return minuten;
    }

    public void setMinuten(int minuten) {
        if (minuten >= 0 && minuten < 60) {
            this.minuten = minuten;
        } else if(minuten < 0) {
            this.minuten = 0;
        } else {
            this.minuten = 59;
        }
    }

    public int getSekunden() {
        return sekunden;
    }

    public void setSekunden(int sekunden) {
        if (sekunden >= 0 && sekunden < 60) {
            this.sekunden = sekunden;
        } else if(sekunden < 0) {
            this.sekunden = 0;
        } else {
            this.sekunden = 59;
        }
    }
    
    public String toString() {
        String stundenS = ""+this.stunden;
        String minutenS = ""+this.minuten;
        String sekundenS = ""+this.sekunden;
        
        if (this.stunden < 10) {
            stundenS = "0"+this.stunden;
        }
        
        if (this.minuten < 10) {
            minutenS = "0"+this.minuten;
        }
        
        if (this.sekunden < 10) {
            sekundenS = "0"+this.sekunden;
        }
        
        return ""+stundenS+":"+minutenS+":"+sekundenS;
    }
}
