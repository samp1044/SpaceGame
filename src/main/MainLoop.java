/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import support.Background;
import window.Fenster;
import units.Thruster;
import units.PlayerShip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import support.FPSManager;
import support.Resources;
import support.Settings;
import units.Bullet;
import units.Particle;
import units.Weapon;
import utils.Logger;
import utils.ObjectList;
import utils.Vector2D;

/**
 * Hauptschleife des Spiels und gleichzeitig grafische Hauptausgabe als
 * Canvas Objekt. 
 * @author Sami
 */
public class MainLoop extends Canvas implements Runnable {
    Fenster fenster;
    
    //Buffer Variablen für das Rendering
    Image dbImage;
    Graphics g;
    
    //FpsManager Objekt der die Framerate verwaltet und die Thread Unterbrechungszeiten berechnet um die Framerate konstant zu halten
    FPSManager fps_manager;
    
    //Background Objekt das die Darstellung und die Berechnung der Hintergrundposition durchführt
    Background background;
    //Ressourcen Objekt in dem alle externen Ressourcen gespeichert werden. Statisch damit vom ganzen Programm aus darauf zugegriffen werden kann
    public static Resources resources;
    
    //Das aktuelle Spielerschiff
    PlayerShip playerShip;
    
    public static boolean focus; //Boolean variable die anzeigt ob das Spielfenster sich gerade im Fokus befindet
    public static boolean resized; //Bollean variable die angibt ob das Spielfenster in der Größe verändert wurde
    
    public static ObjectList particles; //Liste aller Partikel die vom Spiel verwaltet werden sollen
    public static ObjectList bullets; //Liste aller Schuss Objekte die vom Spiel verwaltet werden sollen
    
    public static int mausX; //Aktuelle Mausposition (relativ zu den Fenster koordinaten also ausgehend von 0,0 des Fensters)
    public static int mausY;
    
    Logger logger = new Logger(MainLoop.class); //(Eigenes) Logger objekt für Debug ausgaben
    
    /**
     * Default Konstruktor in dem alle Variablen die für das Spiel benötigt werden initialisiert werden
     */
    public MainLoop() {
        //Initialisieren des Canvas Objekts also der "Zeichenfläche"
        this.setIgnoreRepaint(true);    //Einstellen das Refresh Aufforderungen vom Betriebssystem ignoriert werden
        this.setSize(800,600); //Setzen der Default Größe der Zeichenfläche
        this.setBackground(Color.BLACK); //Setzen der Default Hintergrund farbe der Zeichenfläche
        
        dbImage = null; //Initialisieren des Buffer Images mit einer null Referenz
        
        this.fenster = new Fenster(this); //Ein neues Fenster wird erstellt an das die Zeichenfläche übergeben wird (this)
        
        resources = new Resources(); //Das Ressourcen Objekt wird initialisiert
        
        fps_manager = new FPSManager(); //Der FPS Manager wird initialisiert
        
        focus = true; //Standartmäßig hat das Spiel den Fokus wenn es gestartet wird, also wird die Fokus variable mit true initialisiert
        resized = false; //Standartmäßig ist das Fenster auf einer festen Größe beim Starten also wird die Resized Variable mit false initialisiert
        
        particles = new ObjectList(); //Die Partikel Liste wird mit einer leeren ObjectList initialisiert
        bullets = new ObjectList(); //Die Schuss Liste wird mit einer leeren ObjectList initialisiert
        
        //Die maus koordinaten werden mit 0 initialisiert
        mausX = 0;  
        mausY = 0;
        
        loadResources(); //Laden der Ressourcen nachdem alle Variablen initialisiert wurden.
    }
    
