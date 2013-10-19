/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

/**
 *
 * @author Sami
 */
public class FPSManager {
    private long FPS;
    private long FPSstart;
    private long FPScurrent;
    
    private long lastFrameTime;
    private int expectedFPS;
    
    private long FPSlimiter_Millisecs;
    private int FPSlimiter_NanoSecs;
    
    public FPSManager() {
        this.FPS = 60;
        this.FPSstart = System.currentTimeMillis();
        this.FPScurrent = 0;
        
        lastFrameTime = FPSstart;  
        expectedFPS = 125;
        
        FPSlimiter_Millisecs = 10;
        FPSlimiter_NanoSecs = 0;
    }
    
    public void fpsCount() {
        if (System.currentTimeMillis() - this.FPSstart <= 1000) {
            this.FPScurrent++;
        } else {
            this.FPS = this.FPScurrent;
            this.FPScurrent = 0;
            this.FPSstart = System.currentTimeMillis();
        }
        
        double frameDuration = System.currentTimeMillis() - lastFrameTime;  //Rechnet aus wie lange das Frame gebraucht hat: Zeit der letzten Berechnung in ms - Aktuelle Zeit in ms
        lastFrameTime = System.currentTimeMillis(); //Zeit der letzten Berechnung wird auf die aktuelle Zeit in ms gesetzt
        
        frameDuration = frameDuration - this.FPSlimiter_Millisecs;  //Die frameDuration variable wird weiterverwendet als Speicher für die tatsächliche Dauer ohne der Thread verzögerung: Gesamt Frame dauer - den zuletzt verwendeten limiter
        double limiter = (1000 / expectedFPS) - frameDuration;  //Der neue limiter wird ausgerechnet mit der gewünschten Zeit für ein Frame (1000ms / gewünschte anzahl der Frames pro sekunde) - der tatsächlichen dauer für ein Frame
        
        if (limiter < 0) { //Wenn der limiter negativ wäre, also das ausrechnen des Frames schon mehr zeit benötigt als es insgesamt eigt sollte
            limiter = 0; //Wird der limiter auf 0 gesetzt, da er die Frame zeit sowieso nur verlängern würde und um eine negativ Wert Exception zu vermeiden
        }
        
        this.FPSlimiter_Millisecs = (long)limiter; //Der ausgerechnete limiter wird schließlich der Rückgabewert Variable zugewiesen auf die dann im Thread.sleep(..) zugegriffen wird
    }
    
    public long getFPSlimiter_Millisecs() {
        return this.FPSlimiter_Millisecs;
    }
    
    public int getFPSlimiter_NanoSecs() {
        return this.FPSlimiter_NanoSecs;
    }
    
    public long getFPS() {
        return this.FPS;
    }
}
