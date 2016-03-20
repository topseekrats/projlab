package com.topseekrats.background;

import com.topseekrats.MazeObject;

public class Wall extends Background {
    public void changeHasStargete() {
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
