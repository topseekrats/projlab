package com.topseekrats.background;

import com.topseekrats.MazeObject;

public class Door extends Background {
    public void changeOpened() {
        System.out.println("Door.changeOpened() called");

        System.out.println("Door.changeOpened() returned");
    }

    @Override
    public boolean isPassable() {
        System.out.println("Door.isPassable() called");
        System.out.println("Door.isPassable() returned");
        return false;
    }
}
