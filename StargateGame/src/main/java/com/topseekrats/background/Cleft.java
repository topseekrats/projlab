package com.topseekrats.background;

public class Cleft extends Background {
    public void destroy() {
        System.out.println("Cleft.destroy() called");
        System.out.println("Cleft.destroy() returned");
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
