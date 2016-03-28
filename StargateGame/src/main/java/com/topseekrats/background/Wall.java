package com.topseekrats.background;

public class Wall extends Background {

    public Wall() {
        /*System.out.println("Wall.newWall() called");

        System.out.println("Wall.newWall() returned");*/
    }

    public void changeHasStargate() {
        System.out.println("Wall.changeHasStargate() called");

        System.out.println("Wall.changeHasStargate() returned");
    }

    public boolean isPortalCompatible() {
        System.out.println("Wall.isPortalCompatible() called");

        System.out.println("Wall.isPortalCompatible() returned");
        return false;
    }

    @Override
    public boolean isPassable() {
        System.out.println("Wall.isPassable() called");
        System.out.println("Wall.isPassable() returned");
        return false;
    }
}
