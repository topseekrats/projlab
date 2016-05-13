package com.topseekrats.ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tiles {

    public BufferedImage box = null;
    public BufferedImage cleft = null;
    public BufferedImage colonel = null;
    public BufferedImage doorClosed = null;
    public BufferedImage doorOpened = null;
    public BufferedImage floor = null;
    public BufferedImage jaffa = null;
    public BufferedImage replicator = null;
    public BufferedImage stargateColonel = null;
    public BufferedImage stargateJaffa = null;
    public BufferedImage switchButton = null;
    public BufferedImage wall = null;
    public BufferedImage zpm = null;

    public Tiles() {
        try {
            File textureFolder = new File("textures");
            for (final File f : textureFolder.listFiles() ) {
                if (f.getName().contains("box")) box = ImageIO.read(f);
                else if (f.getName().contains("cleft")) cleft = ImageIO.read(f);
                else if (f.getName().contains("colonel")) colonel = ImageIO.read(f);
                else if (f.getName().contains("door_closed")) doorClosed = ImageIO.read(f);
                else if (f.getName().contains("door_opened")) doorOpened = ImageIO.read(f);
                else if (f.getName().contains("floor")) floor = ImageIO.read(f);
                else if (f.getName().contains("jaffa")) jaffa = ImageIO.read(f);
                else if (f.getName().contains("replicator")) replicator= ImageIO.read(f);
                else if (f.getName().contains("stargate_col")) stargateColonel = ImageIO.read(f);
                else if (f.getName().contains("stargate_jaf")) stargateJaffa = ImageIO.read(f);
                else if (f.getName().contains("switch")) switchButton = ImageIO.read(f);
                else if (f.getName().contains("wall")) wall = ImageIO.read(f);
                else if (f.getName().contains("zpm")) zpm = ImageIO.read(f);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

}
