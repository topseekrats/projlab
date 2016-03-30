package com.topseekrats;

public class Actor implements MazeObject {
    @Override
    public void move() {
        System.out.println("Actor.move() called");

        System.out.println("Actor.move() returned");
    }

    @Override
    public void shoot() {
        System.out.println("Actor.shoot() called");

        System.out.println("Actor.shoot() returned");
    }

    @Override
    public void changeBullet() {
        System.out.println("Actor.changeBullet() called");

        System.out.println("Actor.changeBullet() returned");
    }

    @Override
    public void dropBox() {
        System.out.println("Actor.dropBox() called");

        System.out.println("Actor.dropBox() returned");
    }

    @Override
    public void pickUp() {
        System.out.println("Actor.pickUp() called");

        System.out.println("Actor.pickUp() returned");
    }

    @Override
    public void dispose() {
        System.out.println("Actor.dispose() called");

        System.out.println("Actor.dispose() returned");
    }

    @Override
    public boolean isForeground() {
        System.out.println("Actor.isForeground() called");
        System.out.println("Actor.isForeground() returned");
        return false;
    }

    @Override
    public void setWeight() {
        System.out.println("Actor.setWeight() called");
        System.out.println("Actor.setWeight() returned");
    }

    @Override
    public int getWeight() {
        System.out.println("Actor.getWeight() called");
        System.out.println("Actor.getWeight() returned");
        return 0;
    }

    public String getType() {
        System.out.println("Actor.getType() called");

        System.out.println("Actor.getType() returned");
        return null;
    }
}
