package com.topseekrats.background;

import com.topseekrats.MazeObject;

public abstract class Background implements MazeObject {
    public abstract boolean isPassable();

    @Override
    public boolean isForeground() {
        System.out.println("Background.isForeground() called");
        System.out.println("Background.isForeground() returned");
        return false;
    }

    @Override
    public void setWeight() {}

    @Override
    public int getWeight() { return 0; }

    @Override
    public void move() {}

    @Override
    public void shoot() {}

    @Override
    public void changeBullet() {}

    @Override
    public void dropBox() {}

    @Override
    public void pickUp() {}

    @Override
    public void dispose() {}
}
