package com.topseekrats.foreground;

/**
 * Portál fegyverből kilőtt portálgolyó osztálya
 * Actor shoot() függvénye vezérli
 */
public class Bullet extends Foreground {

    private BulletType type;

    public Bullet(BulletType type) { this.type = type; }

    public BulletType getType() { return type; }

    @Override
    public void move() {
    }

}
