/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sami
 */
public interface Cargo {
    public BufferedImage getIcon();
    public String getName();
    public String getTooltip();
    @Override
    public boolean equals(Object object);
}
