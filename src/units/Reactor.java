/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import Interfaces.Cargo;
import java.awt.Image;
import java.awt.image.BufferedImage;
import main.MainLoop;
import support.FPSManager;
import support.Resources;
import static units.Thruster.STANDART;
import utils.Utils;
import values.StringSelector;

/**
 *
 * @author Sami
 */
public class Reactor implements Cargo {
    private double energyOutput;
    private long reactorOutputDelay;
    private long lastReactorDrain;
    
    private BufferedImage icon;
    private String name;
    
    public Reactor(double energyOutput, long reactorOuputDelay) {
        this.energyOutput = energyOutput;
        this.reactorOutputDelay = reactorOuputDelay;
        this.lastReactorDrain = 0;
        
        icon = MainLoop.resources.getScaledBufferedImageField(Resources.ICON_REACTOR, 50, 50,Resources.SCALE_SMOOTH)[0];
        name = StringSelector.getSring(StringSelector.REACTOR_NAME);
    }
    
    public Reactor(Reactor reactor) {
        this.energyOutput = reactor.energyOutput;
        this.reactorOutputDelay = reactor.reactorOutputDelay;
        this.lastReactorDrain = 0;
    }
    
    public void reactorDrain() {
        this.lastReactorDrain = System.currentTimeMillis();
    }
    
    public double getEnergyOuput() {
        if (System.currentTimeMillis() - this.lastReactorDrain > this.reactorOutputDelay) {
            return this.energyOutput * FPSManager.DELTA;
        } else {
            return 0;
        }
    }
    
    public double getEnergyOutputValue() {
        return this.energyOutput;
    }
    
    public long getReactorOutputDelay() {
        return this.reactorOutputDelay;
    }

    @Override
    public BufferedImage getIcon() {
        return this.icon;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getTooltip() {
        int leftPartLength = 20;
        String returnValue = "";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.REACTOR_OUTPUT), leftPartLength);
        returnValue += (this.energyOutput) + " " + StringSelector.getSring(StringSelector.INVENTORYUI_SHIPREACTORUNIT) + "\n";
        
        returnValue += Utils.trimStringToGivenLength(StringSelector.getSring(StringSelector.REACTOR_DELAY), leftPartLength);
        returnValue += (this.reactorOutputDelay) + " " + StringSelector.getSring(StringSelector.RELOAD_TIME_UNIT) + "\n";
        
        return returnValue;
    }
}
