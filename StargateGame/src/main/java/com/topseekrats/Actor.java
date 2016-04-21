package com.topseekrats;

import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.BulletType;
import com.topseekrats.foreground.Item;

public class Actor implements MazeObject {

    private ActorType type;
    private Bullet bullet;
    private Item item;
    private int zpmCount;

    public Actor(ActorType type) throws IllegalArgumentException {
        this.type = type;
        if (type == ActorType.COLONEL) bullet = new Bullet(BulletType.YELLOW);
        else if (type == ActorType.JAFFA) bullet = new Bullet(BulletType.RED);
        else throw new IllegalArgumentException("Actor object can not be created with REPLICATOR ActorType.");
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
