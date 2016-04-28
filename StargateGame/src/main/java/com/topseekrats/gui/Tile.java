package com.topseekrats.gui;

import java.awt.image.BufferedImage;

/**
 * Created by Mark-PC10 on 2016. 04. 24..
 */
public class Tile {
    private final String description;
    private final int tileX;
    private final int tileY;
    private final boolean walkable;
    private final BufferedImage image;



    public Tile(int tileX, int tileY, String description, boolean walkable, BufferedImage image) {
        this.tileX          = tileX;
        this.tileY          = tileY;
        this.description    = description;
        this.walkable       = walkable;
        this.image          = image;
    }


}
