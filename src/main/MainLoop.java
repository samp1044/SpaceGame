/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import support.Background;
import window.Fenster;
import units.PlayerShip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import support.FPSManager;
import support.Resources;
import support.Settings;
import support.ShipBuilder;
import ui.InGameHUD;
import ui.InventoryHUD;
import ui.TooltipHUD;
import ui.TransferHUD;
import units.AiShip;
import units.Bullet;
import units.Explosion;
import units.ItemDrop;
import units.Particle;
import units.Ship;
import utils.Date;
import utils.Logger;
import utils.ObjectList;
import utils.Time;
import values.StringSelector;

/**
 * Hauptschleife des Spiels und gleichzeitig grafische Hauptausgabe als
 * Canvas Objekt. 
 * @author Sami
 */
public class MainLoop extends Canvas implements Runnable {
    Fenster fenster;
    
    public static int width;
    public static int height;
    
    BufferStrategy bufferStrategy;
    
    //FpsManager Objekt der die Framerate verwaltet und die Thread Unterbrechungszeiten berechnet um die Framerate konstant zu halten
    FPSManager fps_manager;
    
    //Background Objekt das die Darstellung und die Berechnung der Hintergrundposition durchführt
    Background background;
    //Ressourcen Objekt in dem alle externen Ressourcen gespeichert werden. Statisch damit vom ganzen Programm aus darauf zugegriffen werden kann
    public static Resources resources;
    
    InGameHUD inGameHUD;
    InventoryHUD inventoryHUD;
    public static TransferHUD transferHUD;
    
    public static TooltipHUD tooltipHUD;
    public static ObjectList tooltips;
    
    //Das aktuelle Spielerschiff
    public static PlayerShip playerShip;
    
    public static boolean focus; //Boolean variable die anzeigt ob das Spielfenster sich gerade im Fokus befindet
    public static boolean resized; //Bollean variable die angibt ob das Spielfenster in der Größe verändert wurde
    
    public static ObjectList particles; //Liste aller Partikel die vom Spiel verwaltet werden sollen
    public static ObjectList bullets; //Liste aller Schuss Objekte die vom Spiel verwaltet werden sollen
    
    public static ObjectList aiShips;
    public static ObjectList explosions;
    
    public static ObjectList drops;
    
    public static int mausX; //Aktuelle Mausposition (relativ zu den Fenster koordinaten also ausgehend von 0,0 des Fensters)
    public static int mausY;
    
    public static boolean ui_open;
    public static boolean ui_click_happened;
    
    Logger logger = new Logger(MainLoop.class); //(Eigenes) Logger objekt für Debug ausgaben, statisch damit darauf auch in statischen Methoden zugegriffen werden kann
    
    /**
     * Default Konstruktor in dem alle Variablen die für das Spiel benötigt werden initialisiert werden
     */
    public MainLoop() {
        //Initialisieren des Canvas Objekts also der "Zeichenfläche"
        this.setIgnoreRepaint(true);    //Einstellen das Refresh Aufforderungen vom Betriebssystem ignoriert werden
        //this.setSize(1280,800); //Setzen der Default Größe der Zeichenfläche
        this.setSize(Settings.solutionWidth,Settings.solutionHeight);
        this.setBackground(Color.BLACK); //Setzen der Default Hintergrund farbe der Zeichenfläche
        
        this.fenster = new Fenster(this); //Ein neues Fenster wird erstellt an das die Zeichenfläche übergeben wird (this)
        
        this.createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();
        
        resources = new Resources(); //Das Ressourcen Objekt wird initialisiert
        
        fps_manager = new FPSManager(); //Der FPS Manager wird initialisiert
        
        focus = true; //Standartmäßig hat das Spiel den Fokus wenn es gestartet wird, also wird die Fokus variable mit true initialisiert
        resized = false; //Standartmäßig ist das Fenster auf einer festen Größe beim Starten also wird die Resized Variable mit false initialisiert
        
        particles = new ObjectList(); //Die Partikel Liste wird mit einer leeren ObjectList initialisiert
        bullets = new ObjectList(); //Die Schuss Liste wird mit einer leeren ObjectList initialisiert
        
        aiShips = new ObjectList();
        explosions = new ObjectList();
        
        drops = new ObjectList();
        
        tooltips = new ObjectList();
        
        //Die maus koordinaten werden mit 0 initialisiert
        mausX = 0;  
        mausY = 0;
        
        ui_open = false;
        
        StringSelector.init();
        loadResources(); //Laden der Ressourcen nachdem alle Variablen initialisiert wurden.
    }
    
