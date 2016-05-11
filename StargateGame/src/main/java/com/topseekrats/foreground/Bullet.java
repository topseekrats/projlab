package com.topseekrats.foreground;

/**
 * A játékosok fegyveréből kilőtt lövedéket reprezentáló osztály.
 */
public class Bullet extends Foreground {

    private BulletType type;

    /**
     * Lövedék konstruktor.
     *
     * @param type lövedék típusa
     */
    public Bullet(BulletType type) { this.type = type; }

    public BulletType getType() { return type; }

}
