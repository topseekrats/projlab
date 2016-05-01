package com.topseekrats.gui;

import com.topseekrats.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mark-PC10 on 2016. 04. 24..
 * Modified on 2016. 05. 02..
 */
public class Tile {
    private String fn;
    private BufferedImage image;

    public Tile(String fn) {
        this.fn = fn;
        readImage();
    }

    private void readImage() {
        BufferedImage img = null;
        String userDir = System.getProperty("user.dir")+File.separator+"StargateGame"+File.separator+"textura";
        try {
            File file = new File(userDir + File.separator + fn);
            img = ImageIO.read(file);
        } catch (IOException e) {
            Log.log("IOexc");
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.log("NULLexv");
            e.printStackTrace();
        }
        image = img;
    }
    public BufferedImage getImage() {
        return image;
    }

}
