package com.topseekrats.foreground;

public class Bullet extends Foreground {

    private BulletType type;

    public Bullet(BulletType type) { this.type = type; }

    public BulletType getType() { return type; }

    @Override
    public void move() {
    }

}
