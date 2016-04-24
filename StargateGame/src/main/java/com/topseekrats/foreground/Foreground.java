package com.topseekrats.foreground;

import com.topseekrats.MazeObject;

public abstract class Foreground implements MazeObject {

    @Override
    public boolean isForeground() { return true; }

    @Override
    public void changeBullet() {}

    @Override
    public void dropBox() {}

    @Override
    public void move() {}

    @Override
    public void pickUp() {}

    @Override
    public void shoot() {}

}
