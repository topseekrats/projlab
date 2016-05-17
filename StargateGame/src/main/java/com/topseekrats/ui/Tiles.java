package com.topseekrats.ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A textúraképek betöltéséért és tárolásáért felelős osztály.
 */
public class Tiles {

    public BufferedImage box = null;
    public BufferedImage colonel = null;
    public BufferedImage doorClosed = null;
    public BufferedImage doorOpened = null;
    public BufferedImage jaffa = null;
    public BufferedImage portalBlue = null;
    public BufferedImage portalGreen = null;
    public BufferedImage portalRed = null;
    public BufferedImage portalYellow = null;
    public BufferedImage replicator = null;
    public BufferedImage stargateColonel = null;
    public BufferedImage stargateJaffa = null;
    public BufferedImage switchButton = null;
    public BufferedImage wallSimple = null;
    public BufferedImage wallSpecial = null;
    public BufferedImage zpm = null;
    public BufferedImage win = null;
    public BufferedImage lose = null;
    public BufferedImage draw = null;

    /**
     * Tiles konstruktor.
     */
    public Tiles() {
        try {
            File textureFolder = new File("textures");
            for (final File f : textureFolder.listFiles() ) {
                if (f.getName().contains("box")) box = ImageIO.read(f);
                else if (f.getName().contains("colonel")) colonel = ImageIO.read(f);
                else if (f.getName().contains("door_closed")) doorClosed = ImageIO.read(f);
                else if (f.getName().contains("door_opened")) doorOpened = ImageIO.read(f);
                else if (f.getName().contains("jaffa")) jaffa = ImageIO.read(f);
                else if (f.getName().contains("portal_blue")) portalBlue = ImageIO.read(f);
                else if (f.getName().contains("portal_green")) portalGreen = ImageIO.read(f);
                else if (f.getName().contains("portal_red")) portalRed = ImageIO.read(f);
                else if (f.getName().contains("portal_yellow")) portalYellow = ImageIO.read(f);
                else if (f.getName().contains("replicator")) replicator= ImageIO.read(f);
                else if (f.getName().contains("stargate_col")) stargateColonel = ImageIO.read(f);
                else if (f.getName().contains("stargate_jaf")) stargateJaffa = ImageIO.read(f);
                else if (f.getName().contains("switch")) switchButton = ImageIO.read(f);
                else if (f.getName().contains("wall_simple")) wallSimple = ImageIO.read(f);
                else if (f.getName().contains("wall_special")) wallSpecial = ImageIO.read(f);
                else if (f.getName().contains("zpm")) zpm = ImageIO.read(f);
                else if (f.getName().contains("win")) win = ImageIO.read(f);
                else if (f.getName().contains("lose")) lose = ImageIO.read(f);
                else if (f.getName().contains("draw")) draw = ImageIO.read(f);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

}
