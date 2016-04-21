package com.topseekrats;

import com.topseekrats.foreground.Item;

public class Replicator implements MazeObject {

    public Replicator() {}

    @Override
    public void move() {
    }

    @Override
    public boolean isForeground() { return false; }

    @Override
    public void changeBullet() {}

    @Override
    public void dropBox() {}

    @Override
    public void pickUp(final Item item) {}

    @Override
    public void shoot() {}

}
