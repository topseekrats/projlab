package com.topseekrats;

import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.BulletType;
import com.topseekrats.foreground.Item;

public class Actor implements MazeObject {

    private ActorType type;
    private Bullet bullet;
    private Item item;
    private int zpmCount;

    public Actor(ActorType type) {
        this.type = type;
        if (type == ActorType.COLONEL) bullet = new Bullet(BulletType.YELLOW);
        else bullet = new Bullet(BulletType.RED);
    }

    public ActorType getType() { return type; }
    public int getZpmCount() { return  zpmCount; }

    @Override
    public void move() {
    }

    @Override
    public void shoot() {
    }

    @Override
    public void changeBullet() {
        if (type == ActorType.COLONEL) {
            if (bullet.getType() == BulletType.YELLOW) bullet = new Bullet(BulletType.BLUE);
            else bullet = new Bullet(BulletType.YELLOW);
        } else {
            if (bullet.getType() == BulletType.RED) bullet = new Bullet(BulletType.GREEN);
            else bullet = new Bullet(BulletType.RED);
        }
    }

    @Override
    public void dropBox() {
    }

    @Override
    public void pickUp(Item item) {

    }

    @Override
    public boolean isForeground() { return false; }
}
