package com.topseekrats.foreground;

import com.topseekrats.MazeObject;

public abstract class Foreground implements MazeObject {
    @Override
    public boolean isForeground() {
        System.out.println("Foreground.isForeground() called");
        System.out.println("Foreground.isForeground() returned");
        return true;
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