    /**
     * Lädt alle externen Ressourcen für das Spiel im Resources Objekt und wartet in einem eigenen Thread bis alle Ressourcen geladen
     * wurden.
     */
    private void loadResources() {
        new Thread() {
            boolean running = true;
            
            @Override
            public void run() {
                try {
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
                } catch (Exception e) {
                    logger.error("Error beim Ausführen der Ressourcen lade methode: "+e);
                }
            }
        }.start();
    }
    
    /**
     * Initialisiert die Spielobjekte wie den Hintergrund und das Spielerschiff
     */
    private void initObjects() {
        background = new Background(resources); //Hintergrund wird initialisert und vorbereitet
        Background.X = -500000;
        Background.Y = -500000;
        
        inGameHUD = new InGameHUD(this.getWidth(),this.getHeight());
        
        ShipBuilder.initBaseShips();
        playerShip = (PlayerShip) ShipBuilder.DUMMYSHIP_CORVETTE; //Playerschiff wird initialisiert mit dem entsprechenden Base Objekt der ShipBuilder Klasse
        
        aiShips.add(ShipBuilder.getRandomAiShip(500000, 500000, Ship.CORVETTE, Ship.ENEMY, ShipBuilder.WEAK));
        
        //aiShips.add(ShipBuilder.AI_DUMMYSHIP_BASE);
        //aiShips.add(ShipBuilder.AI_DUMMYSHIP_BASE2);
        //aiShips.add(ShipBuilder.AI_DUMMYSHIP_CORVETTE);
        
        inGameHUD.setShieldIcon(playerShip.getShieldRingIcon());
        
        //Starten des Hauptthreads
        this.run();
    }
    
