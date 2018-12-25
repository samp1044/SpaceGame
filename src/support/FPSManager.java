/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.text.DecimalFormat;

/**
 *
 * @author Sami
 */
public class FPSManager {
    public static double DELTA = 0.001;
    
    private long FPS;
    private long FPSstart;
    private long FPScurrent;
    
    private long frameBeginnTime;
    private long deltaTime;
    private long expectedDeltaTime;
    private long expectedFPS;
    
    private long FPSlimiter_Millisecs;
    private long FPSlimiter_NanoSecs;
    
    public FPSManager() {
        this.FPS = 0;
        this.FPSstart = System.nanoTime();
        this.FPScurrent = 0;
        
        frameBeginnTime = FPSstart;  
        expectedFPS = 250;
        
        deltaTime = 0;
        expectedDeltaTime = (1000 * 1000 * 1000) / expectedFPS;
        
        FPSlimiter_Millisecs = 0;
        FPSlimiter_NanoSecs = 0;
    }
    
    public void manageFrameCap() {
        deltaTime = System.nanoTime() - frameBeginnTime;
        
        if (deltaTime <= expectedDeltaTime) {
            this.FPSlimiter_Millisecs = ((expectedDeltaTime - deltaTime) / 1000000);
            this.FPSlimiter_NanoSecs = ((expectedDeltaTime - deltaTime) % 1000000);
        } else {
            this.FPSlimiter_Millisecs = 0;
            this.FPSlimiter_NanoSecs = 0;
        }
        
        
        
        /*double frameDuration = System.currentTimeMillis() - lastFrameTime;  //Rechnet aus wie lange das Frame gebraucht hat: Zeit der letzten Berechnung in ms - Aktuelle Zeit in ms
        lastFrameTime = System.currentTimeMillis(); //Zeit der letzten Berechnung wird auf die aktuelle Zeit in ms gesetzt
        
        if (frameDuration > 0) {    //Java verschlampt rechnung zeitweise -> DELTA = 0 führt zu flackern in Bewegungen
            DELTA = frameDuration / 1000;
        }
        
        frameDuration = frameDuration - this.FPSlimiter_Millisecs;  //Die frameDuration variable wird weiterverwendet als Speicher für die tatsächliche Dauer ohne der Thread verzögerung: Gesamt Frame dauer - den zuletzt verwendeten limiter
        double limiter = (1000 / expectedFPS) - frameDuration;  //Der neue limiter wird ausgerechnet mit der gewünschten Zeit für ein Frame (1000ms / gewünschte anzahl der Frames pro sekunde) - der tatsächlichen dauer für ein Frame
        
        if (limiter < 0) { //Wenn der limiter negativ wäre, also das ausrechnen des Frames schon mehr zeit benötigt als es insgesamt eigt sollte
            limiter = 0; //Wird der limiter auf 0 gesetzt, da er die Frame zeit sowieso nur verlängern würde und um eine negativ Wert Exception zu vermeiden
        }
        /* 
        * Momentan nicht benutzt da auf DELTA System umgestellt*/
        
        //this.FPSlimiter_Millisecs = 0;//(long)Math.round(limiter);//(long)limiter; //Der ausgerechnete limiter wird schließlich der Rückgabewert Variable zugewiesen auf die dann im Thread.sleep(..) zugegriffen wird
    }
    
    public void beginnTime() {
        frameBeginnTime = System.nanoTime();
    }
    
    public void setDelta() {
        deltaTime = System.nanoTime() - frameBeginnTime;
        
        if (deltaTime > 0) {
            DELTA = deltaTime / 1000000000.0;
        }
    }
    
    public void fpsCount() {
        if (System.nanoTime() - this.FPSstart <= 1000000000) {
            this.FPScurrent++;
        } else {
            this.FPS = this.FPScurrent;
            this.FPScurrent = 0;
            this.FPSstart = System.nanoTime();
        }
    }
    
    public long getFPSlimiter_Millisecs() {
        return this.FPSlimiter_Millisecs;
    }
    
    public long getFPSlimiter_NanoSecs() {
        return this.FPSlimiter_NanoSecs;
    }
    
    public long getFPS() {
        return this.FPS;
    }
}
