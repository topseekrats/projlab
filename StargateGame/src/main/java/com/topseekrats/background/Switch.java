package com.topseekrats.background;

public class Switch extends Background {
    public void changeDoorState() {
        System.out.println("Switch.changeDoorState() called");

        System.out.println("Switch.changeDoorState() returned");
    }

    @Override
    public boolean isPassable() {
        System.out.println("Switch.isPassable() called");
        System.out.println("Switch.isPassable() returned");
        return true;
    }
}
