package com.topseekrats.background;

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
