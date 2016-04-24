package com.topseekrats.foreground;

import com.topseekrats.MazeObject;

import java.io.Serializable;

public abstract class Foreground implements MazeObject, Serializable {

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
