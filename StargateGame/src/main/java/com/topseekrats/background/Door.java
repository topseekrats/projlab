package com.topseekrats.background;

public class Door extends Background {

    private boolean opened = false;

    public void changeOpened() { opened = !opened; }

    @Override
    public boolean isPassable() { return opened; }

}
