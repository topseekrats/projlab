package com.topseekrats.background;

import com.topseekrats.MazeObject;
import com.topseekrats.foreground.Item;

public abstract class Background implements MazeObject {

    public abstract boolean isPassable();

    @Override
    public boolean isForeground() { return false; }

    @Override
    public void changeBullet() {}

    @Override
    public void dropBox() {}

    @Override
    public void pickUp() {}

    @Override
    public void move() {}

    @Override
    public void shoot() {}

}
