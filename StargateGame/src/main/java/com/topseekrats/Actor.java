package com.topseekrats;

import com.topseekrats.background.Cleft;
import com.topseekrats.background.Door;
import com.topseekrats.background.Switch;
import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Actor implements MazeObject {
    @Override
    public void move() {
        System.out.println("Actor.move() called");
        new MazeObjectWrapper().getBackground();
        try {
            System.out.println("Is the actual field is a wall? [y/n]:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String respond = br.readLine();
            if (respond.equals("n")) {
                new MazeObjectWrapper().getActor();
                new MazeObjectWrapper().setActor();
                System.out.println("Is the actual field a switch? [y/n]:");
                respond = br.readLine();
                if (respond.equals("y")) {
                    new Switch().changeDoorState();
                    new Door().changeOpened();
                } else {
                    System.out.println("Is the actual field a cleft? [y/n]:");
                    respond = br.readLine();
                    if (respond.equals("y")) {
                        new Cleft().destroy();
                        new Engine().death();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Actor.move() returned");
    }

    @Override
    public void shoot() {
        System.out.println("Actor.shoot() called");
        new Bullet().move();
        System.out.println("Actor.shoot() returned");
    }

    @Override
    public void changeBullet() {
        System.out.println("Actor.changeBullet() called");
        new Bullet().getType();
        System.out.println("Actor.changeBullet() returned");
    }

    @Override
    public void dropBox() {
        System.out.println("Actor.dropBox() called");
        new MazeObjectWrapper().setForeground();
        new MazeObjectWrapper().getBackground();
        System.out.println("Is the actual field a switch? [y/n]:");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String respond = br.readLine();
            if (respond.equals("y")) {
                new Switch().changeDoorState();
                new Door().changeOpened();
            } else {
                System.out.println("Is the actual field a cleft? [y/n]:");
                respond = br.readLine();
                if (respond.equals("y")) new Cleft().destroy();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Actor.dropBox() returned");
    }

    @Override
    public void pickUp() {
        System.out.println("Actor.pickUp() called");
        new Item().getType();
        System.out.println("Set item type [b/z]:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String respond = br.readLine();
            if (respond.equals("b")) {
                new MazeObjectWrapper().getForeground();
                new MazeObjectWrapper().getBackground();
                System.out.println("Is the actual field a switch? [y/n]:");
                respond = br.readLine();
                if (respond.equals("y")) {
                    new Switch().changeDoorState();
                    new Door().changeOpened();
                }
            } else if (respond.equals("z")) {
                new MazeObjectWrapper().getForeground();
                System.out.println("Is this the last ZPM? [y/n]:");
                respond = br.readLine();
                if (respond.equals("y")) new Engine().victory();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Actor.pickUp() returned");
    }

    @Override
    public boolean isForeground() {
        System.out.println("Actor.isForeground() called");
        System.out.println("Actor.isForeground() returned");
        return false;
    }
}
