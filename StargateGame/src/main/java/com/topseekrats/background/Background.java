package com.topseekrats.background;

import com.topseekrats.MazeObject;

import java.io.Serializable;

/**
 * A játékmező landscape objektumainak absztrakt ősosztálya
 */
public abstract class Background implements MazeObject, Serializable {

    private static final long serialVersionUID = 1L;

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
