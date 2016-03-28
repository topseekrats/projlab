package com.topseekrats.foreground;

public class Bullet extends Foreground {

    public Bullet() {
        System.out.println("Bullet.newBullet() called");

        System.out.println("Bullet.newBullet() returned");
    }

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
