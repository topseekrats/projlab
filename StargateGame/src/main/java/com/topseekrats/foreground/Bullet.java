package com.topseekrats.foreground;

import com.topseekrats.MazeObjectWrapper;
import com.topseekrats.background.Wall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bullet extends Foreground {
    public String getType() {
        System.out.println("Bullet.getType() called");
        System.out.println("Bullet.getType() returned");
        return null;
    }

    @Override
    public void move() {
        System.out.println("Bullet.move() called");
        new MazeObjectWrapper().getBackground();
        System.out.println("Is the actual field a wall? [y/n]:");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (br.readLine().equals("n")) {
                new MazeObjectWrapper().getBackground();
                System.out.println("Is the actual field a wall? [y/n]:");
            }
            new Wall().isPortalCompatible();
            System.out.println("Is the wall portal-compatible? [y/n]:");
            String respond = br.readLine();
            if (respond.equals("n")) {
                System.out.println("Bullet.move() returned");
                return;
            }
            System.out.println("Is there a Stargate on the map already? [y/n]:");
            respond = br.readLine();
            if (respond.equals("y")) {
                System.out.println("Is there a portal on this wall already?");
                respond = br.readLine();
                if (respond.equals("y")) new Stargate().dispose();
                else {
                    new Stargate().dispose();
                    System.out.println("Stargate constructor called");
                    new Stargate();
                    System.out.println("Stargate constructor returned");
                }
            } else {
                new Wall().changeHasStargete();
                System.out.println("Is this the second portal? [y/n]:");
                respond = br.readLine();
                if (respond.equals("y")) {
                    System.out.println("Stargate constructor called");
                    new Stargate();
                    System.out.println("Stargate constructor returned");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Bullet.move() returned");
    }
}