    /**
     * Lädt alle externen für das Spiel im Resources Objekt und warten in einem eigenen Thread bis alle Ressourcen geladen
     * wurden.
     */
    private void loadResources() {
        new Thread() {
            boolean running = true;
            
            @Override
            public void run() {
                resources.loadResources();
                
                while(running) {
                    logger.debug("Warte auf Laden der Resourcen");
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        logger.error("Error bei Thread.sleep während dem Resourcenladen : "+e);
                    }
                    
                    if(Resources.loadingFinished) {
                        running = false;
                    }
                }
                
                logger.info("Resourcen erfolgreich geladen");
                initObjects();  //Wurden die Ressourcen erfolgreich geladen werden die Spielobjekte initialisiert
            }
        }.start();
    }
    
    /**
     * Initialisiert die Spielobjekte wie den Hintergrund und das Spielerschiff
     */
    private void initObjects() {
        background = new Background(resources); //Hintergrund wird initialisert und vorbereitet
        
        playerShip = new PlayerShip(PlayerShip.DUMMY,50,50,(this.getWidth()/2) - 25,(this.getHeight()/2) - 25,100,resources); //Playerschiff wird initialisiert mit den entsprechenden Größen und mit der Art angabe
        
        //Hinzufügen der Triebwerksobjekte für das aktuelle Playerschiff  (eventuell als Vorlage irgendwo speichern und automatisiert je nach Typ zuweisen lassen)
        Thruster forwardThruster = new Thruster(Thruster.STANDART,10,10,0.02f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),-6,26,180.0);
        
        playerShip.addForwardThruster(forwardThruster);
        playerShip.addBackwardThruster(new Thruster(Thruster.STANDART,10,10,0.01f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),-17,-30,-50.0));
        playerShip.addBackwardThruster(new Thruster(Thruster.STANDART,10,10,0.01f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),7,-30,50.0));
        playerShip.addFrontLeftThruster(new Thruster(Thruster.STANDART,10,10,0.01f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),-22,-13,-90.0));
        playerShip.addFrontRightThruster(new Thruster(Thruster.STANDART,10,10,0.01f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),12,-13,90.0));
        playerShip.addBackLeftThruster(new Thruster(Thruster.STANDART,10,10,0.01f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),-22,13,-90.0));
        playerShip.addBackRightThruster(new Thruster(Thruster.STANDART,10,10,0.01f,playerShip.getMiddleX(),playerShip.getMiddleY(),playerShip.getRealMiddleX(),playerShip.getRealMiddleY(),12,13,90.0));
        
        playerShip.setRotationThrust(5); //Setzen der maximalen Rotiergeschwindigkeit des Schiffes
        
        //Hinzufügen der Waffen zu dem Playerschiff (eventuell auch als Vorlage abspeichern)
        Weapon weapon = new Weapon(Weapon.LASER,10,30,-25,-10,(int)playerShip.getMiddleX(),(int)playerShip.getMiddleY(),0,200);
        weapon.setBullet(new Bullet(Bullet.LASER_RED,3,30,0,0,new Vector2D(0,-1),0,Bullet.NEUTRAL));
        playerShip.addWeapon(weapon);
        
        weapon = new Weapon(Weapon.LASER,10,30,15,-10,(int)playerShip.getMiddleX(),(int)playerShip.getMiddleY(),0,200);
        weapon.setBullet(new Bullet(Bullet.LASER_RED,3,30,0,0,new Vector2D(0,-1),0,Bullet.NEUTRAL));
        playerShip.addWeapon(weapon);
        
        //Starten des Hauptthreads
        this.run();
    }
    
    @Override
    public void run() {
        while (true) {  //Hauptschleife in der das ganze Spiel läuft
            if(resized) { //Überprüfung ob die Größe des Fenster vom Benutzer verändert wurde
                reCreateBuffer();   //Wenn ja wird das Buffer Image neu erstellt
                resized = false;    //und die variable resized wieder auf false gesetzt
            }
            
            if (focus) {    //Wenn der focus auf das aktuelle Canvas Objekt da ist werden die bewegungsmethoden aufgerufen (Überbleibsel aus früher Testphase - eventuell umändern oder ganz entfernen)
                if(Settings.FORWARD_Key_down) {
                    playerShip.throttleForward();
                }

                if (Settings.LEFT_Key_down) {
                    playerShip.throttleLeft();
                }

                if (Settings.BACKWARD_Key_down) {
                    playerShip.throttleBackward();
                }

                if (Settings.RIGHT_Key_down) {
                    playerShip.throttleRight();
                }
            } else { 
                Settings.FORWARD_Key_down = false;
                Settings.LEFT_Key_down = false;
                Settings.BACKWARD_Key_down = false;
                Settings.RIGHT_Key_down = false;
            }
            
            playerShip.updateShip(mausX,mausY,this.getWidth(),this.getHeight());    //Updaten des playerschiffs mit der aktuellen Mausposition
            
            try {   //Einsetzen eines limiters für die schleife, damit auf einer festgesetzten FPS anzahl geblieben werden kann
                Thread.sleep(fps_manager.getFPSlimiter_Millisecs());
            } catch (Exception e) {
                logger.error("Error bei Thread.sleep mit FPSlimiter: "+e);
            }
            
            fps_manager.fpsCount(); //Weiterzählen der fps
            reRender(); //Aufrufen der Renderfunktionen die das berechnete auf das Fenster zeichnen
        }
    }
    
    /**
     * Zeichenfunktion, die alles auf das Buffer Image zeichnet und die Zeichen methoden der Spielobjekte aufruft.
     * Ebenfalls werden hier die Partikel verwaltet und gegenbenfalls gelöscht und die Debuginformationen wie FPS, Koordinaten
     * und CollisionBoxen direkt ausgegeben.
     */
    public void render() {
        g.setColor(Color.WHITE); //Die Schriftfarbe des Canvas Objekts wird auf Weiß gestellt damit die angezeigten FPS und eventuelle Debugausgaben wie Collisionboxen besser gesehen werden
        
        //Das standart Graphics g Objekt wird in ein nützlicheres Graphics2D Objekt konvertiert
        Graphics2D g2d = (Graphics2D)g.create();
        
        //Die draw methode des Background objektes wird aufgerufen um die ausgerechneten Hintergründe zu zeichnen
        background.draw(g2d, this.getWidth(), this.getHeight());
        
        //Überprüfung ob Partikel in den Einstellungen aktiviert wurden
        if (Settings.particlesEnabled) { 
            for (int i = 0;i < particles.size();i++) { //Schleife die durch alle in der particles ObjectList gespeicherten Particel läuft
                Particle particle = (Particle)particles.getElementAt(i); //Das aktuell behandelte Element der particles ObjektList wird in einer eigene Referenz zwischengespeichert
                
                if (particle.draw(g2d)) { //Die draw methode des aktuellen Partikels wird aufgerufen
                    particles.remove(i); //Wenn die draw methode true zurückwird ist die Lebensdauer des Partikels vorbei und er wird aus der particles ObjectList entfernt
                }
            }
        }
        
        playerShip.draw(g2d); //Das aktuelle Spielerschiff wird gezechnet
        
        if (Settings.showDebug) {//Überprüfung ob Debug Ausgaben aktiviert sind
            playerShip.getBox().draw(g2d); //Wenn ja wird die CollisionBox des Spieler Schiffes auch ausgegeben
        }
        
        for (int i = 0;i < bullets.size();i++) { //Schleife die durch alle in der bullets ObjectList gespeicherten Schüsse läuft
            Bullet bullet = (Bullet)bullets.getElementAt(i); //Das aktuell behandelte Element wird in einer eigenen Referenz zwischengespeichert
            bullet.draw(g2d); //Das aktuell behandelte Element wird durch Aufruf der draw methode gezeichnet
            
            if (Settings.showDebug) {//Wenn Debug Ausgaben aktiviert sind
                bullet.getBox().draw(g2d); //wird die Collision Box der einzelnen Schüsse auch gezeichnet
            }
        }
        
        g.drawString("FPS: "+fps_manager.getFPS(), 30, 30); //Ausgabe der FPS am Bildschirm
        
        //Sonstige Debug Ausgaben
        if (Settings.showDebug) {
            g.drawString("Frame Limiter: "+fps_manager.getFPSlimiter_Millisecs()+"ms, game solution: "+this.getWidth()+"x"+this.getHeight(), 30, 42);
            g.drawString("Ship coord(Real): x="+(long)this.playerShip.getRealMiddleX()+" y="+(long)this.playerShip.getRealMiddleY()+",  coord(Render): x="+(long)this.playerShip.getMiddleX()+" y="+(long)this.playerShip.getMiddleY(), 30, 54);
            g.drawString("Background coord(Stars): x="+(long)Background.X+" y="+(long)Background.Y, 30, 66);
            g.drawString("Particles: "+particles.size(), 30, 78);
            g.drawString("Bullets: "+bullets.size(), 30, 90);
        }
    }
    
    /**
     * Ermittel ob sich die schiffsmittelkoordinaten seit der letzten Überprüfung verändert haben und bessert gegebenfalls die Background Koordinaten
     * entsprechend nach.
     * Überprüft auch ob das Buffer Image bereits erstellt wurde und erstellt dieses gegebenfalls neu. Anschließend werden die Zeichen methoden this.paint(g)
     * und render() aufgerufen und abschließend das Buffer Image als Image auf das CanvasObjekt gezeichnet
     */
    public void reRender() {
        if (dbImage == null) {//Überprüfung ob das Buffer Image schon erstellt wurde
            reCreateBuffer(); //Wenn nicht wird es durch Aufruf der reCreateBufferMethode erstellt
        }
        
        //Berechnen des aktuellen Abstandes zwischen Schiff und Fensterrand
        float distanceX_Old = this.playerShip.getMiddleX();
        float distanceY_Old = this.playerShip.getMiddleY();
        
        //Mittelkoordinaten des Schiffs werden auf die Hälfte der Fensterbreite bzw Fensterhöhe aktualisiert
        this.playerShip.setMiddleX(this.getWidth() / 2);
        this.playerShip.setMiddleY(this.getHeight() / 2);
        
        //Die Hintergrund X werden um den Abstand, um den das Schiff versetzt wurde (neuer Abstand - alter Abstand => veränderte Distanz) ebenfalls verschoben
        Background.positionChanged(this.playerShip.getMiddleX() - distanceX_Old, this.playerShip.getMiddleY() - distanceY_Old);
        
        this.paint(g); //Die vererbte paint methode wird mit dem erstellten Graphics g Objekt aufgerufen sodass vorerst nur im Buffer refresht wird
        render(); //Die Zeichenmethode wird aufgerufen die auf dem Graphics g Objekt, also im Buffer zeichnet
        
        this.getGraphics().drawImage(dbImage, 0, 0, this); //Das richtige Graphics g Objekt für das aktuelle Canvas Fenster wird ermittelt und das gepufferte auf dem gezeichnet wurde wird auf das reale mittels drawImage gezeichnet
    }
    
    /**
     * Erstellt das Buffer Image (dbImage) mit der aktuellen Weite und Höhe des Canvas Objektes neu und weißt dem Graphics g Objekt das Graphics Objekt
     * des erstellten Buffer Images (dbImage) zu
     */
    public void reCreateBuffer() { 
        dbImage = this.createImage(this.getSize().width,this.getSize().height); //Die größe des Buffer Images wird mit der aktuellen Größe des Canvas Objektes initialisiert
        g = dbImage.getGraphics(); //Das Buffer Graphics g Objekt zeigt auf das Graphics Objekt des Buffer Images
    }
}