    @Override
    public void run() {
        try {
            while (true) {  //Hauptschleife in der das ganze Spiel läuft
                fps_manager.beginnTime();
                
                if(resized) { //Überprüfung ob die Größe des Fenster vom Benutzer verändert wurde
                    reCreateBuffer();   //Wenn ja wird das Buffer Image neu erstellt
                    
                    width = this.getWidth();
                    height = this.getHeight();
                    
                    resized = false;    //und die variable resized wieder auf false gesetzt
                }
                
                if (ui_click_happened && !Settings.MOUSE_LEFT_DOWN) {
                    ui_click_happened = false;
                }
                
                if (Settings.INVENTORY_KEY_DOWN) {
                    if (!InventoryHUD.open) {
                        if (!TransferHUD.open) {
                            InventoryHUD.open = true;
                            inventoryHUD = new InventoryHUD(this.getWidth(),this.getHeight());
                        }
                    } else {
                        InventoryHUD.open = false;
                        inventoryHUD.destroy();
                        inventoryHUD = null;
                    }
                    
                    Settings.INVENTORY_KEY_DOWN = false;
                }
                
                if (!TransferHUD.open) {
                    if (transferHUD != null) {
                        transferHUD = null;
                    }
                }
                
                if (InventoryHUD.open || TransferHUD.open) {
                    ui_open = true;
                } else {
                    ui_open = false;
                }
                
                if (!ui_open) {
                    inGameHUD.update(this.getWidth(), this.getHeight(), playerShip.getHealth(), playerShip.getEnergy());
                    
                    for (int i = 0;i < aiShips.size();i++) {
                        AiShip aiShip = (AiShip)aiShips.getElementAt(i);

                        if (aiShip.update()) {
                            aiShips.remove(i);
                        }
                    }
                    
                    for (int i = 0;i < drops.size();i++) {
                        ItemDrop drop = (ItemDrop)drops.getElementAt(i);
                        
                        if (drop.update()) {
                            drops.remove(i);
                        }
                    }
                    
                    for (int i = 0;i < bullets.size();i++) { //Schleife die durch alle in der bullets ObjectList gespeicherten Schüsse läuft
                        Bullet bullet = (Bullet)bullets.getElementAt(i); //Das aktuell behandelte Element wird in einer eigenen Referenz zwischengespeichert
                        
                        if (bullet.update()) {
                            bullets.remove(i);
                        }
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
                } else {
                    if (inventoryHUD != null && InventoryHUD.open) {
                        inventoryHUD.update();
                    }
                    
                    if (transferHUD != null && TransferHUD.open) {
                        transferHUD.update();
                    }
                }
                
                for (int i = 0;i < tooltips.size();i++) {
                    TooltipHUD tooltip = (TooltipHUD)tooltips.getElementAt(i);
                    
                    if (!tooltip.update()) {
                        tooltips.remove(i);
                    }
                }
                
                if (tooltipHUD != null && tooltipHUD.active) {
                    if (!tooltipHUD.update()) {
                        tooltipHUD = null;
                    }
                }
                
                render(); //Aufrufen der Renderfunktionen die das Berechnete auf das Fenster zeichnet
                fps_manager.manageFrameCap();
                
                if (fps_manager.getFPSlimiter_Millisecs() > 0) {
                    try {
                        Thread.sleep(fps_manager.getFPSlimiter_Millisecs(),(int)fps_manager.getFPSlimiter_NanoSecs());
                    } catch (InterruptedException e) {

                    }
                }
                
                if(Settings.takeScreenshot) {
                    takeScreenshot();
                    Settings.takeScreenshot = false;
                }
                
                fps_manager.fpsCount();
                fps_manager.setDelta();
            }
        } catch (Exception e) {
            logger.error("Kritische Exception in Hauptschleife: " + e);
            e.printStackTrace();
            
        } catch (Error er) {
            logger.error("Kritischer Error in Hauptschleife: " + er);
            er.printStackTrace();
            
        } finally {
            logger.info("Versuche Schleife neuzustarten...");
            this.run();
        }
    }
    
    /**
     * Zeichenfunktion, die alles auf das Buffer Image zeichnet und die Zeichen methoden der Spielobjekte aufruft.
     * Ebenfalls werden hier die Partikel verwaltet und gegenbenfalls gelöscht und die Debuginformationen wie FPS, Koordinaten
     * und CollisionBoxen direkt ausgegeben.
     */
    public void render() {
        Graphics2D g2d = (Graphics2D)bufferStrategy.getDrawGraphics();
        
        clearRendering(g2d);
        draw(g2d);
        finishRendering(g2d);
    }
    
    public void clearRendering(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.WHITE);
    }
    
    public void draw(Graphics2D g2d) {
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
            //playerShip.getBox().draw(g2d); //Wenn ja wird die CollisionBox des Spieler Schiffes auch ausgegeben
        }
        
        for (int i = 0;i < aiShips.size();i++) {
            AiShip aiShip = (AiShip)aiShips.getElementAt(i);
            aiShip.draw(g2d);
            
            if (Settings.showDebug) {
                //aiShip.getShield().getCollisionBox().draw(g2d);
            }
        }
        
        for (int i = 0;i < explosions.size();i++) {
            Explosion explosion = (Explosion)explosions.getElementAt(i);
            
            if (explosion.draw(g2d)) {
                explosions.remove(i);
            }
        }
        
        for (int i = 0;i < drops.size();i++) {
            ItemDrop drop = (ItemDrop)drops.getElementAt(i);
            drop.draw(g2d);
        }
        
