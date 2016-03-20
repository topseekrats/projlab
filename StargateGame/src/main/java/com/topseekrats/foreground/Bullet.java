package com.topseekrats.foreground;

public class Bullet extends Foreground {
    public String getType() {
        System.out.println("Bullet.getType() called");

        System.out.println("Bullet.getType() returned");
        return null;
    }

    @Override
    public void move() {
        System.out.println("Bullet.move() called");

        System.out.println("Bullet.move() returned");
    }
}
