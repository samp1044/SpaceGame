/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import main.MainLoop;
import utils.Logger;
import utils.Vector2D;

/**
 *
 * @author Sami
 */
public class Background {
    public static double X;
    public static double Y;
    
    public static double BackgroundX;
    public static double BackgroundY;
    
    private BufferedImage[][] sectors;
    private BufferedImage stars;
    
    private int sectorWidth;
    private int sectorHeight;
    
    //Static weil es sonst in den static methoden nicht aufrufbar ist
    static Logger logger = new Logger(Background.class);
    
    public Background(Resources resources) {
        logger.debug("Initialisiere Background");
        
        this.sectorHeight = 1080;
        logger.debug("sectorHeight set to "+this.sectorHeight);
        this.sectorWidth = 1920;
        logger.debug("sectorWidth set to "+this.sectorWidth);
        
        this.stars = resources.getScaledBufferedImageField(Resources.BACKGROUND_STARS,1920,1080,Resources.SCALE_SMOOTH)[0];
        logger.debug("stars bild geladen: stars.png");
        
        this.sectors = new BufferedImage[1][1];
        logger.debug("sector array erstellt (1/1)");
        
        int cnt = 0;
        BufferedImage[] sectorImgs = resources.getScaledBufferedImageField(Resources.BACKGROUND_SECTORS, this.sectorWidth, this.sectorHeight,Resources.SCALE_SMOOTH);
        for (int i = 0;i < sectors.length;i++) {
            for (int j = 0;j < sectors[i].length;j++) {
                sectors[i][j] = sectorImgs[cnt];
                if (cnt < sectorImgs.length) {
                    cnt++;
                }
            }
        }
        
        logger.info("Background initialisiert");
    }
    
    public static void move(Vector2D direction) {
        if (direction != null) {
            direction = direction.multiplyWithNumber(-1);
            
            if (X + direction.getX() <= 0) {
                X = X + direction.getX();
            } else {
                X = 0;
                MainLoop.playerShip.stop();
            }
            
            if (Y + direction.getY() <= 0) {
                Y = Y + direction.getY();
            } else {
                Y = 0;
                MainLoop.playerShip.stop();
            }
            
            /*not implemented
             * direction = direction.divideByNumber(2);
            
            if (BackgroundX + direction.getX() <= 0) {
                BackgroundX = BackgroundX + direction.getX();
            } else {
                BackgroundX = 0;
            }
            
            if (BackgroundY + direction.getY() <= 0) {
                BackgroundY = BackgroundY + direction.getY();
            } else {
                BackgroundY = 0;
            }*/
        }
    }
    
    public static void positionChanged(float changeX, float changeY) {
        Background.X = Background.X + changeX;
        Background.Y = Background.Y + changeY;
        
        //ungetestet
        Background.BackgroundX = Background.BackgroundX + changeX;
        Background.BackgroundY = Background.BackgroundY + changeY;
    }
    
    public void draw(Graphics2D g2d,int canvasWidth,int canvasHeight) {
        drawStars(g2d, canvasWidth, canvasHeight);
    }
    
    private void drawStars(Graphics2D g2d,int canvasWidth,int canvasHeight) {
        int pictureWidth = this.sectorWidth;
        int pictureHeight = this.sectorHeight;
        
         //Rechnet aus wie oft die Bildbreite vollständig in den X offset vom Background reingeht (Bsp.: Bild = 200px; X = -845px => notNecessaryX = 4), X und Y sollten IMMER NEGATIV sein
        int notNecessaryX = (int)(X / pictureWidth);
        int notNecessaryY = (int)(Y / pictureHeight); //Gleiches bei Y
        
        if (notNecessaryX < 0) {  //Überprüft ob die anzahl der nicht benötigten bilder eine positive zahl ist
            notNecessaryX = notNecessaryX * -1; //Wenn sie negativ ist wird sie positiv gemacht
        }
        
        if (notNecessaryY < 0) { //Gleiches wie bei X
            notNecessaryY = notNecessaryY * -1;
        }
        
        notNecessaryX = pictureWidth * notNecessaryX; //rechnet die koordinaten aus auf denen keine bilder gezeichnet werden müssen (bildWeite in pixel * anzahl wie oft die bildweite in X reingeht)
        notNecessaryY = pictureHeight * notNecessaryY;//das gleiche für Y
        
        int drawX = (int)(X + notNecessaryX); //subtrahiert die nicht benötigten X koordinaten von der tatsächlichen X position (X sollte immer negativ sein)
        int drawY = (int)(Y + notNecessaryY); //das gleiche bei Y
        
        int howOftenX = ((canvasWidth + (drawX * -1)) / pictureWidth) + 1; //rechnet aus wie oft das bild in der weite gezeichnet werden soll, also auf der X achse. 
                                                           //Rechnet canvas weite (Breite vom zeichenobjekt) durch die Breite des Bildes und das ganze noch +1 zur sicherheit
        int howOftenY = ((canvasHeight + (drawY * -1)) / pictureHeight) + 1;//das gleiche bei Y also in die bilder in der tiefe.
        //Canvas weite (bzw Höhe) + drawX (bzw drawY) * -1  um auch das bild das links vom sichtfenster gezeichnet wird zu berücksichtigen, drawX (bzw drawY) * -1 um die zurechnung positiv werden zu lassen
        
        int realDrawX = drawX; //kopieren des ausgerechneten wertes in eine rechenvariable, weil der ursprungswert noch benötigt wird
        int realDrawY = drawY; //das gleiche bei Y obwohl der wert hier nicht benötigt wird
        
        for (int i = 0;i < howOftenY;i++) { //schleife die die bilder auf der Y achse zeichnet also die zeilen erhöht
            for (int j = 0;j < howOftenX;j++) { //Schleife, die die bilder auf der X achse zeichnet, also eine zeile hintergrundbilder fabriziert
                g2d.drawImage(this.stars, realDrawX, realDrawY, null); //tatsächliches zeichnen des Stars Bildes mit den realDrawX und realDrawY koordinaten
                realDrawX = realDrawX + pictureWidth; //x position um die bild weite vergrößern für das nächste bild
            }
            realDrawX = drawX; //x position auf den anfang resetten um eine neue zeile von hintergrundbildern zu starten
            realDrawY = realDrawY + pictureHeight; //y um die bild höhe vergrößern für die neue zeile
        }
    }
    
}