        for (int i = 0;i < bullets.size();i++) { //Schleife die durch alle in der bullets ObjectList gespeicherten Schüsse läuft
            Bullet bullet = (Bullet)bullets.getElementAt(i); //Das aktuell behandelte Element wird in einer eigenen Referenz zwischengespeichert
            bullet.draw(g2d);
            
            
            if (Settings.showDebug) {//Wenn Debug Ausgaben aktiviert sind
                //bullet.getBox().draw(g2d); //wird die Collision Box der einzelnen Schüsse auch gezeichnet
            }
        }
        
        inGameHUD.draw(g2d);
        
        if (InventoryHUD.open) {
            if (inventoryHUD != null) {
                inventoryHUD.draw(g2d);
            }
        }
        
        if (TransferHUD.open) {
            if (transferHUD != null) {
                transferHUD.draw(g2d);
            }
        }
        
        for (int i = 0;i < tooltips.size();i++) {
            TooltipHUD tooltip = (TooltipHUD) tooltips.getElementAt(i);
            tooltip.draw(g2d);
        }
        
        if (tooltipHUD != null && tooltipHUD.active) {
            tooltipHUD.draw(g2d);
        }
        
        //Sonstige Debug Ausgaben
        if (Settings.showDebug) {
            g2d.drawString("FPS: "+fps_manager.getFPS(), 30, 30); //Ausgabe der FPS am Bildschirm
            g2d.drawString("Frame Limiter: "+fps_manager.getFPSlimiter_Millisecs()+"ms "+fps_manager.getFPSlimiter_NanoSecs()+"ns Delta: "+FPSManager.DELTA+", game solution: "+this.getWidth()+"x"+this.getHeight(), 30, 42);
            g2d.drawString("Ship coord(Real, not working): x="+(long)playerShip.getMiddleX()+" y="+(long)playerShip.getMiddleY()+",  coord(Render): x="+(long)this.playerShip.getDrawMiddleX()+" y="+(long)this.playerShip.getDrawMiddleY()+" SPEED: "+(int)playerShip.getSpeed()+" m/s", 30, 54);
            g2d.drawString("Background coord(Stars): x="+(long)Background.X+" y="+(long)Background.Y, 30, 66);
            g2d.drawString("Particles: "+particles.size() + " Bullets: "+bullets.size()+" AIShips: "+aiShips.size()+" Explosions: "+explosions.size() + " ItemDrops: "+drops.size()+" Tooltips: "+tooltips.size(), 30, 78);
            g2d.drawString("MausCoord: X:"+mausX+" Y:"+mausY, 30, 90);
        }
    }
    
    public void finishRendering(Graphics2D g2d) {
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    
    /**
     * Erstellt das Buffer Image (dbImage) mit der aktuellen Weite und Höhe des Canvas Objektes neu und weißt dem Graphics g Objekt das Graphics Objekt
     * des erstellten Buffer Images (dbImage) zu
     */
    public void reCreateBuffer() { 
        
    }
    
    public void takeScreenshot() {
        final String fileNameConstant = "screenshot";
        final String fileTypeExtension = "png";

        Date date = new Date();
        Time time = new Time();

        String timeStamp = "" + date.getDateString() + "" + time.getTimeString();

        String filename = fileNameConstant + timeStamp + "." + fileTypeExtension;
        
        try {
            File outputfile = new File(filename);
            Image image = this.createImage(this.getSize().width, this.getSize().height);
            Graphics2D g2d = (Graphics2D)image.getGraphics().create();
            
            clearRendering(g2d);
            draw(g2d);
            
            ImageIO.write((BufferedImage)image,  fileTypeExtension, outputfile);
            
            logger.info("Screenshot taken: ["+filename+"]");
            g2d.dispose();
        } catch (IOException ex) {
            logger.error("Failed to save screenshot ["+filename+"]");
        }
    }
}
